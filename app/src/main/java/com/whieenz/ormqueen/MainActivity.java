package com.whieenz.ormqueen;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.whieenz.ormqueen.bean.MaterialXjListBean;
import com.whieenz.ormqueen.bean.MultiBean;
import com.whieenz.ormqueen.util.JavaReflectUtil;
import com.whieenz.ormqueen.util.SQLiteDbUtil;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //SQLiteDbUtil.createTable(MaterialXjListBean.class);
        SQLiteDbUtil.createTable(MultiBean.class);
       // JavaReflectUtil.getAttributeListType(MultiBean.class);
    }
}
