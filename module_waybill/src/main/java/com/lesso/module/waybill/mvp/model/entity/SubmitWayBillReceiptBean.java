package com.lesso.module.waybill.mvp.model.entity;

import android.support.annotation.NonNull;

/**
 * @Author :hexingbo
 * @Date :2019/11/16
 * @FileName： SubmitDriverTransportPunchBean
 * @Describe :
 */
public class SubmitWayBillReceiptBean {

    private String currentUserId;//：司机id;
    private String orderId;//：订单ID;
    private String orderNo;//：订单号;
    private String carNo;//：车牌号;
    private String deliveryAddress;//：收货地址;

    public SubmitWayBillReceiptBean(@NonNull String orderId, @NonNull String orderNo,
                                    @NonNull String carNo, @NonNull String currentUserId,
                                    @NonNull String deliveryAddress) {
        this.currentUserId = currentUserId;
        this.orderId = orderId;
        this.orderNo = orderNo;
        this.carNo = carNo;
        this.deliveryAddress = deliveryAddress;
    }
}
