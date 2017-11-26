package com.whieenz.ormqueen.util;

import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by wuzhigang on 2017-06-16.
 */
public class ReflectUtil {
    /**
     * 私有构造方法.
     */
    private ReflectUtil() {
    }

    /**
     * 通过反射获取对象setXXX(yyy)方法，并给对象赋值.<br>
     *
     * @param o
     *            反射对象
     * @param fieldName
     *            set方法名
     * @param value
     *            参数值
     */
    public static void invokeSetMethod(final Object o, final String fieldName, final Object value) {
        final String temp_fieldName = fieldName.toLowerCase();
        final StringBuilder methodname = new StringBuilder();
        methodname.append("set").append(temp_fieldName.substring(0, 1).toUpperCase())
                .append(temp_fieldName.substring(1));
        try {
            final Field field = o.getClass().getDeclaredField(temp_fieldName);
            final Class<?> fieldtype = field.getType();
            final Method method = o.getClass().getMethod(methodname.toString(), fieldtype);
            Object newvalue = value;
            if (newvalue.getClass() != fieldtype) {
                // 如果要保存的值和fieldtype不匹配,则进行下面转换
                newvalue = typeConver(fieldtype, value);
            }
            method.invoke(o, newvalue);
        } catch (final Exception e) {
            throw new RuntimeException("方法调用失败。 " +  e.getMessage());
        }
    }

    /**
     * 通过反射获取对象getXXX()方法，并返回调用结果. <br>
     *
     * @param o
     *            反射对象
     * @param fieldName
     *            set方法名
     * @return getXXX方法返回对象结果
     */
    public static Object invokeGetMethod(final Object o, final String fieldName) {
        final String temp_fieldName = fieldName.toLowerCase();
        final StringBuilder methodname = new StringBuilder();
        methodname.append("get").append(temp_fieldName.substring(0, 1).toUpperCase())
                .append(temp_fieldName.substring(1));
        try {
            final Method method = o.getClass().getMethod(methodname.toString());
            return method.invoke(o);
        } catch (final Exception e) {
            throw new RuntimeException("方法调用失败。 "+ e.getMessage());
        }
    }

    /**
     * 通过反射将map的值封装到对象中.
     *
     * @param form
     *            反射对象
     * @param conMap
     *            map值
     */
    public static void reflectSetMethod(final Object form, final Map<String, Object> conMap) {
        final Class cls = form.getClass();
        final Method[] methods = cls.getDeclaredMethods();
        for (int i = 0; i < methods.length; i++) {
            final Method method = methods[i];
            final String methodName = method.getName();
            if (methodName.startsWith("set")) {
                // 属性名
                final StringBuilder filedname = new StringBuilder();
                final String name = methodName.substring(methodName.indexOf("set") + "set".length());
                filedname.append(name.substring(0, 1).toLowerCase()).append(name.substring(1));
                final Object value = conMap.get(filedname.toString());
                if (value == null) {
                    continue;
                }
                Field field;
                try {
                    field = form.getClass().getDeclaredField(filedname.toString());
                    final Class<?> fieldtype = field.getType();

                    method.invoke(form, typeConver(fieldtype, value));
                } catch (final Exception e) {
                    throw new RuntimeException("反射传递参数错误", e);
                }

            }
        }
    }

    /**
     * 排量通过反射将map的值封装到对象中.
     *
     * @param c
     *            返回结果集中的对象类型
     * @param newResList
     *            map集合list
     * @return 对象集合list
     */
    public static List reflectSetMethodOfList(final Class c, final List<Map> newResList) {
        final List list = new ArrayList();
        Object o;
        if (newResList == null) {
            return list;
        }
        for (final Map map : newResList) {
            try {
                o = c.newInstance();
            } catch (final Exception e) {
                throw new RuntimeException("实例化对象失败" + c.getName(), e);
            }
            reflectSetMethod(o, map);
            list.add(o);
        }
        return list;
    }

    /**
     * 通过反射将对象的值封装到map中.
     *
     * @param bo
     *            反射对象
     * @param conMap
     *            map值(key全部为小写)
     */
    public static void reflectGetMethod(final Object bo, final Map<String, Object> conMap) {
        final Class cls = bo.getClass();
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
                    if (method.invoke(bo) == null) {
                        continue;
                    }
                    conMap.put(filedname.toString(), method.invoke(bo));
                } catch (final Exception e) {
                    throw new RuntimeException("反射传递参数错误", e);
                }

            }
        }

    }

    /**
     * 获取当前对象或者父类对象的属性.
     *
     * @param o
     *            对象
     * @param fieldName
     *            字段属性名
     * @return filed
     */
    public static Field getDeclaredField(final Object o, final String fieldName) {
        Field field = null;
        Class cls = o.getClass();
        for (; cls != Object.class; cls = cls.getSuperclass()) {
            try {
                field = cls.getDeclaredField(fieldName);
                return field;
            } catch (final Exception e) {
                // 此处异常不能处理
                e.printStackTrace();
                Log.i("tefectUtils", "获取当前对象或者父类对象属性失败,其属性名称:");
            }
        }
        return field;
    }

    /**
     * TODO 类型转换. 注：目前只支持参数为String、Integer(int)、Double(double)、BigDecimal的转换.
     *
     * @param <T>
     *            转换的类型
     * @param t
     *            转换类型
     * @param o
     *            转换值
     * @return 返回转换的值
     */
    @SuppressWarnings("unchecked")
    private static <T> T typeConver(final T t, final Object o) {
        if(o==null) {
            return null;
        }
        final String temp = String.valueOf(o);
        if (t == String.class) {
            return (T) String.valueOf(o);
        }
        if (t == Short.class || t.toString().equals("short")) {
            return (T) Short.valueOf(temp);
        }
        if (t == Integer.class || t.toString().equals("int")) {
            return (T) Integer.valueOf(temp);
        }
        if (t == Long.class || t.toString().equals("long")) {
            return (T) Long.valueOf(temp);
        }
        if (t == Float.class || t.toString().equals("float")) {
            return (T) Float.valueOf(temp);
        }
        if (t == Double.class || t.toString().equals("double")) {
            return (T) Double.valueOf(temp);
        }
        if (t == java.math.BigDecimal.class) {
            final DecimalFormat df = new DecimalFormat("#.##########");
            // DecimalFormat 不支持格式化String，必须提前转化为bigDecimal
            return (T) new java.math.BigDecimal(df.format(new java.math.BigDecimal(temp)));
        }
        if (t == java.util.Date.class) {
            final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            try {
                return (T) df.parse(String.valueOf(o));
            } catch (final ParseException e) {
                e.printStackTrace();
            }
        }
        return (T) o;
    }

}
