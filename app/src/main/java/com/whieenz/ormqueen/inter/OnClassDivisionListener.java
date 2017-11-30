package com.whieenz.ormqueen.inter;

/**
 * Created by heziwen on 2017/11/30.
 * 作用：
 */

public interface OnClassDivisionListener {
    void afterBase(String column, Class clzaa, boolean isLast);

    void afterObject(String column, Class clzaa, boolean isLast);

    void afterList(String column, Class clzaa, boolean isLast);
}
