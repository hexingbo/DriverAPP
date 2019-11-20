package com.lesso.module.me.mvp.model.entity;

import android.support.annotation.NonNull;

/**
 * @Author :hexingbo
 * @Date :2019/11/15
 * @Describe :
 */
public class SubmitCompanyJoiningBean {

    private String companyId;//公司ID
    private String driverId;//司机ID
    private String joinType;//加盟类型 0：物流公司邀请；1：司机主动加入

    public SubmitCompanyJoiningBean(@NonNull String companyId, @NonNull String driverId, @NonNull String joinType) {
        this.companyId = companyId;
        this.driverId = driverId;
        this.joinType = joinType;
    }
}
