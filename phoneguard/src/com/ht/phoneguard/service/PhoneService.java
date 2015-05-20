package com.ht.phoneguard.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import com.android.internal.telephony.ITelephony;
import com.ht.phoneguard.dao.DbManager;
import com.ht.phoneguard.pojo.TongHua;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;

/**
 * Created by annuo on 2015/5/17.
 */
public class PhoneService extends Service {

    TelephonyManager tManager;
    CustomPhone custonPhne;

    public class CustomPhone extends PhoneStateListener {
        @Override
        public void onCallStateChanged(int state, String number) {
            switch (state) {
                case TelephonyManager.CALL_STATE_IDLE:
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    break;
                case TelephonyManager.CALL_STATE_RINGING:
                    if (DbManager.getInstance().phonenumberisExist("+86"+number)) {
                        try {
                            Method method = Class.forName("android.os.ServiceManager")
                                    .getMethod("getService", String.class);
                            IBinder binder = (IBinder) method.invoke(null, new Object[]{TELEPHONY_SERVICE});
                            ITelephony telephony = ITelephony.Stub
                                    .asInterface(binder);
                            // 挂断电话
                            Log.d("PhoneService", "挂断");
                            telephony.endCall();
                            //把来电记录存入拦截记录表中
                            TongHua tongHua = new TongHua();
                            SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            String time = sDateFormat.format(System.currentTimeMillis());
                            tongHua.setTime(time);
                            tongHua.setPhonenumber("+86"+number);
                            DbManager.getInstance().addTongHua(tongHua);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    break;
            }

        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //拦截电话
        tManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        custonPhne = new CustomPhone();
        tManager.listen(custonPhne, PhoneStateListener.LISTEN_CALL_STATE);
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
