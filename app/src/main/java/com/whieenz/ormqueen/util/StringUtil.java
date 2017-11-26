package com.whieenz.ormqueen.util;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by heziwen on 2017/8/15.
 */

public class StringUtil {

    /**
     * 获取JSON中的String数据
     * @param jsonObject
     * @param key
     * @return
     */
    public static String getJsonStr(JSONObject jsonObject , String key){
        if (jsonObject == null ){
            return "";
        }
        Object obj = null;
        try {
            obj = jsonObject.get(key);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (obj == null){
            return "";
        }else{
            return getStrim(obj);
        }
    }

    /**
     * 获取String 同时执行 trim去掉空格
     * @param obj
     * @return
     */
    public static String getStrim(Object obj) {
        if (obj == null ){
            return "";
        } else {
            return String.valueOf(obj).trim();
        }
    }
    /**
     * 获取String
     * @param obj
     * @return
     */
    public static String getStr(Object obj) {
        if (obj == null ){
            return "";
        } else {
            return String.valueOf(obj);
        }
    }
    /**
     * 获取当前时间
     * @return
     */
    public static String getNowTime(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String time = formatter.format(curDate);
        return time;
    }
    /**
     * 判断给定字符串是否空白串。 空白串是指由空格、制表符、回车符、换行符组成的字符串 若输入字符串为null或空字符串，返回true
     *
     * @param input
     * @return boolean
     */
    public static boolean isEmpty(String input) {
        return input == null || input.length() < 1;
    }
    /**
     * 获取当前日期
     * @return
     */
    public static String getNowDay(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String time = formatter.format(curDate);
        return time;
    }

}
