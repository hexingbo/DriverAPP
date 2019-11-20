package com.lesso.module.me.mvp.model.entity;

import android.support.annotation.NonNull;

/**
 * @Author :hexingbo
 * @Date :2019/11/16
 * @FileName： SubmitDriverVerifyBean
 * @Describe :
 */
public class SubmitDriverVerifyBean {

    private String currentUserId;//当前用户id
    private String name;//司机姓名
    private String idno;//身份证号
    private String driverno;//驾驶证号
    private String idCardPath;//身份证正面附件地址
    private String idCardBackPath;//身份证反面附件地址
    private String driverCardPath;//驾驶证主页附件地址
    private String driverCardBackPath;//驾驶证副页附件地址
    private String lifePhotoPath;//手持身份证附件地址

    /**
     * 实际认证提交参数
     *
     * @param currentUserId      当前用户id
     * @param name               司机姓名
     * @param idno               身份证号
     * @param driverno           驾驶证号
     * @param idCardPath         身份证正面附件地址
     * @param idCardBackPath     身份证反面附件地址
     * @param driverCardPath     驾驶证主页附件地址
     * @param driverCardBackPath 驾驶证副页附件地址
     * @param lifePhotoPath      手持身份证附件地址
     */
    public SubmitDriverVerifyBean(@NonNull String currentUserId, @NonNull String name, @NonNull String idno,
                                  @NonNull String driverno, @NonNull String idCardPath, @NonNull String idCardBackPath,
                                  @NonNull String driverCardPath, @NonNull String driverCardBackPath, String lifePhotoPath) {
        this.currentUserId = currentUserId;
        this.name = name;
        this.idno = idno;
        this.driverno = driverno;
        this.idCardPath = idCardPath;
        this.idCardBackPath = idCardBackPath;
        this.driverCardPath = driverCardPath;
        this.driverCardBackPath = driverCardBackPath;
        this.lifePhotoPath = lifePhotoPath;
    }
}
