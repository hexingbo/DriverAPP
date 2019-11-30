package com.lesso.module.me.mvp.model.entity;

/**
 * @author wangdunwei
 */
public class CarJoinBean {


    /**
     * insuranceFormatDate : 2020-01-01
     * carInsurancePath : null
     * drivingCardPath : null
     * drivingCardBackPath : null
     * carAPhotoPath : null
     * carBPhotoPath : null
     * carCPhotoPath : null
     * carInsuranceUrl : null
     * drivingCardUrl : null
     * drivingCardBackUrl : null
     * carAPhotoUrl : null
     * carBPhotoUrl : null
     * carCPhotoUrl : null
     * companyId : null
     * compVerifyStatus : null
     * compVerifyStatusDesc : null
     * compVerifyRemark : null
     * carOperation : null
     * tblWlVerifyPictureList : null
     * guid : 1cd14ac7-3544-4ff1-ba72-649b83566f65
     * carNo : 甘a10000
     * belongBy : null
     * carType : 保温车
     * carLong : 10
     * carSize : 10
     * deadWeight : 10
     * insuranceBy : null
     * insuranceDate : null
     * carSource : null
     * issueBy : null
     * issueDate : null
     * issueStatus : null
     * createdDate : null
     * updatedDate : null
     * status : null
     * verifyStatus : null
     */

    private String insuranceFormatDate;
    private String carInsurancePath;
    private String drivingCardPath;
    private String drivingCardBackPath;
    private String carAPhotoPath;
    private String carBPhotoPath;
    private String carCPhotoPath;
    private String carInsuranceUrl;
    private String drivingCardUrl;
    private String drivingCardBackUrl;
    private String carAPhotoUrl;
    private String carBPhotoUrl;
    private String carCPhotoUrl;
    private String companyId;
    /**
     * 认证状态（B：审核中；D：审核通过；F：审核不通过）
     */
    private String compVerifyStatus;
    private String compVerifyStatusDesc;
    private String compVerifyRemark;
    private String carOperation;
    private String tblWlVerifyPictureList;
    private String guid;
    private String carNo;
    private String belongBy;
    private String carType;
    private String carLong;
    private String carSize;
    private String deadWeight;
    private String insuranceBy;
    private String insuranceDate;
    private String carSource;
    private String issueBy;
    private String issueDate;
    private String issueStatus;
    private String createdDate;
    private String updatedDate;
    private String status;
    private String verifyStatus;
    private boolean selected;

    public static final String STATE_REVIEWING = "B";
    public static final String STATE_PASSED= "D";
    public static final String STATE_REJECTED = "F";

    public String getInsuranceFormatDate() {
        return insuranceFormatDate;
    }

    public void setInsuranceFormatDate(String insuranceFormatDate) {
        this.insuranceFormatDate = insuranceFormatDate;
    }

    public String getCarInsurancePath() {
        return carInsurancePath;
    }

    public void setCarInsurancePath(String carInsurancePath) {
        this.carInsurancePath = carInsurancePath;
    }

    public String getDrivingCardPath() {
        return drivingCardPath;
    }

    public void setDrivingCardPath(String drivingCardPath) {
        this.drivingCardPath = drivingCardPath;
    }

    public String getDrivingCardBackPath() {
        return drivingCardBackPath;
    }

    public void setDrivingCardBackPath(String drivingCardBackPath) {
        this.drivingCardBackPath = drivingCardBackPath;
    }

    public String getCarAPhotoPath() {
        return carAPhotoPath;
    }

    public void setCarAPhotoPath(String carAPhotoPath) {
        this.carAPhotoPath = carAPhotoPath;
    }

    public String getCarBPhotoPath() {
        return carBPhotoPath;
    }

    public void setCarBPhotoPath(String carBPhotoPath) {
        this.carBPhotoPath = carBPhotoPath;
    }

    public String getCarCPhotoPath() {
        return carCPhotoPath;
    }

