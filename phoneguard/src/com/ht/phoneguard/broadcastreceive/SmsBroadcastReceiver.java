package com.ht.phoneguard.broadcastreceive;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import com.ht.phoneguard.dao.DbManager;
import com.ht.phoneguard.pojo.DuanXin;

import java.text.SimpleDateFormat;

/**
 * Created by IntelliJ IDEA
 * Project: com.ht.smstest
 * Author: 安诺爱成长
 * Email: 1399487511@qq.com
 * Date: 2015/5/15
 */
public class SmsBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        //接收由sms传递过来的数据
        Bundle extras = intent.getExtras();
        //通过pdus可以获得接收到的所有短信信息
        Object[] array = (Object[]) extras.get("pdus");
        //因为可能同时获得多个信息
        for (Object o1 : array) {
            SmsMessage message = SmsMessage.createFromPdu((byte[]) o1);
            //获得接收短信的电话号码
            String adress = message.getOriginatingAddress();
            //获得短信的内容
            String content = message.getDisplayMessageBody();
            if (DbManager.getInstance().phonenumberisExist(adress) || (DbManager.getInstance().phonenumberisExist("+86" +adress))) {
                //把短信加入短信拦截表
                DuanXin duanXin = new DuanXin();
                duanXin.setPhonenumber(adress);
                duanXin.setContent(content);
                SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String time = sDateFormat.format(System.currentTimeMillis());
                duanXin.setTime(time);
                DbManager.getInstance().addDuanXin(duanXin);
                abortBroadcast();
            }
        }

    }


}
