package com.whieenz.ormqueen.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by heziwen on 2017-08-28.
 */
public class JavaReflectUtil {
    /**
     * 获取类的简名，不包含包名
     *
     * @param c 类
     * @return 类名
     */
    public static String getClassName(Class c) {
        if (c == null) {
            return null;
        }
        return c.getSimpleName();
    }

    /**
     * 获取类的属性名
     *
     * @param c 类
     * @return 所有属性的数组
     */
    public static String[] getAttributeNames(Class c) {
        if (c == null) {
            return null;
        }
        Field[] declaredFields = new Field[0];
        try {
            declaredFields = c.getDeclaredFields();
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        List<String> names = new ArrayList<>();
        for (int i = 0; i < declaredFields.length; i++) {
            /**忽略编译产生的属性**/
            if (declaredFields[i].isSynthetic()) {
                continue;
            }
            /**忽略serialVersionUID**/
            if (declaredFields[i].getName().equals("serialVersionUID")) {
                continue;
            }
            names.add(declaredFields[i].getName());
        }
        return names.toArray(new String[names.size()]);
    }

    /**
     * 获取类的属性类型
     *
     * @param c 类
     * @return 所有属性类型的数组
     */
    public static Class[] getAttributeType(Class c) {
        if (c == null) {
            return null;
        }
        Field[] declaredFields = new Field[0];
        try {
            declaredFields = c.getDeclaredFields();
        } catch (SecurityException e) {
            e.printStackTrace();
        }

        List<Object> types = new ArrayList<>();
        for (int i = 0; i < declaredFields.length; i++) {
            if (declaredFields[i].isSynthetic()) {
                continue;
            }
            if (declaredFields[i].getName().equals("serialVersionUID")) {
                continue;
            }
            types.add(declaredFields[i].getType());
        }
        return types.toArray(new Class[types.size()]);
    }

    /**
     * 获取类的属性类型
     *
     * @param c 类
     * @return 所有属性类型的数组
     */
    public static Class[] getAttributeListType(Class c) {
        if (c == null) {
            return null;
        }
        Field[] declaredFields = new Field[0];
        try {
            declaredFields = c.getDeclaredFields();
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        List<Object> types = new ArrayList<>();
        for (Field f : declaredFields) {
            f.setAccessible(true);
            if (f.getType() == java.util.List.class) {
                // 如果是List类型，得到其Generic的类型
                Type genericType = f.getGenericType();
                if (genericType == null) continue;
                // 如果是泛型参数的类型
                if (genericType instanceof ParameterizedType) {
                    ParameterizedType pt = (ParameterizedType) genericType;
                    //得到泛型里的class类型对象
                    Class<?> genericClazz = (Class<?>) pt.getActualTypeArguments()[0];
                    types.add(genericClazz);
                    //String name = genericClazz.getName();
                }
            }
        }
        return types.toArray(new Class[types.size()]);
    }

    /**
     * 获取类的属性名获取属性值
     *
     * @param o         类对象
     * @param attribute 属性名称
     * @return 所对应的属性值
     */
    public static Object getValueByAttribute(Object o, String attribute) {
        if (o == null) {
            return null;
        }
        if (attribute == null || attribute.isEmpty()) {
            return null;
        }
        try {
            String getMethodName = "get" + attribute.substring(0, 1).toUpperCase() + attribute.substring(1);
            Method method = o.getClass().getMethod(getMethodName, new Class[]{});
            Object value = method.invoke(o, new Object[]{});
            return value;
        } catch (NoSuchMethodException e) {
            String getMethodName = "is" + attribute.substring(0, 1).toUpperCase() + attribute.substring(1);
            try {
                Method method = o.getClass().getMethod(getMethodName, new Class[]{});
                Object value = method.invoke(o, new Object[]{});
                return value;
            } catch (NoSuchMethodException e1) {
                e1.printStackTrace();
            } catch (InvocationTargetException e1) {
                e1.printStackTrace();
            } catch (IllegalAccessException e1) {
                e1.printStackTrace();
            }
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取类的属性名获取属性值
     *
     * @param o 类对象
     * @return 所对应的属性值
     */
    public static List<Map<String, Object>> getAllFiledInfo(Object o) {
        if (o == null) {
            return null;
        }
        String[] attributes = getAttributeNames(o.getClass());
        Object[] attributeTypes = getAttributeType(o.getClass());
        List<Map<String, Object>> allFiledInfos = new ArrayList<>();
        for (int i = 0; i < attributes.length; i++) {
            Map<String, Object> allFiledInfo = new HashMap<>();
            allFiledInfo.put("name", attributes[i]);
            allFiledInfo.put("type", attributeTypes[i]);
            allFiledInfo.put("value", getValueByAttribute(o, attributes[i]));
            allFiledInfos.add(allFiledInfo);
        }
        return allFiledInfos;
    }

}
