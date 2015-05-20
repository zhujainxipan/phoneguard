package com.ht.phoneguard.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by IntelliJ IDEA
 * Project: com.ht.mynote.utils
 * Author: 安诺爱成长
 * Email: 1399487511@qq.com
 * Date: 2015/5/4
 */
public class PhoneGuardSQLiteOpenHelper extends SQLiteOpenHelper {
    public PhoneGuardSQLiteOpenHelper(Context context) {
        super(context, "phoneguard", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE dark(_id INTEGER PRIMARY KEY ASC AUTOINCREMENT, name TEXT NOT NULL, phonenumber TEXT NOT NULL);");
        sqLiteDatabase.execSQL("CREATE TABLE duanxin(_id INTEGER PRIMARY KEY ASC AUTOINCREMENT, phonenumber TEXT, content TEXT, time TEXT);");
        sqLiteDatabase.execSQL("CREATE TABLE tonghua(_id INTEGER PRIMARY KEY ASC AUTOINCREMENT, phonenumber TEXT, time TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }
}