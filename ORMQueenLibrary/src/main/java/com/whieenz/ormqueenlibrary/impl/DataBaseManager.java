package com.whieenz.ormqueenlibrary.impl;


import android.annotation.TargetApi;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;

import com.whieenz.ormqueenlibrary.inter.OnClassDivisionListener;
import com.whieenz.ormqueenlibrary.inter.OnObjectDivisionListener;
import com.whieenz.ormqueenlibrary.util.DivisionUtil;
import com.whieenz.ormqueenlibrary.util.GsonUtil;
import com.whieenz.ormqueenlibrary.util.JavaReflectUtil;
import com.whieenz.ormqueenlibrary.util.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by whieenz on 2017/12/3.
 */

public abstract class DataBaseManager extends AbstractDataBaseManager {

    private SQLiteDatabase db;

    public DataBaseManager(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DataBaseManager(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    public SQLiteDatabase getDatabase() {
        return db;
    }

    public void setDatabase(SQLiteDatabase db) {
        this.db = db;
    }

    @Override
    void openDB() {
        db = this.getWritableDatabase();
    }

    @Override
    void closeDB() {
        db.close();
    }

    @Override
    void beginTransaction() {
        db.beginTransaction();
    }

    @Override
    void endTransaction() {
        db.endTransaction();
    }

    @Override
    void setTransactionSuccessful() {
        db.setTransactionSuccessful();
    }

    @Override
    void doCreateTable(Class clazz, SQLiteDatabase... db) {
        if (db.length > 0) {
            this.db = db[0];
        }
        exeCreateTable(clazz);
    }

    @Override
    public <T> void doInsertAll(List<T> list) {
        exeInsertAll(list);
    }

    @Override
    void doInsert(Object object) {
        exeInsert(object);
    }

    @Override
    <T> int doDelete(Class<T> clazz, int id) {
        return exeDelete(clazz, id);
    }

    @Override
    <T> int doDeleteAll(Class<T> clazz) {
        return exeDeleteAll(clazz);
    }

    @Override
    <T> List<T> doQueryAll(Class<T> c) {
        return exeQueryAll(c);
    }


    /**
     * 创建表默认会创建一个列名为id的列，主键，自动增长
     *
     * @param c       类
     * @param mainIDs 如果有外键 （外键名）
     */
    private void exeCreateTable(Class c, String... mainIDs) {
        final String TABLE_NAME = JavaReflectUtil.getClassName(c).toUpperCase();
        final StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("CREATE TABLE IF NOT EXISTS ").append(TABLE_NAME).append("(");
        sqlBuilder.append("ID TEXT PRIMARY KEY,");
        for (String mainID : mainIDs) {
            sqlBuilder.append(mainID).append(" TEXT,");
        }
        DivisionUtil.divisionByClass(c, new OnClassDivisionListener() {
            @Override
            public void afterBase(String column, Class clzaa, boolean isLast) {
                addColumn2Builder(column, isLast, sqlBuilder, "TEXT");
            }

            @Override
            public void afterObject(String column, Class clzaa, boolean isLast) {
                exeCreateTable(clzaa, TABLE_NAME + "_ID");
                addColumn2Builder(column, isLast, sqlBuilder, "TEXT");
            }

            @Override
            public void afterList(String column, Class clzaa, boolean isLast) {
                exeCreateTable(clzaa, TABLE_NAME + "_ID");
                addColumn2Builder(column, isLast, sqlBuilder, "TEXT");
            }
        });
        sqlBuilder.append(")");
        execSQL(sqlBuilder.toString());
    }

    /**
     * 新增数据库表一个列字段
     *
     * @param column     字段名对应Class 属性
     * @param isLast     是否结束标记
     * @param sqlBuilder sqlBuilder
     * @param columnType 字段类型
     */
    private void addColumn2Builder(String column, boolean isLast, StringBuilder sqlBuilder, String columnType) {
        String columnName = column.toUpperCase();
        if (isLast) {
            sqlBuilder.append(columnName).append(" ").append(columnType).append(" ");
        } else {
            sqlBuilder.append(columnName).append(" ").append(columnType).append(" ");
            sqlBuilder.append(",");
        }
    }


    /**
     * 插入批量数据
     *
     * @param <T>   泛型对象
     * @param lists 要插入的对象集合
     */
    private <T> void exeInsertAll(List<T> lists, String... ids) {
        if (lists == null) {
            return;
        }
        for (T t : lists) {
            exeInsert(t, ids);
        }
    }

    /**
     * 根据id删除一条数据
     *
     * @param c   要删除的对象类
     * @param <T> 泛型对象
     * @param id  要删除的id
     * @return [影响的行数]the number of rows affected
     */
    private <T> int exeDelete(Class<T> c, int id) {
        if (c == null) {
            return 0;
        }
        String TABLE_NAME = JavaReflectUtil.getClassName(c);
        int num = 0;
        try {
            openDB();
            num = db.delete(TABLE_NAME, "id=?", new String[]{String.valueOf(id)});
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeDB();
        }
        return num;
    }

    /**
     * 删除表中的全部数据
     *
     * @param c   要删除的对象类
     * @param <T> 泛型对象
     * @return [影响的行数]the number of rows affected
     */
    public <T> int exeDeleteAll(Class<T> c) {
        if (c == null) {
            return 0;
        }
        String TABLE_NAME = JavaReflectUtil.getClassName(c).toUpperCase();
        DivisionUtil.divisionByClass(c, new OnClassDivisionListener() {
            @Override
            public void afterBase(String column, Class clzaa, boolean isLast) {

            }

            @Override
            public void afterObject(String column, Class clzaa, boolean isLast) {
                exeDeleteAll(clzaa);
            }

            @Override
            public void afterList(String column, Class clzaa, boolean isLast) {
                exeDeleteAll(clzaa);
            }
        });
        int num = db.delete(TABLE_NAME, null, null);
        return num;
    }

    /**
     * 根据条件删除一条数据
     *
     * @param c     要删除的对象类
     * @param <T>   泛型对象
     * @param where 要删除条件
     * @return [影响的行数]the number of rows affected
     */
    public <T> int exeDelete(Class<T> c, String where) {
        if (c == null) {
            return 0;
        }
        String TABLE_NAME = JavaReflectUtil.getClassName(c);
        int num = db.delete(TABLE_NAME, where, null);
        return num;
    }

    /**
     * 根据id修改一条数据
     *
     * @param t   要修改的对象
     * @param <T> 泛型对象
     * @param id  要修改的id
     * @return [影响的行数]the number of rows affected
     */
    public <T> int update(T t, int id) {
        if (t == null) {
            return 0;
        }
        int num = 0;
        String TABLE_NAME = JavaReflectUtil.getClassName(t.getClass());
        ContentValues contentValues = getContentValues(t);
        num = db.update(TABLE_NAME, contentValues, "id=?", new String[]{String.valueOf(id)});
        return num;
    }

    /**
     * 根据条件修改一条数据
     *
     * @param t     要修改的对象
     * @param <T>   泛型对象
     * @param where 要修改的条件
     * @return [影响的行数]the number of rows affected
     */
    public <T> int update(T t, String where) {
        if (t == null) {
            return -1;
        }
        String TABLE_NAME = JavaReflectUtil.getClassName(t.getClass());
        ContentValues contentValues = getContentValues(t);
        int num = db.update(TABLE_NAME, contentValues, where, null);
        return num;
    }

    /**
     * 根据id查询一条数据
     *
     * @param c   要查询的对象类
     * @param id  要查询的对象的id
     * @param <T> 泛型对象
     * @return 查询出来的数据对象
     */
    public <T> T query(Class<T> c, int id) {

        if (c == null) {
            return null;
        }

        String TABLE_NAME = JavaReflectUtil.getClassName(c);
        Cursor cursor = null;
        try {
            cursor = db.query(TABLE_NAME, null, "id=?", new String[]{String.valueOf(id)}, null, null, null);
            if (cursor == null) {

                return null;
            }
            if (cursor.moveToNext()) {
                return cursor2Bean(c, cursor);
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return null;
    }

    private <T> List<T> exeQueryAll(Class<T> c, String... condition) {
        List<T> lists = null;
        Cursor cursor = null;
        try {
            if (c == null) {
                return null;
            }
            String TABLE_NAME = JavaReflectUtil.getClassName(c).toUpperCase();
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT * FROM ").append(TABLE_NAME);
            List<String> conditions = new ArrayList<>();
            for (int i = 0; i < condition.length; i++) {
                sql.append(" WHERE ");
                sql.append(condition[i++]);
                sql.append(" =?");
                if (i < condition.length - 1)
                    sql.append(" ,");
                conditions.add(condition[i]);
            }
            cursor = db.rawQuery(sql.toString(), conditions.toArray(new String[conditions.size()]));
            if (cursor == null) {
                return null;
            }
            lists = new ArrayList<>();
            while (cursor.moveToNext()) {
                lists.add(cursor2Bean(c, cursor));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return lists;
    }

    /**
     * 删除数据表
     *
     * @param <T> 泛型对象
     * @param c   要删除的对象类，自动映射为表名
     */
    private <T> void dropTable(Class<T> c) {
        if (c == null) {
            return;
        }
        String TABLE_NAME = JavaReflectUtil.getClassName(c).toUpperCase();
        String sql = "DROP TABLE " +
                "IF EXISTS " +
                TABLE_NAME;
        DivisionUtil.divisionByClass(c, new OnClassDivisionListener() {
            @Override
            public void afterBase(String column, Class clzaa, boolean isLast) {
                dropTable(clzaa);
            }

            @Override
            public void afterObject(String column, Class clzaa, boolean isLast) {
                dropTable(clzaa);
            }

            @Override
            public void afterList(String column, Class clzaa, boolean isLast) {
                dropTable(clzaa);
            }
        });
        execSQL(sql);
    }

    private <T> List<T> query(Class<T> c, String... condition) {
        List<T> lists = null;
        Cursor cursor = null;
        try {
            if (c == null) {
                return null;
            }
            String TABLE_NAME = JavaReflectUtil.getClassName(c).toUpperCase();
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT * FROM ").append(TABLE_NAME);
            List<String> conditions = new ArrayList<>();
            for (int i = 0; i < condition.length; i++) {
                sql.append(" WHERE ");
                sql.append(condition[i++]);
                sql.append(" =?");
                if (i < condition.length - 1)
                    sql.append(" ,");
                conditions.add(condition[i]);
            }
            cursor = db.rawQuery(sql.toString(), conditions.toArray(new String[conditions.size()]));
            if (cursor == null) {
                return null;
            }
            lists = new ArrayList<>();
            while (cursor.moveToNext()) {
                lists.add(cursor2Bean(c, cursor));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return lists;
    }

    /**
     * 执行Sql语句建表，插入，修改，删除
     *
     * @param sql 要执行的sqk语句
     * @throws SQLException SQL语句不正确
     */
    public void execSQL(String sql) {
        try {
            db.execSQL(sql);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }


    private String getID(String table_name) {
        String id = "";
        String sql = "SELECT MAX(ID)+1 AS ID FROM " + table_name;
        try {
            Cursor cursor = db.rawQuery(sql, null, null);
            if (cursor.moveToFirst()) {
                id = cursor.getString(cursor.getColumnIndex("ID"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        return id == null ? "1" : id;
    }

    /**
     * 执行Sql语句，查询
     *
     * @param sql 要执行的sql语句
     * @return 一个包含Map中key=字段名,value=值的集合对象
     * @throws SQLException SQL语句不正确
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public List<Map<String, Object>> rawQuery(String sql) {
        List<Map<String, Object>> lists = null;
        Cursor cursor = null;
        try {
            openDB();
            cursor = db.rawQuery(sql, null, null);
            if (cursor == null) {
                return null;
            }
            lists = new ArrayList<>();
            while (cursor.moveToNext()) {
                Map<String, Object> map = new HashMap<>();
                for (String column : cursor.getColumnNames()) {
                    map.put(column, cursor.getString(cursor.getColumnIndex(column)));
                }
                lists.add(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            closeDB();
        }
        return lists;
    }


    /**
     * 查询表中的所有数据
     *
     * @param c   要查询的对象类
     * @param <T> 泛型对象
     * @return 查询出来的对象类集合
     */
    public <T> List<T> cursor2Bean(Cursor cursor, Class<T> c) {
        List<T> lists = null;
        if (cursor == null) {
            return null;
        }
        lists = new ArrayList<>();
        while (cursor.moveToNext()) {
            lists.add(cursor2Bean(c, cursor));
        }
        if (cursor != null) {
            cursor.close();
        }
        return lists;
    }

    /**
     * 根据游标所得到的的值创建一个对象
     *
     * @param c      对象类
     * @param cursor 游标
     * @param <T>    泛型对象
     * @return 所要创建的对象
     */

    private <T> T cursor2Bean(Class<T> c, final Cursor cursor) {
        final String TABLE_NAME = JavaReflectUtil.getClassName(c).toUpperCase();
        final String id = cursor.getString(cursor.getColumnIndex("ID"));
        final Map retMap = new HashMap();
        DivisionUtil.divisionByClass(c, new OnClassDivisionListener() {
            @Override
            public void afterBase(String column, Class clzaa, boolean isLast) {
                String val = cursor.getString(cursor.getColumnIndex(column.toUpperCase()));
                if (!StringUtil.isEmpty(val)) {
                    retMap.put(column, val);
                }
            }

            @Override
            public void afterObject(String column, Class clzaa, boolean isLast) {
                String mainID = cursor.getString(cursor.getColumnIndex(column.toUpperCase()));
                List clazzList = query(clzaa, "ID", mainID);
                if (clazzList != null && clazzList.size() > 0) {
                    retMap.put(column, clazzList.get(0));
                }
            }

            @Override
            public void afterList(String column, Class clzaa, boolean isLast) {
                List clazzList = query(clzaa, TABLE_NAME + "_ID", id);
                if (clazzList != null && clazzList.size() > 0) {
                    retMap.put(column, clazzList);
                }
            }
        });
        return (T) GsonUtil.stringToObject(GsonUtil.objectToString(retMap), c);
    }

    /**
     * 根据对象获取 ContentValues   ids TABLE_ID 'value'
     *
     * @param t   对象
     * @param <T> 泛型对象
     * @return ContentValues用来操作SQLite数据库
     */
    public <T> ContentValues getContentValues(T t, String... ids) {
        Class<?> c = t.getClass();
        final String table_name = JavaReflectUtil.getClassName(c).toUpperCase();
        String tempId = StringUtil.getStrim(UUID.randomUUID());
        final ContentValues contentValues = new ContentValues();

        for (int i = 0; i < ids.length; i += 2) {
            contentValues.put(ids[i], ids[i + 1]);
            if (ids[i].equals("ID")) {
                tempId = ids[i + 1];
            }
        }
        final String id = tempId;

        DivisionUtil.divisionByObject(t, new OnObjectDivisionListener() {
            @Override
            public void afterBase(String column, Class clzaa, String value) {
                if (value == null) {
                    contentValues.putNull(column.toUpperCase());
                } else {
                    contentValues.put(column.toUpperCase(), String.valueOf(value));
                }
            }

            @Override
            public void afterObject(String column, Class clzaa, Object value) {
                String subID = StringUtil.getStrim(UUID.randomUUID());
                contentValues.put(column, subID);
                exeInsert(value, "ID", subID);
            }

            @Override
            public void afterList(String column, Class clzaa, List value) {
                exeInsertAll(value, table_name + "_ID", id);
            }
        });
        if (!contentValues.containsKey("ID")) {
            contentValues.put("ID", id);
        }
        return contentValues;
    }

    /**
     * 插入一条数据
     *
     * @param <T> 泛型对象
     * @param t   要插入的对象
     * @return [影响的行数]he row ID of the newly inserted row, or -1 if an error occurred
     */
    public <T> long exeInsert(T t, String... ids) {
        if (t == null) {
            return -1;
        }
        Class<?> c = t.getClass();
        String TABLE_NAME = JavaReflectUtil.getClassName(c).toUpperCase();
        ContentValues contentValues = getContentValues(t, ids);
        long num = db.insert(TABLE_NAME, null, contentValues);
        return num;
    }

}
