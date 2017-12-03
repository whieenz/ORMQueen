package com.whieenz.ormqueen.impl;


import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.whieenz.ormqueen.inter.IDataBaseManager;

import java.util.List;

/**
 * Created by whieenz on 2017/12/3.
 */

abstract class AbstractDataBaseManager extends SQLiteOpenHelper implements IDataBaseManager {

    public AbstractDataBaseManager(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public AbstractDataBaseManager(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    abstract void openDB();

    abstract void closeDB();

    abstract void beginTransaction();

    abstract void endTransaction();

    abstract void setTransactionSuccessful();

    abstract void doCreateTable(Class clazz, SQLiteDatabase... db);

    abstract <T> void doInsertAll(List<T> list);

    abstract void doInsert(Object object);

    abstract <T> int doDelete(Class<T> clazz, int id);

    abstract <T> int doDeleteAll(Class<T> clazz);

    abstract <T> List<T> doQueryAll(Class<T> c);

    @Override
    public void createTable(Class clazz, SQLiteDatabase... dbs) {
        if (dbs != null && dbs.length > 0) {
            doCreateTable(clazz, dbs);
        } else {
            try {
                openDB();
                beginTransaction();
                doCreateTable(clazz);
                setTransactionSuccessful();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                endTransaction();
                closeDB();
            }
        }
    }

    @Override
    public <T> void insertAll(List<T> list) {
        try {
            openDB();
            beginTransaction();
            doInsertAll(list);
            setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            endTransaction();
            closeDB();
        }
    }

    @Override
    public void insert(Object object) {
        try {
            openDB();
            beginTransaction();
            doInsert(object);
            setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            endTransaction();
            closeDB();
        }
    }

    @Override
    public <T> int delete(Class<T> clazz, int id) {
        int result = -1;
        try {
            openDB();
            beginTransaction();
            result = doDelete(clazz, id);
            setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            endTransaction();
            closeDB();
        }
        return result;
    }

    @Override
    public <T> int deleteAll(Class<T> clazz) {
        int result = -1;
        try {
            openDB();
            beginTransaction();
            result = doDeleteAll(clazz);
            setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            endTransaction();
            closeDB();
        }
        return result;
    }

    @Override
    public <T> List<T> queryAll(Class<T> clazz) {
        List<T> result = null;
        try {
            openDB();
            beginTransaction();
            result = doQueryAll(clazz);
            setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            endTransaction();
            closeDB();
        }
        return result;
    }
}
