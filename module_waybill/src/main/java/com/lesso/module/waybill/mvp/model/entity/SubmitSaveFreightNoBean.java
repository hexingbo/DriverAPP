package com.lesso.module.waybill.mvp.model.entity;

import android.support.annotation.Nullable;

/**
 * @Author :hexingbo
 * @Date :2019/11/15
 * @FileName： SubmitWayBillListBean
 * @Describe :
 */
public class SubmitSaveFreightNoBean {

    private String orderId;//订单ID
    private String freightNo;//货运单号

    public SubmitSaveFreightNoBean(@Nullable String orderId, @Nullable String freightNo) {
        this.orderId = orderId;
        this.freightNo = freightNo;
    }
}
