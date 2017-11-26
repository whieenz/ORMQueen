package com.whieenz.ormqueen.bean;

import android.support.annotation.NonNull;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by wuzhigang on 2017-07-03.
 */
public class MaterialXjListBean  implements Serializable,Comparable<MaterialXjListBean> {
    /**
     * 标签管理维度 .
     */
    private String bqglwd;

    /**
     * 需求申请单号 .
     */
    private String sqbh;

    /**
     * 项目名称 .
     */
    private String gcxm;

    /**
     * 物资编码 .
     */
    private String wzbm;

    /**
     * 物资对象 .
     */
    private String wzdx;

    /**
     * 规格型号 .
     */
    private String ggxh;

    /**
     * 可领用数量 .
     */
    private BigDecimal klysl;

    /**
     * 计量单位 .
     */
    private String jldw;
    /**
     * 计量单位mc .
     */
    private String jldwmc;

    /**
     * 领购资产 .
     */
    private String lgzc;

    /**
     * 施工合同 .
     */
    private String sght;

    /**
     * 材质 .
     */
    private String cz;

    /**
     * 图号 .
     */
    private String tuhao;

    /**
     * 本次申请数量 .
     */
    private BigDecimal bcsqsl;

    /**
     * 来源单号 .
     */
    private String lydh;

    /**
     * 标段 .
     */
    private String bddx;

    /**
     * 机组 .
     */
    private String jzdx;

    /**
     * 工程或费用名称 .
     */
    private String wbsdx;

    /**
     * 概算编码 .
     */
    private String wbsbm;

    /**
     * 安装部位 .
     */
    private String azbw;

    /**
     * 来源单据Id .
     */
    private String ywid;

    /**
     * 来源单据类型id .
     */
    private String typeId;

    /**
     * 来源单据子表 .
     */
    private String zbid;

    /**
     * 计价方法 .
     */
    private String jjff;

    /**
     * 基建工程明细 .
     */
    private String jjgcmx;

    /**
     * 主键 .
     */
    private String gid;

    /**
     * 概算类型 .
     */
    private String gslx;

    /**
     * 费用类型 .
     */
    private String fylx;

    /**
     * 物料性质 .
     */
    private String wlxz;

    /**
     * 物资分类 .
     */
    private String wzfl;

    /**
     * 来源单据ID .
     */
    private String lydjtypeid;

    /**
     * 来源单据GID .
     */
    private String lydjgid;

    /**
     * 来源单据类型ID  .
     */
    private String lydjywid;

    /**
     * 来源单据子表名 .
     */
    private String lydjzbid;

    /**
     * 来源单据类型 .
     */
    private String lydjlx;

    /**
     * 施工单位 .
     */
    private String sgdw;

    /**
     * 是否抵扣 .
     */
    private String sfdk;

    /**
     * 物资名称 .
     */
    private String wzmc;

    /**
     * 物资大类 .
     */
    private String wldl;

    /**
     * 是否超发料 .
     */
    private String sfcfl;

    /**
     * 需求明细批号 .
     */
    private String xqmxph;

    /**
     * 需求日期 .
     */
    private String xqrq;

    /**
     * 对应仓库 .
     */
    private String ckdxids;

    /**
     * 税率 .
     */
    private BigDecimal taxrate;

    /**
     * property0 .
     */
    private Date xqrqDate;

    /**
     * 出库数量，关联来源单界面有出库数量时，使用该字段 .
     */
    private BigDecimal cksl;

    /**
     * 项目名称名称 .
     */
    private String gcxmmc;

    /**
     * 供应商 .
     */
    private String vendor;

    /**
     * 供应商名称 .
     */
    private String gysmc;

    /**
     * 合同对象 .
     */
    private String htdx;

    /**
     * 合同编号 .
     */
    private String htbh;

    /**
     * 合同名称 .
     */
    private String htmc;

    /**
     * 库位对象 .
     */
    private String kwdx;

    /**
     * 库位名称 .
     */
    private String kwmc;

