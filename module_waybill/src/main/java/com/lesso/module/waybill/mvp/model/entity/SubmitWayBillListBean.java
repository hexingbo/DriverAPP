package com.lesso.module.waybill.mvp.model.entity;

import android.support.annotation.Nullable;

/**
 * @Author :hexingbo
 * @Date :2019/11/15
 * @FileName： SubmitWayBillListBean
 * @Describe :
 */
public class SubmitWayBillListBean {

    public SubmitWayBillListBean(int current, int size, ConditionBean condition) {
        this.condition = condition;
        this.current = current;
        this.size = size;
    }

    /**
     * condition : {"driverId":"eeb64e5e-c970-4252-95bc-55f623660a5e","orderStatus":"A"}
     * current : 1
     * size : 10
     */

    private ConditionBean condition;
    private int current;
    private int size;

    public ConditionBean getCondition() {
        return condition;
    }

    public void setCondition(ConditionBean condition) {
        this.condition = condition;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public static class ConditionBean {

        public ConditionBean(@Nullable String driverId, @Nullable String orderStatus) {
            this.driverId = driverId;
            this.orderStatus = orderStatus;
        }

        /**
         * driverId : eeb64e5e-c970-4252-95bc-55f623660a5e
         * orderStatus : A
         */

        private String driverId;
        private String orderStatus;

        public String getDriverId() {
            return driverId;
        }

        public void setDriverId(String driverId) {
            this.driverId = driverId;
        }

        public String getOrderStatus() {
            return orderStatus;
        }

        public void setOrderStatus(String orderStatus) {
            this.orderStatus = orderStatus;
        }
    }
}
