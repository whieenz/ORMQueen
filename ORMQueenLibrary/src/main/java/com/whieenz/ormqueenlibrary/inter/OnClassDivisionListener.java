package com.whieenz.ormqueenlibrary.inter;

/**
 * Created by whieenz on 2017/11/30.
 * 作用：
 */

public interface OnClassDivisionListener {
    void afterBase(String column, Class clzaa, boolean isLast);

    void afterObject(String column, Class clzaa, boolean isLast);

    void afterList(String column, Class clzaa, boolean isLast);
}
