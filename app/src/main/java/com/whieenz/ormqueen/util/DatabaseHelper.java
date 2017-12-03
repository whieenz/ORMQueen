package com.whieenz.ormqueen.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.whieenz.ormqueen.bean.MultiBean;
import com.whieenz.ormqueen.impl.DataBaseManager;

import java.sql.DatabaseMetaData;


/**
 * 数据库核心类，用于初始化表结构和在APP版本更新时处理表结构的更新
 */
public class DatabaseHelper extends DataBaseManager {

    private static final String DATABASE_NAME = "test1.db";
    private static final int DATABASE_VERSION = 2;


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        createTable(MultiBean.class, sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        createTable(MultiBean.class, sqLiteDatabase);
    }
}