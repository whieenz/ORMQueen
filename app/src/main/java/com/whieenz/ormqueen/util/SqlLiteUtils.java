package com.whieenz.ormqueen.util;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wuzhigang on 2017-08-24.
 */
public class SqlLiteUtils {
    private static String TAG = "CursorUtils";

    /**
     * 游标转Map .
     *
     * @param c .
     * @return .
     */
    public static Map cursor2Map(Cursor c) {
        if (c.getCount() == 0) {
            return null;
        }
        String[] names = c.getColumnNames();
        c.moveToNext();
        final Map retMap = new HashMap();
        for (final String name : names) {
            String val = c.getString(c.getColumnIndex(name));
            if (!StringUtil.isEmpty(val)) {
                retMap.put(name.toLowerCase(), val);
            }
        }
        return retMap;
    }

    public static void cursor2List(Cursor c, List<Map> inList) {
        if (c.getCount() > 0) {
            String[] names = c.getColumnNames();
            final List<Map> list = new ArrayList<Map>();
            while (c.moveToNext()) {
                final Map map = new HashMap();
                for (final String name : names) {
                    String val = c.getString(c.getColumnIndex(name));
                    if (!StringUtil.isEmpty(val)) {
                        map.put(name.toLowerCase(), val);
                    }
                }
                inList.add(map);
            }
        }
    }

    /**
     * 对象转ContentValues .
     *
     * @param object
     * @return
     */
    public static ContentValues vo2ContentValues(Object object) {
        ContentValues values = new ContentValues();
        final Class cls = object.getClass();
        final Method[] methods = cls.getDeclaredMethods();
        for (int i = 0; i < methods.length; i++) {
            final Method method = methods[i];
            final String methodName = method.getName();
            if (methodName.startsWith("get")) {
                // 属性名
                final StringBuilder filedname = new StringBuilder();
                final String name = methodName.substring(methodName.indexOf("get") + "get".length());
                filedname.append(name.substring(0, 1).toLowerCase()).append(name.substring(1));
                try {
                    if (method.invoke(object) == null) {
                        continue;
                    }
                    values.put(filedname.toString(), StringUtil.getStrim(method.invoke(object)));
                } catch (final Exception e) {
                    throw new RuntimeException("反射传递参数错误", e);
                }

            }
        }
        return values;
    }

    /**
     * 创建表默认会创建一个列名为id的列，主键，自动增长
     *
     * @param db
     * @param tableName
     * @param c
     * @param <T>
     */
    public static <T> void createTable(SQLiteDatabase db, String tableName, Class<T> c) {
        String[] column = JavaReflectUtil.getAttributeNames(c);
        Object[] type = JavaReflectUtil.getAttributeType(c);
        String sql = "CREATE TABLE IF NOT EXISTS " + tableName + "(";
        sql += "id  Integer PRIMARY KEY AUTOINCREMENT,";
        for (int i = 0; i < column.length; i++) {
            if (!"id".equals(column[i])) {
                if (i != column.length - 1) {
                    sql += column[i] + " TEXT ,";
                } else {
                    sql += column[i] + " TEXT ";
                }
            }
        }
        sql += ")";
        db.execSQL(sql);
    }
}
