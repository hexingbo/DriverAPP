package com.lesso.module.waybill.mvp.model.entity;

/**
 * @Author :hexingbo
 * @Date :2019/11/15
 * @FileName： WayBillListBean
 * @Describe :
 */
public class WayBillListBean {

    private String orderId;//订单ID
    private String goodsName;//货物名称
    private String carNo;//车牌号
    private String shipperCompanyHeadUrl;//发货公司log
    private String orderNo;//订单号
    private String planDeliverDate;//预计发货时间
    private String logisticsCompany;//物流公司
    private String receivingParty;//收货方名称
    private String orderStatus;//订单状态(G(待派车),T(已派车，未签约),A(待发货)，B(待收货),F(已完成))
    private String shipperAddress;//发货地址
    private String receivingPartyAddress;//收货地址
    private String shipper;//发货方

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

    public String getCarNo() {
        return carNo;
    }

    public void setCarNo(String carNo) {
        this.carNo = carNo;
    }

    public String getShipperCompanyHeadUrl() {
        return shipperCompanyHeadUrl;
    }

    public void setShipperCompanyHeadUrl(String shipperCompanyHeadUrl) {
        this.shipperCompanyHeadUrl = shipperCompanyHeadUrl;
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

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
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

    public String getShipper() {
        return shipper;
    }

    public void setShipper(String shipper) {
        this.shipper = shipper;
    }
}
