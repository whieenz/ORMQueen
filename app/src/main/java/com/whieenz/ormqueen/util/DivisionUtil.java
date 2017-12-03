package com.whieenz.ormqueen.util;

import com.whieenz.ormqueen.inter.OnClassDivisionListener;
import com.whieenz.ormqueen.inter.OnObjectDivisionListener;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by whieenz on 2017/12/3.
 *
 */

public class DivisionUtil {
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

    public static void divisionByClass(Class c, OnClassDivisionListener listener) {
        String[] column = JavaReflectUtil.getAttributeNames(c);
        Object[] type = JavaReflectUtil.getAttributeType(c);
        Object[] listType = JavaReflectUtil.getAttributeListType(c);
        int listItem = 0;
        for (int i = 0; i < column.length; i++) {
            String columnName = column[i];
            if (!"ID".equals(columnName)) {
                String typeInfo = ((Class) type[i]).getName();
                boolean isLast = i == column.length - 1;
                if (isBase(typeInfo)) {
                    listener.afterBase(columnName, (Class) type[i], isLast);
                } else if (typeInfo.equals("java.util.List")) {
                    Class listClass = (Class) listType[listItem++];
                    listener.afterList(columnName, listClass, isLast);
                } else {
                    Class<?> newClass;
                    try {
                        newClass = Class.forName(typeInfo);
                        listener.afterObject(columnName, newClass, isLast);
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static void divisionByObject(Object o, OnObjectDivisionListener listener) {
        List<Map<String, Object>> allInfo = JavaReflectUtil.getAllFiledInfo(o);
        Object[] listType = JavaReflectUtil.getAttributeListType(o.getClass());
        int listItem = 0;
        for (int i = 0; i < allInfo.size(); i++) {
            Map<String, Object> info = allInfo.get(i);
            String column = info.get("name").toString();
            Class type = (Class) info.get("type");
            Object value = info.get("value");
            String typeInfo = type.getName();
            if (isBase(typeInfo)) {
                listener.afterBase(column, type, value.toString());
            } else if (typeInfo.equals("java.util.List")) {
                Class listClass = (Class) listType[listItem++];
                listener.afterList(column, listClass, (List) value);
            } else {
                Class<?> newClass;
                try {
                    newClass = Class.forName(typeInfo);
                    listener.afterObject(column, newClass, value);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
