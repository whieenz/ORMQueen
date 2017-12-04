package com.whieenz.ormqueenlibrary.inter;

import java.util.List;

/**
 * Created by whieenz on 2017/11/30.
 * 作用：
 */

public interface OnObjectDivisionListener {
    void afterBase(String column, Class clzaa, String value);

    void afterObject(String column, Class clzaa, Object value);

    void afterList(String column, Class clzaa, List value);
}
