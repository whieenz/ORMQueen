package com.whieenz.ormqueen.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.whieenz.ormqueen.impl.DataBaseManager;


/**
 * 数据库核心类，用于初始化表结构和在APP版本更新时处理表结构的更新
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "test.db";
    public static final int DATABASE_VERSION = 0;

    private DataBaseManager dataBaseManager;


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建数据库后，对数据库的操作
        Log.i(""," ss ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}