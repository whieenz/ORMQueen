package com.whieenz.ormqueen;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.whieenz.LogCook;
import com.whieenz.ormqueen.bean.EntitySlBean;
import com.whieenz.ormqueen.bean.MaterialXjListBean;
import com.whieenz.ormqueen.bean.MultiBean;
import com.whieenz.ormqueen.util.DatabaseHelper;
import com.whieenz.ormqueen.util.SQLiteDbUtil;
import com.whieenz.ormqueen.util.StringUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        //SQLiteDbUtil.createTable(MaterialXjListBean.class);
        EntitySlBean slBean1 = new EntitySlBean();
        EntitySlBean slBean2 = new EntitySlBean();
        slBean1.setState("0");
        slBean1.setEntityid("Entityid1");
        slBean1.setSl(BigDecimal.ONE);
        slBean2.setSl(BigDecimal.TEN);
        slBean2.setEntityid("Entityid2");
        slBean2.setState("1");
        List<EntitySlBean> slBeens = new ArrayList<>();
        slBeens.add(slBean1);
        slBeens.add(slBean2);
        MaterialXjListBean xjListBean1 = new MaterialXjListBean();
        xjListBean1.setAzbw("ddd");
        xjListBean1.setYjghsj(11);
        xjListBean1.setCksl(BigDecimal.ONE);
        xjListBean1.setWzbm("wzbm");
        xjListBean1.setWzmc("wzmc");
        xjListBean1.setWlentitySl(slBeens);
        List<MaterialXjListBean> xjListBeens = new ArrayList<>();
        xjListBeens.add(xjListBean1);
        MultiBean multiBean = new MultiBean();
        multiBean.setSl(BigDecimal.ZERO);
        multiBean.setAge(10);
        multiBean.setState(true);
        multiBean.setXjListBean1(xjListBean1);
        multiBean.setXjListBean2(xjListBean1);
        multiBean.setXjListBeans(xjListBeens);
        DatabaseHelper helper = new DatabaseHelper(this);
        helper.insert(multiBean);
        List<MultiBean> multiBeens = helper.queryAll(MultiBean.class);
        LogCook.log(StringUtil.getStr(multiBeens));
        helper.deleteAll(MultiBean.class);
//        SQLiteDbUtil.getSQLiteDbUtil(this).createTable(MultiBean.class);
//        SQLiteDbUtil.getSQLiteDbUtil(this).exeInsert(multiBean);
//        List<MultiBean> query = SQLiteDbUtil.getSQLiteDbUtil(this).query(MultiBean.class);
//        int deleteAll = SQLiteDbUtil.getSQLiteDbUtil(this).deleteAll(MultiBean.class);
//        Log.d("", "onCreate: " + deleteAll);
        // JavaReflectUtil.getAttributeListType(MultiBean.class);

//        LogCook.v(TAG,"测试日志v");
//        LogCook.i(TAG,"测试日志i");
//        LogCook.d(TAG,"测试日志d");
//        LogCook.w(TAG,"测试日志w");
//        LogCook.e(TAG,"测试日志e");
//        LogCook.log("测试日志log");
    }
}
