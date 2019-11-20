package com.lesso.module.me.mvp.model.entity;

import android.support.annotation.NonNull;

/**
 * @Author :hexingbo
 * @Date :2019/11/15
 * @FileName： SubmitLoginOutBean
 * @Describe :
 */
public class SubmitCompanyJoinedListBean {

    private ConditionBean condition;//driverId：司机ID
    private int current;
    private int size;

    public SubmitCompanyJoinedListBean(int current, int size, @NonNull ConditionBean condition) {
        this.condition = condition;
        this.size = size;
        this.current = current;
    }

    public static class ConditionBean {
        private String driverId;//司机ID

        public ConditionBean(@NonNull String driverId) {
            this.driverId = driverId;
        }
    }
}
