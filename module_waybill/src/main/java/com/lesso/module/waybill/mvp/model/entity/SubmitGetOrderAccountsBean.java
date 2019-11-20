package com.lesso.module.waybill.mvp.model.entity;

import android.support.annotation.Nullable;

/**
 * @Author :hexingbo
 * @Date :2019/11/19
 * @FileName： SubmitGetOrderAccountsBean
 * @Describe :
 */
public class SubmitGetOrderAccountsBean {

    private int current;
    private int size;
    private EntityBean entity;

    /**
     * 获取对账列表数据提交参数
     *
     * @param current 当前页
     * @param size    每页大小
     * @param entity  查找条件
     */
    public SubmitGetOrderAccountsBean(int current, int size, @Nullable EntityBean entity) {
        this.current = current;
        this.size = size;
        this.entity = entity;
    }

    public static class EntityBean {

        private String accountStatus;
        private String driverId;
        private String condition;
        private String driverDateStart;
        private String driverDateEnd;

        /**
         * 查询条件
         *
         * @param accountStatus   对账状态 空：待确认A：确认中B：已确认
         * @param driverId        司机ID
         * @param condition       付款公司/订单号
         * @param driverDateStart 发货开始时间 对账状态为待确认时，必填 格式：2019-01-01
         * @param driverDateEnd   发货结束时间 对账状态为待确认时，必填 格式：2019-01-01
         */
        public EntityBean(String accountStatus, String driverId, String condition,
                          String driverDateStart, String driverDateEnd) {
            this.accountStatus = accountStatus;
            this.driverDateStart = driverDateStart;
            this.driverDateEnd = driverDateEnd;
            this.driverId = driverId;
            this.condition = condition;
        }
    }

}
