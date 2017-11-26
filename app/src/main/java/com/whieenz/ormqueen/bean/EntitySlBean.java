package com.whieenz.ormqueen.bean;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by wuzhigang on 2017-07-07.
 */
public class EntitySlBean implements Serializable {
    public EntitySlBean(){
        this.state="0";
    }
    public EntitySlBean(String initentityid, BigDecimal initsl) {
        this.state = "0";
        this.entityid = initentityid;
        this.sl = initsl;
    }
    private String entityid;
    private BigDecimal sl;
    /**
     * 状态 0：未匹配，1：已匹配
     */
    private String state;
    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }
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
}
