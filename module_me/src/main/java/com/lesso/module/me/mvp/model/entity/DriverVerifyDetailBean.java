package com.lesso.module.me.mvp.model.entity;

import android.support.annotation.Nullable;

/**
 * @Author :hexingbo
 * @Date :2019/11/16
 * @FileName： DriverVerifyDetailBean
 * @Describe :
 */
public class DriverVerifyDetailBean {
    private String guid;//
    private String driverBy;//司机姓名
    private String driverMobile;//司机电话
    private String idno;//身份证号
    private String driverno;//驾驶证号
    private String driverCar;//拥有车辆
    private String belongBy;//所属物流公司
    private String openId;//微信openid
    private String createdDate;//创建时间
    private String updatedDate;//修改时间
    private String status;//记录状态（A：可用；E不可用）
    private String verifyStatus;//认证状态
    private String headUrl;//个人头像url

    private String idCardUrl;//身份证正面附件url
    private String idCardBackUrl;//身份证反面附件url

    private String driverCardUrl;//驾驶证正面附件url
    private String driverCardBackUrl;//驾驶证反面附件url

    private String idCardPath;//身份证正面附件路径地址
    private String idCardBackPath;//身份证反面附件路径地址
    private String driverCardPath;//驾驶证正面附件路径地址
    private String driverCardBackPath;//驾驶证反面附件路径地址
    private String headSrc;//用户头像附件路径地址

    public DriverVerifyDetailBean() {

    }

    /**
     * 提交保存用户信息
     * @param guid
     * @param driverBy 司机姓名
     * @param idno 身份证号
     * @param driverno 驾驶证号
     * @param idCardUrl 身份证正面附件url
     * @param idCardBackUrl 身份证反面附件url
     * @param driverCardUrl 驾驶证正面附件url
     * @param driverCardBackUrl 身份证正面附件路径地址
     * @param idCardPath 身份证正面附件路径地址
     * @param idCardBackPath 身份证反面附件路径地址
     * @param driverCardPath 驾驶证正面附件路径地址
     * @param driverCardBackPath 驾驶证反面附件路径地址
     */
    public DriverVerifyDetailBean(@Nullable String guid, @Nullable String driverBy, @Nullable String idno, @Nullable String driverno,
                                  @Nullable String idCardUrl, @Nullable String idCardBackUrl,
                                  @Nullable String driverCardUrl, @Nullable String driverCardBackUrl,
                                  @Nullable String idCardPath, @Nullable String idCardBackPath,
                                  @Nullable String driverCardPath, @Nullable String driverCardBackPath,@Nullable String headSrc) {
        this.guid = guid;
        this.driverBy = driverBy;
        this.idno = idno;
        this.driverno = driverno;
        this.idCardUrl = idCardUrl;
        this.idCardBackUrl = idCardBackUrl;
        this.driverCardUrl = driverCardUrl;
        this.driverCardBackUrl = driverCardBackUrl;
        this.idCardPath = idCardPath;
        this.idCardBackPath = idCardBackPath;
        this.driverCardPath = driverCardPath;
        this.driverCardBackPath = driverCardBackPath;
        this.headSrc = headSrc;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getDriverBy() {
        return driverBy;
    }

    public void setDriverBy(String driverBy) {
        this.driverBy = driverBy;
    }

    public String getDriverMobile() {
        return driverMobile;
    }

    public void setDriverMobile(String driverMobile) {
        this.driverMobile = driverMobile;
    }

    public String getIdno() {
        return idno;
    }

    public void setIdno(String idno) {
        this.idno = idno;
    }

    public String getDriverno() {
        return driverno;
    }

    public void setDriverno(String driverno) {
        this.driverno = driverno;
    }

    public String getDriverCar() {
        return driverCar;
    }

    public void setDriverCar(String driverCar) {
        this.driverCar = driverCar;
    }

    public String getBelongBy() {
        return belongBy;
    }

    public void setBelongBy(String belongBy) {
        this.belongBy = belongBy;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getVerifyStatus() {
        return verifyStatus;
    }

    public void setVerifyStatus(String verifyStatus) {
        this.verifyStatus = verifyStatus;
    }

    public void setHeadSrc(String headSrc) {
        this.headSrc = headSrc;
    }

    public String getHeadSrc() {
        return headSrc;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public String getIdCardUrl() {
        return idCardUrl;
    }

    public void setIdCardUrl(String idCardUrl) {
        this.idCardUrl = idCardUrl;
    }

    public String getIdCardBackUrl() {
        return idCardBackUrl;
    }

    public void setIdCardBackUrl(String idCardBackUrl) {
        this.idCardBackUrl = idCardBackUrl;
    }

    public String getDriverCardUrl() {
        return driverCardUrl;
    }

    public void setDriverCardUrl(String driverCardUrl) {
        this.driverCardUrl = driverCardUrl;
    }

    public String getDriverCardBackUrl() {
        return driverCardBackUrl;
    }

    public void setDriverCardBackUrl(String driverCardBackUrl) {
        this.driverCardBackUrl = driverCardBackUrl;
    }

    public String getIdCardPath() {
        return idCardPath;
    }

    public void setIdCardPath(String idCardPath) {
        this.idCardPath = idCardPath;
    }

    public String getIdCardBackPath() {
        return idCardBackPath;
    }

    public void setIdCardBackPath(String idCardBackPath) {
        this.idCardBackPath = idCardBackPath;
    }

    public String getDriverCardPath() {
        return driverCardPath;
    }

    public void setDriverCardPath(String driverCardPath) {
        this.driverCardPath = driverCardPath;
    }

    public String getDriverCardBackPath() {
        return driverCardBackPath;
    }

    public void setDriverCardBackPath(String driverCardBackPath) {
        this.driverCardBackPath = driverCardBackPath;
    }

    @Override
    public String toString() {
        return "DriverVerifyDetailBean{" +
                "guid='" + guid + '\'' +
                ", driverBy='" + driverBy + '\'' +
                ", driverMobile='" + driverMobile + '\'' +
                ", idno='" + idno + '\'' +
                ", driverno='" + driverno + '\'' +
                ", driverCar='" + driverCar + '\'' +
                ", belongBy='" + belongBy + '\'' +
                ", openId='" + openId + '\'' +
                ", createdDate='" + createdDate + '\'' +
                ", updatedDate='" + updatedDate + '\'' +
                ", status='" + status + '\'' +
                ", verifyStatus='" + verifyStatus + '\'' +
                ", headUrl='" + headUrl + '\'' +
                ", idCardUrl='" + idCardUrl + '\'' +
                ", idCardBackUrl='" + idCardBackUrl + '\'' +
                ", driverCardUrl='" + driverCardUrl + '\'' +
                ", driverCardBackUrl='" + driverCardBackUrl + '\'' +
                ", idCardPath='" + idCardPath + '\'' +
                ", idCardBackPath='" + idCardBackPath + '\'' +
                ", driverCardPath='" + driverCardPath + '\'' +
                ", driverCardBackPath='" + driverCardBackPath + '\'' +
                ", headSrc='" + headSrc + '\'' +
                '}';
    }
}
