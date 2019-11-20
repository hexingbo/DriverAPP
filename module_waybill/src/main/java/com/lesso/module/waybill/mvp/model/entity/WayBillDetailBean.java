package com.lesso.module.waybill.mvp.model.entity;

import java.util.List;

/**
 * @Author :hexingbo
 * @Date :2019/11/15
 * @FileName： WayBillListBean
 * @Describe :运单详情
 */
public class WayBillDetailBean {

    private String orderId;//订单ID
    private String goodsName;//货物名称
    private String orderNo;//订单号
    private String planDeliverDate;//预计发货时间
    private String logisticsCompany;//物流公司 承运方
    private String receivingParty;//收货方名称
    private String shipperAddress;//发货地址
    private String receivingPartyAddress;//收货地址
    private String remark;//备注
    private String freightNo;//货运单号
    private String customerType;//客户类型(cg：常规用户；ld：零担用户)
    private String orderStatus;//订单状态(G(待派车),T(已派车，未签约),A(待发货)，B(待收货),F(已完成))
    private String carNo;//车牌号
    private String shipper;//发货方
    private String shipperCompanyHeadUrl;//发货公司log

    private List<TransportTrackBean> transportTrack;//运输轨迹
    private String deliverDate;//发车时间
    private String customerTypeName;//发车类型名称
    private String issuingBay;//发货仓
    private String driverBy;//运输司机
    private String receiptDate;//到货时间
    private String freightStation;//货运站

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getPlanDeliverDate() {
        return planDeliverDate;
    }

    public void setPlanDeliverDate(String planDeliverDate) {
        this.planDeliverDate = planDeliverDate;
    }

    public String getLogisticsCompany() {
        return logisticsCompany;
    }

    public void setLogisticsCompany(String logisticsCompany) {
        this.logisticsCompany = logisticsCompany;
    }

    public String getReceivingParty() {
        return receivingParty;
    }

    public void setReceivingParty(String receivingParty) {
        this.receivingParty = receivingParty;
    }

    public String getShipperAddress() {
        return shipperAddress;
    }

    public void setShipperAddress(String shipperAddress) {
        this.shipperAddress = shipperAddress;
    }

    public String getReceivingPartyAddress() {
        return receivingPartyAddress;
    }

    public void setReceivingPartyAddress(String receivingPartyAddress) {
        this.receivingPartyAddress = receivingPartyAddress;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getFreightNo() {
        return freightNo;
    }

    public void setFreightNo(String freightNo) {
        this.freightNo = freightNo;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getCarNo() {
        return carNo;
    }

    public void setCarNo(String carNo) {
        this.carNo = carNo;
    }

    public String getShipper() {
        return shipper;
    }

    public void setShipper(String shipper) {
        this.shipper = shipper;
    }

    public String getShipperCompanyHeadUrl() {
        return shipperCompanyHeadUrl;
    }

    public void setShipperCompanyHeadUrl(String shipperCompanyHeadUrl) {
        this.shipperCompanyHeadUrl = shipperCompanyHeadUrl;
    }

    public List<TransportTrackBean> getTransportTrack() {
        return transportTrack;
    }

    public void setTransportTrack(List<TransportTrackBean> transportTrack) {
        this.transportTrack = transportTrack;
    }

    public String getDeliverDate() {
        return deliverDate;
    }

    public void setDeliverDate(String deliverDate) {
        this.deliverDate = deliverDate;
    }

    public String getCustomerTypeName() {
        return customerTypeName;
    }

    public void setCustomerTypeName(String customerTypeName) {
        this.customerTypeName = customerTypeName;
    }

    public String getIssuingBay() {
        return issuingBay;
    }

    public void setIssuingBay(String issuingBay) {
        this.issuingBay = issuingBay;
    }

    public String getDriverBy() {
        return driverBy;
    }

    public void setDriverBy(String driverBy) {
        this.driverBy = driverBy;
    }

    public String getReceiptDate() {
        return receiptDate;
    }

    public void setReceiptDate(String receiptDate) {
        this.receiptDate = receiptDate;
    }

    public String getFreightStation() {
        return freightStation;
    }

    public void setFreightStation(String freightStation) {
        this.freightStation = freightStation;
    }

    public static class TransportTrackBean {

        private String date;//日期(月-日)
        private String time;//时间(时-分)
        private String transportRemark;//运输说明

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getTransportRemark() {
            return transportRemark;
        }

        public void setTransportRemark(String transportRemark) {
            this.transportRemark = transportRemark;
        }
    }

}