    public void setCarCPhotoPath(String carCPhotoPath) {
        this.carCPhotoPath = carCPhotoPath;
    }

    public String getCarInsuranceUrl() {
        return carInsuranceUrl;
    }

    public void setCarInsuranceUrl(String carInsuranceUrl) {
        this.carInsuranceUrl = carInsuranceUrl;
    }

    public String getDrivingCardUrl() {
        return drivingCardUrl;
    }

    public void setDrivingCardUrl(String drivingCardUrl) {
        this.drivingCardUrl = drivingCardUrl;
    }

    public String getDrivingCardBackUrl() {
        return drivingCardBackUrl;
    }

    public void setDrivingCardBackUrl(String drivingCardBackUrl) {
        this.drivingCardBackUrl = drivingCardBackUrl;
    }

    public String getCarAPhotoUrl() {
        return carAPhotoUrl;
    }

    public void setCarAPhotoUrl(String carAPhotoUrl) {
        this.carAPhotoUrl = carAPhotoUrl;
    }

    public String getCarBPhotoUrl() {
        return carBPhotoUrl;
    }

    public void setCarBPhotoUrl(String carBPhotoUrl) {
        this.carBPhotoUrl = carBPhotoUrl;
    }

    public String getCarCPhotoUrl() {
        return carCPhotoUrl;
    }

    public void setCarCPhotoUrl(String carCPhotoUrl) {
        this.carCPhotoUrl = carCPhotoUrl;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCompVerifyStatus() {
        return compVerifyStatus;
    }

    public void setCompVerifyStatus(String compVerifyStatus) {
        this.compVerifyStatus = compVerifyStatus;
    }

    public String getCompVerifyStatusDesc() {
        return compVerifyStatusDesc;
    }

    public void setCompVerifyStatusDesc(String compVerifyStatusDesc) {
        this.compVerifyStatusDesc = compVerifyStatusDesc;
    }

    public String getCompVerifyRemark() {
        return compVerifyRemark;
    }

    public void setCompVerifyRemark(String compVerifyRemark) {
        this.compVerifyRemark = compVerifyRemark;
    }

    public String getCarOperation() {
        return carOperation;
    }

    public void setCarOperation(String carOperation) {
        this.carOperation = carOperation;
    }

    public String getTblWlVerifyPictureList() {
        return tblWlVerifyPictureList;
    }

    public void setTblWlVerifyPictureList(String tblWlVerifyPictureList) {
        this.tblWlVerifyPictureList = tblWlVerifyPictureList;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getCarNo() {
        return carNo;
    }

    public void setCarNo(String carNo) {
        this.carNo = carNo;
    }

    public String getBelongBy() {
        return belongBy;
    }

    public void setBelongBy(String belongBy) {
        this.belongBy = belongBy;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public String getCarLong() {
        return carLong;
    }

    public void setCarLong(String carLong) {
        this.carLong = carLong;
    }

    public String getCarSize() {
        return carSize;
    }

    public void setCarSize(String carSize) {
        this.carSize = carSize;
    }

    public String getDeadWeight() {
        return deadWeight;
    }

    public void setDeadWeight(String deadWeight) {
        this.deadWeight = deadWeight;
    }

    public String getInsuranceBy() {
        return insuranceBy;
    }

    public void setInsuranceBy(String insuranceBy) {
        this.insuranceBy = insuranceBy;
    }

    public String getInsuranceDate() {
        return insuranceDate;
    }

    public void setInsuranceDate(String insuranceDate) {
        this.insuranceDate = insuranceDate;
    }

    public String getCarSource() {
        return carSource;
    }

    public void setCarSource(String carSource) {
        this.carSource = carSource;
    }

    public String getIssueBy() {
        return issueBy;
    }

    public void setIssueBy(String issueBy) {
        this.issueBy = issueBy;
    }

    public String getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }

    public String getIssueStatus() {
        return issueStatus;
    }

    public void setIssueStatus(String issueStatus) {
        this.issueStatus = issueStatus;
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

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
