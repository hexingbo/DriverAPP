package com.lesso.module.waybill.mvp.model.entity;

/**
 * @Author :hexingbo
 * @Date :2019/11/19
 * @FileName： SubmitOrderSaveAccountsBean
 * @Describe :
 */
public class SubmitOrderSaveAccountsBean {

    private String orderId;//订单ID ,多个以逗号隔开
    private String driverId;// 司机ID
    private String driverDateStart;//发货开始时间,格式：2019-01-01
    private String driverDateEnd;//发货结束时间,格式：2019-01-01

    public SubmitOrderSaveAccountsBean(String orderId, String driverId, String driverDateStart, String driverDateEnd) {
        this.orderId = orderId;
        this.driverId = driverId;
        this.driverDateStart = driverDateStart;
        this.driverDateEnd = driverDateEnd;
    }
}