    /**
     * 批次号码 .
     */
    private String pchm;
    /**
     * 启用批次管理 .
     */
    private String qypcgzgl;

    /**
     * 物资实物数量 .
     */
    private List<EntitySlBean> wlentitySl;

    /**
     * 匹配数量 .
     */
    private BigDecimal ppsl;
    /**
     * 预计归还时间.
     */
    private long yjghsj;

    public String getSqbh() {
        return sqbh;
    }

    public void setSqbh(String sqbh) {
        this.sqbh = sqbh;
    }

    public String getGcxm() {
        return gcxm;
    }

    public void setGcxm(String gcxm) {
        this.gcxm = gcxm;
    }

    public String getWzbm() {
        return wzbm;
    }

    public void setWzbm(String wzbm) {
        this.wzbm = wzbm;
    }

    public String getWzdx() {
        return wzdx;
    }

    public void setWzdx(String wzdx) {
        this.wzdx = wzdx;
    }

    public String getGgxh() {
        return ggxh;
    }

    public void setGgxh(String ggxh) {
        this.ggxh = ggxh;
    }

    public BigDecimal getKlysl() {
        return klysl;
    }

    public void setKlysl(BigDecimal klysl) {
        this.klysl = klysl;
    }

    public String getJldw() {
        return jldw;
    }

    public void setJldw(String jldw) {
        this.jldw = jldw;
    }

    public String getLgzc() {
        return lgzc;
    }

    public void setLgzc(String lgzc) {
        this.lgzc = lgzc;
    }

    public String getSght() {
        return sght;
    }

    public void setSght(String sght) {
        this.sght = sght;
    }

    public String getCz() {
        return cz;
    }

    public void setCz(String cz) {
        this.cz = cz;
    }

    public String getTuhao() {
        return tuhao;
    }

    public void setTuhao(String tuhao) {
        this.tuhao = tuhao;
    }

    public BigDecimal getBcsqsl() {
        return bcsqsl;
    }

    public void setBcsqsl(BigDecimal bcsqsl) {
        this.bcsqsl = bcsqsl;
    }

    public String getLydh() {
        return lydh;
    }

    public void setLydh(String lydh) {
        this.lydh = lydh;
    }

    public String getBddx() {
        return bddx;
    }

    public void setBddx(String bddx) {
        this.bddx = bddx;
    }

    public String getJzdx() {
        return jzdx;
    }

    public void setJzdx(String jzdx) {
        this.jzdx = jzdx;
    }

    public String getWbsdx() {
        return wbsdx;
    }

    public void setWbsdx(String wbsdx) {
        this.wbsdx = wbsdx;
    }

    public String getWbsbm() {
        return wbsbm;
    }

    public void setWbsbm(String wbsbm) {
        this.wbsbm = wbsbm;
    }

    public String getAzbw() {
        return azbw;
    }

    public void setAzbw(String azbw) {
        this.azbw = azbw;
    }

    public String getYwid() {
        return ywid;
    }

