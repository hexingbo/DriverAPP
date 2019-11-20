package com.lesso.module.me.mvp.model.entity;

/**
 * @Author :hexingbo
 * @Date :2019/11/16
 * @FileName： CompanyJoinBean
 * @Describe :物流公司对象
 */
public class CompanyJoinBean {

    private String companyId;//公司ID
    private String companyName;//公司名称
    private String headUrl;//头像url
    private String legalPersonName;//法人姓名
    private String contactBy;//联系人
    private String contactMobile;//联系人电话
    private String address;//地址

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

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public String getLegalPersonName() {
        return legalPersonName;
    }

    public void setLegalPersonName(String legalPersonName) {
        this.legalPersonName = legalPersonName;
    }

    public String getContactBy() {
        return contactBy;
    }

    public void setContactBy(String contactBy) {
        this.contactBy = contactBy;
    }

    public String getContactMobile() {
        return contactMobile;
    }

    public void setContactMobile(String contactMobile) {
        this.contactMobile = contactMobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
