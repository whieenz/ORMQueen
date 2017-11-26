package com.whieenz.ormqueen.bean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by asus on 2017/11/25.
 */

public class MultiBean {

    private String entityid;
    private BigDecimal sl;
    private Date time;
    private boolean state;
    private int age;
    private long mLong;
    private float mFloat;
    private byte mByte;
    private MaterialXjListBean xjListBean1 = new MaterialXjListBean();
    private MaterialXjListBean xjListBean2;
    private List<MaterialXjListBean> xjListBeans = new ArrayList<>();
    private EntitySlBean entitySlBean1 = new EntitySlBean();
    private EntitySlBean entitySlBean2;
    private List<EntitySlBean> entitySlBeans;


    public String getEntityid() {
        return entityid;
    }

    public void setEntityid(String entityid) {
        this.entityid = entityid;
    }

    public BigDecimal getSl() {
        return sl;
    }

    public void setSl(BigDecimal sl) {
        this.sl = sl;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public long getmLong() {
        return mLong;
    }

    public void setmLong(long mLong) {
        this.mLong = mLong;
    }

    public float getmFloat() {
        return mFloat;
    }

    public void setmFloat(float mFloat) {
        this.mFloat = mFloat;
    }

    public byte getmByte() {
        return mByte;
    }

    public void setmByte(byte mByte) {
        this.mByte = mByte;
    }

    public MaterialXjListBean getXjListBean1() {
        return xjListBean1;
    }

    public void setXjListBean1(MaterialXjListBean xjListBean1) {
        this.xjListBean1 = xjListBean1;
    }

    public MaterialXjListBean getXjListBean2() {
        return xjListBean2;
    }

    public void setXjListBean2(MaterialXjListBean xjListBean2) {
        this.xjListBean2 = xjListBean2;
    }

    public List<MaterialXjListBean> getXjListBeans() {
        return xjListBeans;
    }

    public void setXjListBeans(List<MaterialXjListBean> xjListBeans) {
        this.xjListBeans = xjListBeans;
    }

    public EntitySlBean getEntitySlBean1() {
        return entitySlBean1;
    }

    public void setEntitySlBean1(EntitySlBean entitySlBean1) {
        this.entitySlBean1 = entitySlBean1;
    }

    public EntitySlBean getEntitySlBean2() {
        return entitySlBean2;
    }

    public void setEntitySlBean2(EntitySlBean entitySlBean2) {
        this.entitySlBean2 = entitySlBean2;
    }

    public List<EntitySlBean> getEntitySlBeans() {
        return entitySlBeans;
    }

    public void setEntitySlBeans(List<EntitySlBean> entitySlBeans) {
        this.entitySlBeans = entitySlBeans;
    }
}
