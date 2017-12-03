package com.whieenz.ormqueen.inter;

import android.database.sqlite.SQLiteDatabase;

import java.util.List;

/**
 * Created by whieenz on 2017/12/3.
 *
 */

public interface IDataBaseManager {
    void createTable(Class clazz, SQLiteDatabase... db);

    <T> void insertAll(List<T> list);

    void insert(Object object);

    <T> int delete(Class<T> clazz, int id);

    <T> int deleteAll(Class<T> clazz);

    <T> List<T> queryAll(Class<T> c);
}
