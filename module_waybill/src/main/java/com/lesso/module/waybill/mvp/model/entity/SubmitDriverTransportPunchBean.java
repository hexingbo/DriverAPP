package com.lesso.module.waybill.mvp.model.entity;

import android.support.annotation.NonNull;

/**
 * @Author :hexingbo
 * @Date :2019/11/16
 * @FileName： SubmitDriverTransportPunchBean
 * @Describe :
 */
public class SubmitDriverTransportPunchBean {

    private String currentUserId;//：司机id;
    private String orderId;//：订单ID;
    private String orderNo;//：订单号;
    private String carNo;//：车牌号;
    private String checkInAddress;//：签到地址;
    private String latitude;//：纬度
    private String longitude;//：经度

    public SubmitDriverTransportPunchBean(@NonNull String orderId, @NonNull String orderNo,
                                          @NonNull String carNo, @NonNull String currentUserId,
                                          @NonNull String latitude, @NonNull String longitude, @NonNull String checkInAddress) {
        this.currentUserId = currentUserId;
        this.orderId = orderId;
        this.orderNo = orderNo;
        this.carNo = carNo;
        this.checkInAddress = checkInAddress;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