    public void setYwid(String ywid) {
        this.ywid = ywid;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getZbid() {
        return zbid;
    }

    public void setZbid(String zbid) {
        this.zbid = zbid;
    }

    public String getJjff() {
        return jjff;
    }

    public void setJjff(String jjff) {
        this.jjff = jjff;
    }

    public String getJjgcmx() {
        return jjgcmx;
    }

    public void setJjgcmx(String jjgcmx) {
        this.jjgcmx = jjgcmx;
    }

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public String getGslx() {
        return gslx;
    }

    public void setGslx(String gslx) {
        this.gslx = gslx;
    }

    public String getFylx() {
        return fylx;
    }

    public void setFylx(String fylx) {
        this.fylx = fylx;
    }

    public String getWlxz() {
        return wlxz;
    }

    public void setWlxz(String wlxz) {
        this.wlxz = wlxz;
    }

    public String getWzfl() {
        return wzfl;
    }

    public void setWzfl(String wzfl) {
        this.wzfl = wzfl;
    }

    public String getLydjtypeid() {
        return lydjtypeid;
    }

    public void setLydjtypeid(String lydjtypeid) {
        this.lydjtypeid = lydjtypeid;
    }

    public String getLydjgid() {
        return lydjgid;
    }

    public void setLydjgid(String lydjgid) {
        this.lydjgid = lydjgid;
    }

    public String getLydjywid() {
        return lydjywid;
    }

    public void setLydjywid(String lydjywid) {
        this.lydjywid = lydjywid;
    }

    public String getLydjzbid() {
        return lydjzbid;
    }
    public void setLydjzbid(String lydjzbid) {
        this.lydjzbid = lydjzbid;
    }
    public String getLydjlx() {
        return lydjlx;
    }
    public void setLydjlx(String lydjlx) {
        this.lydjlx = lydjlx;
    }
    public String getSgdw() {
        return sgdw;
    }
    public void setSgdw(String sgdw) {
        this.sgdw = sgdw;
    }
    public String getSfdk() {
        return sfdk;
    }
    public void setSfdk(String sfdk) {
        this.sfdk = sfdk;
    }
    public String getWzmc() {
        return wzmc;
    }
    public void setWzmc(String wzmc) {
        this.wzmc = wzmc;
    }
    public String getWldl() {
        return wldl;
    }
    public void setWldl(String wldl) {
        this.wldl = wldl;
    }
    public String getSfcfl() {
        return sfcfl;
    }
    public void setSfcfl(String sfcfl) {
        this.sfcfl = sfcfl;
    }
    public String getXqmxph() {
        return xqmxph;
    }
    public void setXqmxph(String xqmxph) {
        this.xqmxph = xqmxph;
    }
    public String getXqrq() {
        return xqrq;
    }
    public void setXqrq(String xqrq) {
        this.xqrq = xqrq;
    }
    public String getCkdxids() {
        return ckdxids;
    }
    public void setCkdxids(String ckdxids) {
        this.ckdxids = ckdxids;
    }
    public BigDecimal getTaxrate() {
        return taxrate;
    }
    public void setTaxrate(BigDecimal taxrate) {
        this.taxrate = taxrate;
    }
    public Date getXqrqDate() {
        return xqrqDate;
    }
    public void setXqrqDate(Date xqrqDate) {
        this.xqrqDate = xqrqDate;
    }
    public BigDecimal getCksl() {
        return cksl;
    }
    public void setCksl(BigDecimal cksl) {
        this.cksl = cksl;
    }
    public String getGcxmmc() {
        return gcxmmc;
    }
    public void setGcxmmc(String gcxmmc) {
        this.gcxmmc = gcxmmc;
    }
    public String getVendor() {
        return vendor;
    }
    public void setVendor(String vendor) {
        this.vendor = vendor;
    }
    public String getGysmc() {
        return gysmc;
    }
    public void setGysmc(String gysmc) {
        this.gysmc = gysmc;
    }
    public String getHtdx() {
        return htdx;
    }
    public void setHtdx(String htdx) {
        this.htdx = htdx;
    }
    public String getHtbh() {
        return htbh;
    }
    public void setHtbh(String htbh) {
        this.htbh = htbh;
    }
    public String getHtmc() {
        return htmc;
    }
    public void setHtmc(String htmc) {
        this.htmc = htmc;
    }
    public String getKwdx() {
        return kwdx;
    }
    public void setKwdx(String kwdx) {
        this.kwdx = kwdx;
    }
    public String getKwmc() {
        return kwmc;
    }
    public void setKwmc(String kwmc) {
        this.kwmc = kwmc;
    }
    public String getPchm() {
        return pchm;
    }
    public void setPchm(String pchm) {
        this.pchm = pchm;
    }

    public String getJldwmc() {
        return jldwmc;
    }


    public void setJldwmc(String jldwmc) {
        this.jldwmc = jldwmc;
    }
    public String getBqglwd() {
        return bqglwd;
    }
    public void setBqglwd(String bqglwd) {
        this.bqglwd = bqglwd;
    }
    public String getQypcgzgl() {
        return qypcgzgl;
    }

    public void setQypcgzgl(String qypcgzgl) {
        this.qypcgzgl = qypcgzgl;
    }

    public long getYjghsj() {
        return yjghsj;
    }

    public void setYjghsj(long yjghsj) {
        this.yjghsj = yjghsj;
    }

    public List<EntitySlBean> getWlentitySl() {
        return wlentitySl;
    }
    public void setWlentitySl(List<EntitySlBean> wlentitySl) {
        this.wlentitySl = wlentitySl;
    }
    public void addWlentitySl(List<EntitySlBean> wlentitySl) {
        if (this.wlentitySl!=null){
            this.wlentitySl.addAll(wlentitySl);
        }

    }
    public BigDecimal getPpsl() {
        return ppsl;
    }
    public void setPpsl(BigDecimal ppsl) {
        this.ppsl = ppsl;
    }

    @Override
    public int compareTo(@NonNull MaterialXjListBean another) {
        if (this.getWzbm().compareTo(another.getWzbm()) == 0){
            return this.getKwmc().compareTo(another.getKwmc());
        }
        return this.getWzbm().compareTo(another.getWzbm());
    }

    public MaterialXjListBean deepCopy(){
       MaterialXjListBean bean = new MaterialXjListBean();
       bean.setAzbw(this.azbw);
       bean.setBcsqsl(this.bcsqsl);
       bean.setBddx(this.bddx);
       bean.setBqglwd(this.bqglwd);
       bean.setCkdxids(this.ckdxids);
       bean.setCksl(this.cksl);
       bean.setCz(this.cz);
       bean.setFylx(this.fylx);
       bean.setGid(this.gid);
       bean.setGslx(this.gslx);
       bean.setGysmc(this.gysmc);
       bean.setGgxh(this.ggxh);
       bean.setGcxm(this.gcxm);
       bean.setGcxmmc(this.gcxmmc);
       bean.setHtbh(this.htbh);
       bean.setHtdx(this.htdx);
       bean.setHtmc(this.htmc);
       bean.setJldw(this.jldw);
       bean.setJldwmc(this.jldwmc);
       bean.setJjff(this.jjff);
       bean.setJjgcmx(this.jjgcmx);
       bean.setJzdx(this.jzdx);
       bean.setKlysl(this.klysl);
       bean.setKwdx(this.kwdx);
       bean.setKwmc(this.kwmc);
       bean.setLydjlx(this.lydjlx);
       bean.setLydh(this.lydh);
       bean.setLgzc(this.lgzc);
       bean.setLydjtypeid(this.lydjtypeid);
       bean.setLydjzbid(this.lydjzbid);
       bean.setLydjgid(this.lydjgid);
       bean.setLydjywid(this.lydjywid);
       bean.setPpsl(this.ppsl);
       bean.setPchm(this.pchm);
       bean.setSfcfl(this.sfcfl);
       bean.setSfdk(this.sfdk);
       bean.setSgdw(this.sgdw);
       bean.setSght(this.sght);
       bean.setSqbh(this.sqbh);
       bean.setTypeId(this.typeId);
       bean.setTuhao(this.tuhao);
       bean.setTaxrate(this.taxrate);
       bean.setVendor(this.vendor);
       bean.setWzmc(this.wzmc);
       bean.setWzfl(this.wzfl);
       bean.setWzdx(this.wzdx);
       bean.setWzbm(this.wzbm);
       bean.setWlxz(this.wlxz);
       bean.setWbsdx(this.wbsdx);
       bean.setWbsbm(this.wbsbm);
       bean.setWldl(this.wldl);
       //bean.setWlentitySl(this.wlentitySl);
       bean.setXqrqDate(this.xqrqDate);
       bean.setXqmxph(this.xqmxph);
       bean.setXqrq(this.xqrq);
       bean.setYwid(this.ywid);
       bean.setZbid(this.zbid);
       bean.setYjghsj(this.yjghsj);
       bean.setQypcgzgl(this.qypcgzgl);
        return bean;
   }
}
