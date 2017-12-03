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
    private MaterialXjListBean xjListBean1 = new MaterialXjListBean();
    private MaterialXjListBean xjListBean2;
    private List<MaterialXjListBean> xjListBeans = new ArrayList<>();

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

}
