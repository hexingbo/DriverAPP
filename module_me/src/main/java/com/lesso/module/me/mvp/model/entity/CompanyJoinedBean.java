package com.lesso.module.me.mvp.model.entity;

/**
 * @Author :hexingbo
 * @Date :2019/11/16
 * @FileName： CompanyJoinBean
 * @Describe :物流公司对象
 */
public class CompanyJoinedBean {

    private String companyId;//公司ID
    private String companyName;//公司名称
    private String driverId;//司机ID
    private String joinDate;//加盟时间
    private int joinSource;//加盟来源(0：物流公司邀请司机；1：司机加盟物流公司)
    private String auditStatus;//审核状态
    private String guid;//加盟主键ID
    private String auditStatusCode;//B:审核中、待确认；F:审核不通过、拒绝；D：审核通过、同意

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(String joinDate) {
        this.joinDate = joinDate;
    }

    public int getJoinSource() {
        return joinSource;
    }

    public void setJoinSource(int joinSource) {
        this.joinSource = joinSource;
    }

    public String getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getAuditStatusCode() {
        return auditStatusCode;
    }

    public void setAuditStatusCode(String auditStatusCode) {
        this.auditStatusCode = auditStatusCode;
    }

}
