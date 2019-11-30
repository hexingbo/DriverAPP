package com.lesso.module.me.mvp.model.entity;

/**
 * @author wangdunwei
 */
public class QueryCarJoinReq {
    private QueryCarJoinReq.Condition condition;
    private int current;
    private int size;

    public QueryCarJoinReq() {
    }

    public QueryCarJoinReq(String driverId, int current, int size) {
        this.condition = new Condition(driverId);
        this.current = current;
        this.size = size;
    }

    public QueryCarJoinReq(String driverId, String companyId, int current, int size) {
        this.condition = new Condition(driverId, companyId);
        this.current = current;
        this.size = size;
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

    public static class Condition {
        private String driverId;
        /**
         * flag：0：查询我的车辆列表，1：查询我的车辆及对应的加盟状态
         */
        private int flag;
        private String companyId;

        public static final int FLAG_MY_CAR = 0;
        public static final int FLAG_CAR_AND_JOIN_STATE = 1;

        public Condition(String driverId) {
            this.driverId = driverId;
            this.flag = FLAG_MY_CAR;
        }

        public Condition(String driverId, String companyId) {
            this.driverId = driverId;
            this.flag = FLAG_CAR_AND_JOIN_STATE;
            this.companyId = companyId;
        }

        public String getDriverId() {
            return driverId;
        }

        public void setDriverId(String driverId) {
            this.driverId = driverId;
        }

        public int getFlag() {
            return flag;
        }

        public void setFlag(int flag) {
            this.flag = flag;
        }

        public String getCompanyId() {
            return companyId;
        }

        public void setCompanyId(String companyId) {
            this.companyId = companyId;
        }
    }
}
