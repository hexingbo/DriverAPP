package com.lesso.module.message.mvp.model.entity;

import android.support.annotation.NonNull;

/**
 * @Author :hexingbo
 * @Date :2019/11/16
 * @FileName： SubmitGetMessageListBean
 * @Describe :
 */
public class SubmitGetMessageListBean {
    private int current;
    private int size;
    private ConditionBean condition;

    public SubmitGetMessageListBean(int current, int size, @NonNull ConditionBean condition) {
        this.current = current;
        this.size = size;
        this.condition = condition;
    }

    public static class ConditionBean {
        private String driverId;

        public ConditionBean(@NonNull String driverId) {
            this.driverId = driverId;
        }
    }
}
