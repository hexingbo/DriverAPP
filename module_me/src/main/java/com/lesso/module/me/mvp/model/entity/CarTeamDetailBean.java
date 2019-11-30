package com.lesso.module.me.mvp.model.entity;

/**
 * @Author :hexingbo
 * @Date :2019/11/26
 * @FileName： CarTeamDetailBean
 * @Describe :车辆详情
 */
public class CarTeamDetailBean {
    private  String guid;//车辆ID
    private  String carNo;//车牌号
    private  String belongBy;//所属车主id
    private  String carType;//车辆类型
    private  String carLong;//车辆长度
    private  String carSize;//车辆体积
    private  String deadWeight;//载重量
    private  String insuranceBy;//车保承险公司
    private  String insuranceFormatDate;//车保到期日
    private  String carInsurancePath;//车辆保险附件地址
    private  String drivingCardPath;//行驶证正面附件路径
    private  String drivingCardBackPath;//行驶证反面附件路径
    private  String carAPhotoPath;//车头照附件路径
    private  String carBPhotoPath;//车身照附件路径
    private  String carCPhotoPath;//车尾照附件路径
    private  String carInsuranceUrl;//车辆保险附件url地址
    private  String drivingCardUrl;//行驶证正面附件url地址
    private  String drivingCardBackUrl;//行驶证反面附件url地址
    private  String carAPhotoUrl;//车头照附件url地址
    private  String carBPhotoUrl;//车身照附件url地址
    private  String carCPhotoUrl;//车尾照附件url地址
    /**
     * carSize : 216
     * deadWeight : 54565
     * status : A
     */

    private String status;


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

    @Override
    public String toString() {
        return "CarTeamDetailBean{" +
                "guid='" + guid + '\'' +
                ", carNo='" + carNo + '\'' +
                ", belongBy='" + belongBy + '\'' +
                ", carType='" + carType + '\'' +
                ", carLong='" + carLong + '\'' +
                ", carSize='" + carSize + '\'' +
                ", deadWeight='" + deadWeight + '\'' +
                ", insuranceBy='" + insuranceBy + '\'' +
                ", insuranceFormatDate='" + insuranceFormatDate + '\'' +
                ", carInsurancePath='" + carInsurancePath + '\'' +
                ", drivingCardPath='" + drivingCardPath + '\'' +
                ", drivingCardBackPath='" + drivingCardBackPath + '\'' +
                ", carAPhotoPath='" + carAPhotoPath + '\'' +
                ", carBPhotoPath='" + carBPhotoPath + '\'' +
                ", carCPhotoPath='" + carCPhotoPath + '\'' +
                ", carInsuranceUrl='" + carInsuranceUrl + '\'' +
                ", drivingCardUrl='" + drivingCardUrl + '\'' +
                ", drivingCardBackUrl='" + drivingCardBackUrl + '\'' +
                ", carAPhotoUrl='" + carAPhotoUrl + '\'' +
                ", carBPhotoUrl='" + carBPhotoUrl + '\'' +
                ", carCPhotoUrl='" + carCPhotoUrl + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
