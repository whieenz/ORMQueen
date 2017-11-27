package com.whieenz.ormqueen.util;

import android.annotation.TargetApi;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * SQLite封装工具类
 * Created by heziwen on 2017-08-28.
 */

public class SQLiteDbUtil {
    /**
     * 支持的表字段数据类型包括：基本类型、包装类型、String类型、Date类型
     */
    static String[] types = {"java.lang.Integer",
            "java.lang.Double",
            "java.lang.Float",
            "java.lang.Long",
            "java.lang.Short",
            "java.lang.Byte",
            "java.lang.Boolean",
            "java.lang.Character",
            "java.lang.String",
            "java.util.Date",
            "java.math.BigDecimal",
            "int", "double", "long", "short", "byte", "boolean", "char", "float"};

    private static boolean isBase(String type) {
        return Arrays.asList(types).contains(type);
    }

    /**
     * SQLite工具类对象
     */
    private static volatile SQLiteDbUtil sqLiteDbUtil;
    /**
     * 打印日志的TAG，用来调试
     */

    private final DatabaseHelper dbHelper;

    private final String TAG = "SQLiteDbUtil";

    /**
     * SQLiteDateBase对象
     */
    private SQLiteDatabase sqLiteDatabase;
    /**
     * 上下文
     */
    private Context context;
    /**
     * 数据库的路径
     */
    private String path;
    /**
     * 数据库名称
     */
    private String databaseName;

