package com.lesso.module.waybill.mvp.model.entity;

/**
 * @Author :hexingbo
 * @Date :2019/11/19
 * @FileName： OrderAccountBean
 * @Describe :
 */
public class OrderAccountBean {
    private String orderId;//订单ID
    private String logisticsCompany;//物流公司
    private String shipper;//发货方
    private String shipperAddress;//发货地址
    private String receivingPartyAddress;//收货地址
    private String goodsName;//货物名称
    private String carNo;//车牌号
    private String transportCosts;//运费
    private String receivingParty;//收货方

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getLogisticsCompany() {
        return logisticsCompany;
    }

    public void setLogisticsCompany(String logisticsCompany) {
        this.logisticsCompany = logisticsCompany;
    }

    public String getShipper() {
        return shipper;
    }

    public void setShipper(String shipper) {
        this.shipper = shipper;
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

    public String getTransportCosts() {
        return transportCosts;
    }

    public void setTransportCosts(String transportCosts) {
        this.transportCosts = transportCosts;
    }

    public String getReceivingParty() {
        return receivingParty;
    }

    public void setReceivingParty(String receivingParty) {
        this.receivingParty = receivingParty;
    }
}
