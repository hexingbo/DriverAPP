package com.lesso.module.me.mvp.model.entity;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import me.jessyan.armscomponent.commonres.enums.CompanyActionType;

/**
 * @Author :hexingbo
 * @Date :2019/11/16
 * @FileName： CompanyJoinBean
 * @Describe :物流公司对象
 */
public class SubmitCompanyJoinedActionBean {

    private String companyId;//公司ID
    private String driverId;//司机ID
    private String operatorType;//操作类型 CANCEL:取消；QUIT：退出；AGREE：同意 ；REFUSE：拒绝
    private String joinId;//加盟主键ID

    public SubmitCompanyJoinedActionBean(@NonNull String companyId, @NonNull String driverId, @NonNull String joinId,@Nullable CompanyActionType operatorType) {
        this.companyId = companyId;
        this.driverId = driverId;
        this.operatorType = operatorType.name();
        this.joinId = joinId;
    }
}