    /**
     * 构造函数私有化，单例
     */
    private SQLiteDbUtil(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    /**
     * 单例
     *
     * @return 数据库管理工具类对象
     */
    public static SQLiteDbUtil getSQLiteDbUtil(Context context) {
        if (sqLiteDbUtil == null) {
            synchronized (SQLiteDbUtil.class) {
                if (sqLiteDbUtil == null) {
                    sqLiteDbUtil = new SQLiteDbUtil(context);
                }
            }
        }
        return sqLiteDbUtil;
    }

    /**
     * 创建或打开数据库连接
     * 默认创建在"/data/data/cn.bluemobi.dylan.sqlite/databases/mydb.db";
     *
     * @param context 上下文
     */
    public void openOrCreateDataBase(Context context) {
        openOrCreateDataBase(context, null, null);
    }

    /**
     * 创建或打开数据库连接 重载
     * 默认创建在"/data/data/cn.bluemobi.dylan.sqlite/databases/mydb.db";
     *
     * @param context      上下文
     * @param databaseName 数据库名称
     */
    public void openOrCreateDataBase(Context context, String databaseName) {
        openOrCreateDataBase(context, null, databaseName);
    }

    /**
     * 创建或打开数据库连接 重载
     * 默认创建在"/data/data/cn.bluemobi.dylan.sqlite/databases/mydb.db";
     *
     * @param context      上下文
     * @param path         数据库路径
     * @param databaseName 数据库表名
     */
    public void openOrCreateDataBase(Context context, String path, String databaseName) {
        this.context = context;
        this.path = path;
        this.databaseName = databaseName;
        if (TextUtils.isEmpty(databaseName)) {
            databaseName = "mydb.db";
        }
        if (TextUtils.isEmpty(path)) {
            sqLiteDatabase = context.openOrCreateDatabase(databaseName, Context.MODE_PRIVATE, null);
        } else {
            File dataBaseFile = new File(path, databaseName);
            if (!dataBaseFile.getParentFile().exists()) {
                dataBaseFile.mkdirs();
            }
            sqLiteDatabase = SQLiteDatabase.openOrCreateDatabase(dataBaseFile, null);
        }
    }


    /**
     * 获取数据库操作对象
     *
     * @return 数据库操作对象 SQLiteDatabase
     */
    public SQLiteDatabase getSqLiteDatabase() {
        return sqLiteDatabase;
    }

    /**
     * 打开数据库连接
     *
     * @throws Exception 数据库为初始化异常
     */
    private void open() throws Exception {
        if (sqLiteDatabase != null) {
            this.openOrCreateDataBase(context, path, databaseName);
        } else {
            throw new Exception("未初始化数据库");
        }

    }

    private void openDB() {
        sqLiteDatabase = dbHelper.getWritableDatabase();
    }

    /**
     * 关闭数据库连接
     */
    public void close() {
        if (sqLiteDatabase != null && sqLiteDatabase.isOpen()) {
            sqLiteDatabase.close();
        }
    }

//    /**
//     * 创建表默认会创建一个列名为id的列，主键，自动增长
//     *
//     * @param <T> 泛型对象
//     * @param c   要创建的对象类，自动映射为表名
//     */
//    public <T> void createTable(Class<T> c) {
//        String TABLE_NAME = JavaReflectUtil.getClassName(c).toUpperCase();
//        String[] column = JavaReflectUtil.getAttributeNames(c);
//        Object[] type = JavaReflectUtil.getAttributeType(c);
//        Object[] listType = JavaReflectUtil.getAttributeListType(c);
//        int listItem = 0;
//        StringBuilder sqlBuilder = new StringBuilder();
//        sqlBuilder.append("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(");
//        sqlBuilder.append("ID INTEGER PRIMARY KEY AUTOINCREMENT,");
//        for (int i = 0; i < column.length; i++) {
//            String columnName = column[i].toUpperCase();
//            if (!"ID".equals(columnName)) {
//                String typeInfo = ((Class) type[i]).getName();
//                if (isBase(typeInfo)) {  //是否基本类型
//                    if (i != column.length - 1) {
//                        sqlBuilder.append(columnName + " " + "TEXT ");
//                        sqlBuilder.append(",");
//                    } else {
//                        sqlBuilder.append(columnName + " " + "TEXT ");
//                    }
//                } else if (typeInfo.equals("java.util.List")) {
//                    Class listClass = (Class) listType[listItem++];
//                    createSubTable(listClass, TABLE_NAME + "_ID");
//                    if (i != column.length - 1) {
//                        sqlBuilder.append(columnName + " " + " INTEGER");
//                        sqlBuilder.append(",");
//                    } else {
//                        sqlBuilder.append(columnName + " " + " INTEGER");
//                    }
//                } else {
//                    Class<?> newClass;
//                    try {
//                        newClass = Class.forName(typeInfo);
//                        createTable(newClass);
//                    } catch (ClassNotFoundException e) {
//                        e.printStackTrace();
//                    }
//                    if (i != column.length - 1) {
//                        sqlBuilder.append(columnName + " INTEGER");
//                        sqlBuilder.append(",");
//                    } else {
//                        sqlBuilder.append(columnName + " INTEGER");
//                    }
//                }
//            }
//        }
//        sqlBuilder.append(")");
//        Log.i("", sqlBuilder.toString());
//        execSQL(sqlBuilder.toString());
//    }

    public void createTable(Class c, String... mainIDs) {
        String TABLE_NAME = JavaReflectUtil.getClassName(c).toUpperCase();
        String[] column = JavaReflectUtil.getAttributeNames(c);
        Object[] type = JavaReflectUtil.getAttributeType(c);
        Object[] listType = JavaReflectUtil.getAttributeListType(c);
        int listItem = 0;
        List<String> createdTableCache = new ArrayList<>();

        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(");
        sqlBuilder.append("ID INTEGER PRIMARY KEY AUTOINCREMENT,");
        for (String mainID : mainIDs) {
            sqlBuilder.append(mainID + " INTEGER,");
        }
        for (int i = 0; i < column.length; i++) {
            String columnName = column[i].toUpperCase();
            if (!"ID".equals(columnName)) {
                String typeInfo = ((Class) type[i]).getName();
                if (isBase(typeInfo)) {  //是否基本类型
                    if (i != column.length - 1) {
                        sqlBuilder.append(columnName + " " + "TEXT ");
                        sqlBuilder.append(",");
                    } else {
                        sqlBuilder.append(columnName + " " + "TEXT ");
                    }
                } else if (typeInfo.equals("java.util.List")) {
                    Class listClass = (Class) listType[listItem++];
                    if (!createdTableCache.contains(listClass.getName().toUpperCase())) {
                        createTable(listClass, TABLE_NAME + "_ID");
                        createdTableCache.add(listClass.getName().toUpperCase());
                    }
                    if (i != column.length - 1) {
                        sqlBuilder.append(columnName + " " + " INTEGER");
                        sqlBuilder.append(",");
                    } else {
                        sqlBuilder.append(columnName + " " + " INTEGER");
                    }
                } else {
                    Class<?> newClass;
                    try {
                        newClass = Class.forName(typeInfo);
                        if (!createdTableCache.contains(newClass.getName().toUpperCase())) {
                            createTable(newClass, TABLE_NAME + "_ID");
                            createdTableCache.add(newClass.getName().toUpperCase());
                        }
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    if (i != column.length - 1) {
                        sqlBuilder.append(columnName + " INTEGER");
                        sqlBuilder.append(",");
                    } else {
                        sqlBuilder.append(columnName + " INTEGER");
                    }
                }
            }
        }
        sqlBuilder.append(")");
        Log.i("", sqlBuilder.toString());
        execSQL(sqlBuilder.toString());
    }

    //    public static <T> void createTable(Class<T> c) {
//        String TABLE_NAME = JavaReflectUtil.getClassName(c);
//        String[] column = JavaReflectUtil.getAttributeNames(c);
//        Object[] type = JavaReflectUtil.getAttributeType(c);
//
//        showInfo(column, type);
//        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(";
//        sql += "id  Integer PRIMARY KEY AUTOINCREMENT,";
//        for (int i = 0; i < column.length; i++) {
//            if (!"id".equals(column[i])) {
//                if (i != column.length - 1) {
//                    sql += column[i] + " " + type[i].getClass().getSimpleName() + ",";
//                } else {
//                    sql += column[i] + " " + type.getClass().getSimpleName();
//                }
//            }
//        }
//        sql += ")";
//        //execSQL(sql);
//    }
    private static void showInfo(String[] column, Object[] type) {
        if (column.length != type.length) {
            return;
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < column.length; i++) {
            builder.append("KEY:");
            builder.append(column[i]);
            builder.append(":");
            builder.append("TYPE:");
            builder.append(((Class) type[i]).getName());
            builder.append("\n");
        }
        System.out.print(builder);
    }


    /**
     * 删除数据表
     *
     * @param <T> 泛型对象
     * @param c   要删除的对象类，自动映射为表名
     */
    public <T> void drop(Class<T> c) {
        if (c == null) {
            return;
        }
        String TABLE_NAME = JavaReflectUtil.getClassName(c);
        String sql = "DROP TABLE " +
                "IF EXISTS " +
                TABLE_NAME;
        execSQL(sql);
    }

//    /**
//     * 插入一条数据
//     *
//     * @param <T> 泛型对象
//     * @param t   要插入的对象
//     * @return [影响的行数]he row ID of the newly inserted row, or -1 if an error occurred
//     */
//    public <T> long insert(T t) {
//        if (t == null) {
//            return -1;
//        }
//        String TABLE_NAME = JavaReflectUtil.getClassName(t.getClass());
//        ContentValues contentValues = getContentValues(t);
//        long num = 0;
//        try {
//            open();
//            num = sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            close();
//        }
//        return num;
//    }


    /**
     * 插入批量数据
     *
     * @param <T>   泛型对象
     * @param lists 要插入的对象集合
     */
    public <T> void insertAll(List<T> lists) {
        if (lists == null) {
            return;
        }
        try {
            open();
            sqLiteDatabase.beginTransaction();
            for (T t : lists) {
                String TABLE_NAME = JavaReflectUtil.getClassName(t.getClass());
                ContentValues contentValues = getContentValues(t);
                sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
            }
            sqLiteDatabase.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sqLiteDatabase.endTransaction();
            close();
        }
    }

    /**
     * 插入批量数据
     *
     * @param <T>   泛型对象
     * @param lists 要插入的对象集合
     */
    public <T> void insertAll(List<T> lists, String... ids) {
        if (lists == null) {
            return;
        }
        for (T t : lists) {
            insert(t, ids);
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
    public <T> int delete(Class<T> c, int id) {
        if (c == null) {
            return 0;
        }
        String TABLE_NAME = JavaReflectUtil.getClassName(c);
        int num = 0;
        try {
            open();
            num = sqLiteDatabase.delete(TABLE_NAME, "id=?", new String[]{String.valueOf(id)});
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
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
    public <T> int delete(Class<T> c, String where) {
        if (c == null) {
            return 0;
        }
        String TABLE_NAME = JavaReflectUtil.getClassName(c);
        int num = 0;
        try {
            open();
            num = sqLiteDatabase.delete(TABLE_NAME, where, null);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
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
    public <T> int deleteAll(Class<T> c) {
        if (c == null) {
            return 0;
        }
        String TABLE_NAME = JavaReflectUtil.getClassName(c).toUpperCase();
        String[] column = JavaReflectUtil.getAttributeNames(c);
        Object[] type = JavaReflectUtil.getAttributeType(c);
        Object[] listType = JavaReflectUtil.getAttributeListType(c);
        int listItem = 0;
        for (int i = 0; i < column.length; i++) {
            String tableColumn = column[i].toUpperCase();
            if (!"ID".equals(tableColumn)) {
                String typeInfo = ((Class) type[i]).getName();
                if (isBase(typeInfo)) {  //是否基本类型

                } else if (typeInfo.equals("java.util.List")) {
                    Class listClass = (Class) listType[listItem++];
                    deleteAll(listClass);
                } else {
                    Class<?> newClass;
                    try {
                        newClass = Class.forName(typeInfo);
                        deleteAll(newClass);
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        int num = 0;
        try {
            openDB();
            num = sqLiteDatabase.delete(TABLE_NAME, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
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
        try {
            String TABLE_NAME = JavaReflectUtil.getClassName(t.getClass());
            ContentValues contentValues = getContentValues(t);
            open();
            num = sqLiteDatabase.update(TABLE_NAME, contentValues, "id=?", new String[]{String.valueOf(id)});
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
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
        int num = 0;
        try {
            open();
            num = sqLiteDatabase.update(TABLE_NAME, contentValues, where, null);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
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
            open();
            cursor = sqLiteDatabase.query(TABLE_NAME, null, "id=?", new String[]{String.valueOf(id)}, null, null, null);
            if (cursor == null) {

                return null;
            }
            if (cursor.moveToNext()) {
                return newInstance(c, cursor);
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            close();
        }
        return null;
    }

    /**
     * 查询表中的所有数据
     *
     * @param c   要查询的对象类
     * @param <T> 泛型对象
     * @return 查询出来的对象类集合
     */
//    public <T> List<T> query(Class<T> c) {
//
//        List<T> lists = null;
//        Cursor cursor = null;
//        try {
//            if (c == null) {
//                return null;
//            }
//            openDB();
//            String TABLE_NAME = JavaReflectUtil.getClassName(c).toUpperCase();
//            cursor = sqLiteDatabase.query(TABLE_NAME, null, null, null, null, null, null);
//            if (cursor == null) {
//                return null;
//            }
//            lists = new ArrayList<>();
//            while (cursor.moveToNext()) {
//                lists.add(newInstance(c, cursor));
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (cursor != null) {
//                cursor.close();
//            }
//            close();
//        }
//        return lists;
//    }
    public <T> List<T> query(Class<T> c, String... condition) {
        List<T> lists = null;
        Cursor cursor = null;
        try {
            if (c == null) {
                return null;
            }
            openDB();
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
            cursor = sqLiteDatabase.rawQuery(sql.toString(), conditions.toArray(new String[conditions.size()]));
            if (cursor == null) {
                return null;
            }
            lists = new ArrayList<>();
            while (cursor.moveToNext()) {
                lists.add(newInstance(c, cursor));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            close();
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
            openDB();
            sqLiteDatabase.execSQL(sql);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
    }

    private String getID(String sql) {
        String id = "";
        try {
            openDB();
            Cursor cursor = sqLiteDatabase.rawQuery(sql, null, null);
            if (cursor.moveToFirst()) {
                id = cursor.getString(cursor.getColumnIndex("ID"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
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
            open();
            cursor = sqLiteDatabase.rawQuery(sql, null, null);
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
            close();
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
    public  <T> List<T> cursor2Bean(Cursor cursor, Class<T> c) {
        List<T> lists = null;
        if (cursor == null) {
            return null;
        }
        lists = new ArrayList<>();
        while (cursor.moveToNext()) {
            lists.add(newInstance(c, cursor));
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

    private <T> T newInstance(Class<T> c, Cursor cursor) {
        String TABLE_NAME = JavaReflectUtil.getClassName(c).toUpperCase();
        String[] column = JavaReflectUtil.getAttributeNames(c);
        Object[] type = JavaReflectUtil.getAttributeType(c);
        Object[] listType = JavaReflectUtil.getAttributeListType(c);
        String id = cursor.getString(cursor.getColumnIndex("ID"));
        int listItem = 0;
        final Map retMap = new HashMap();
        for (int i = 0; i < column.length; i++) {
            String columnName = column[i];
            String tableColumn = column[i].toUpperCase();
            if (!"ID".equals(tableColumn)) {
                String typeInfo = ((Class) type[i]).getName();
                if (isBase(typeInfo)) {  //是否基本类型
                    String val = cursor.getString(cursor.getColumnIndex(tableColumn));
                    if (!StringUtil.isEmpty(val)) {
                        retMap.put(columnName, val);
                    }
                } else if (typeInfo.equals("java.util.List")) {
                    Class listClass = (Class) listType[listItem++];
                    List clazzList = query(listClass, TABLE_NAME + "_ID", id);
                    if (clazzList != null && clazzList.size() > 0) {
                        retMap.put(columnName, clazzList);
                    }
                } else {
                    Class<?> newClass;
                    try {
                        newClass = Class.forName(typeInfo);
                        List clazzList = query(newClass, TABLE_NAME + "_ID", id);
                        if (clazzList != null && clazzList.size() > 0) {
                            retMap.put(columnName, clazzList.get(0));
                        }
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return (T) GsonUtil.stringToObject(GsonUtil.objectToString(retMap), c);
    }
//    public static <T> T newInstance(Class<T> c, Cursor cursor) {
//        try {
//            Constructor<T> con = c.getConstructor();
//            con.setAccessible(true);
//            T t = con.newInstance();
//            Class[] types = JavaReflectUtil.getAttributeType(c);
//            String[] columns = JavaReflectUtil.getAttributeNames(c);
//            for (int i = 0; i < types.length; i++) {
//                String column = columns[i];
//                String tableColumn = columns[i].toUpperCase();
//                Class type = types[i];
//                Method method = c.getMethod("set" + column.substring(0, 1).toUpperCase() + column.substring(1), new Class[]{type});
//                Object value = null;
//                if (type.equals(String.class)) {
//                    value = cursor.getString(cursor.getColumnIndex(tableColumn));
//                } else if (type.equals(Integer.class) || type.getName().equals("int")) {
//                    value = cursor.getInt(cursor.getColumnIndex(tableColumn));
//                } else if (type.equals(Character.class) || type.getName().equals("char")) {
//                    value = Character.valueOf(cursor.getString(cursor.getColumnIndex(tableColumn)).charAt(0));
//                } else if (type.equals(Boolean.class) || type.getName().equals("boolean")) {
//                    value = "true".equalsIgnoreCase(cursor.getString(cursor.getColumnIndex(tableColumn))) ? true : false;
//                } else if (type.equals(Float.class) || type.getName().equals("float")) {
//                    value = cursor.getFloat(cursor.getColumnIndex(tableColumn));
//                } else if (type.equals(Double.class) || type.getName().equals("double")) {
//                    value = cursor.getDouble(cursor.getColumnIndex(tableColumn));
//                } else if (type.equals(Byte.class) || type.getName().equals("byte")) {
//                    value = Byte.valueOf(cursor.getString(cursor.getColumnIndex(tableColumn)));
//                } else if (type.equals(Short.class) || type.getName().equals("short")) {
//                    value = cursor.getShort(cursor.getColumnIndex(tableColumn));
//                } else if (type.equals(Long.class) || type.getName().equals("long")) {
//                    value = cursor.getLong(cursor.getColumnIndex(tableColumn));
//                } else if (type.equals(java.sql.Date.class)) {
//                    value = java.sql.Date.valueOf(cursor.getString(cursor.getColumnIndex(tableColumn)));
//                } else if (type.equals(BigDecimal.class)) {
//                    value = BigDecimal.valueOf(cursor.getLong(cursor.getColumnIndex(tableColumn)));
//                }
//                method.invoke(t, value);
//            }
//            return t;
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (InstantiationException e) {
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

    /**
     * 根据对象获取 ContentValues   ids TABLE_ID 'value'
     *
     * @param t   对象
     * @param <T> 泛型对象
     * @return ContentValues用来操作SQLite数据库
     */

    public <T> ContentValues getContentValues(T t, String... ids) {
        Class<?> c = t.getClass();
        if (List.class.isAssignableFrom(c)) {
            Log.d(TAG, "getContentValues: ");
        }
        String table_name = JavaReflectUtil.getClassName(c).toUpperCase();
        String sql = "SELECT MAX(ID)+1 AS ID FROM " + table_name;
        String id = getID(sql);
        List<Map<String, Object>> allInfo = JavaReflectUtil.getAllFiledInfo(t);
        ContentValues contentValues = new ContentValues();
        for (int i = 0; i < ids.length; i += 2) {
            contentValues.put(ids[i], ids[i + 1]);
        }
        for (int i = 0; i < allInfo.size(); i++) {
            Map<String, Object> info = allInfo.get(i);
            String column = (String) info.get("name");
            Class type = (Class) info.get("type");
            Object value = info.get("value");
            String typeInfo = type.getName();
            if (isBase(typeInfo)) {  //是否基本类型
                if (value == null) {
                    contentValues.putNull(column);
                } else {
                    contentValues.put(column, String.valueOf(value));
                }
            } else if (typeInfo.equals("java.util.List")) {
                insertAll((List) value, table_name + "_ID", id);
            } else {
                insert(value, table_name + "_ID", id);
            }
        }
        contentValues.remove("id");
        return contentValues;
    }

    /**
     * 插入一条数据
     *
     * @param <T> 泛型对象
     * @param t   要插入的对象
     * @return [影响的行数]he row ID of the newly inserted row, or -1 if an error occurred
     */
    public <T> long insert(T t, String... ids) {
        if (t == null) {
            return -1;
        }
        Class<?> c = t.getClass();
        String TABLE_NAME = JavaReflectUtil.getClassName(c).toUpperCase();
        ContentValues contentValues = getContentValues(t, ids);
        long num = 0;
        try {
            openDB();
            num = sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return num;
    }

/**
 *  Class<?> c = t.getClass();
 String TABLE_NAME = JavaReflectUtil.getClassName(c).toUpperCase();
 List<Map<String, Object>> allInfo = JavaReflectUtil.getAllFiledInfo(t);
 String sql = "select max(ID) from " + TABLE_NAME;
 Object[] listType = JavaReflectUtil.getAttributeListType(c);
 int listItem = 0;
 for (int i = 0; i < allInfo.size(); i++) {
 Map<String, Object> info = allInfo.get(i);
 String column = (String) info.get("name");
 Class type = (Class) info.get("type");
 Object value = info.get("value");
 String typeInfo = type.getName();
 if (isBase(typeInfo)) {  //是否基本类型

 } else if (typeInfo.equals("java.util.List")) {
 Class listClass = (Class) listType[listItem++];

 } else {
 Class<?> newClass;
 try {
 newClass = Class.forName(typeInfo);
 } catch (ClassNotFoundException e) {
 e.printStackTrace();
 }

 }

 }
 */
}
