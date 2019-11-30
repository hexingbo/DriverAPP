package com.lesso.module.me.mvp.model.entity;

import android.util.Log;

import com.lesso.module.me.R;

import java.util.List;

/**
 * @author EDZ
 */
public class CarJoinReq {
    private String carId;
    private String belongBy;
    /**
     * cancel：取消加盟，quit：退出加盟，resubmit：重新提交或申请加盟
     */
    private String operType;

    public static final String OPERATE_RESUBMIT = "resubmit";
    public static final String OPERATE_CANCEL = "cancel";
    public static final String OPERATE_QUIT = "quit";

    public static CarJoinReq ofSubmit(List<CarJoinBean> list, String companyId) {
        return new CarJoinReq(mergeCarId(list), companyId, "resubmit");
    }

    public static CarJoinReq ofCancel(CarJoinBean item, String companyId) {
        return new CarJoinReq(item.getGuid(), companyId, "cancel");
    }

    public static CarJoinReq ofQuit(CarJoinBean item, String companyId) {
        return new CarJoinReq(item.getGuid(), companyId, "quit");
    }

    private static String mergeCarId(List<CarJoinBean> list) {
        String str = "";
        for(int i = 0; i < list.size(); i++) {
            str += list.get(i).getGuid();
            str += ",";
        }

        if(str.endsWith(",")) {
            str = str.substring(0, str.length() - 1);
        }

        Log.e("tag", str);
        return str;
    }


    public CarJoinReq(String carId, String belongBy, String operType) {
        this.carId = carId;
        this.belongBy = belongBy;
        this.operType = operType;
    }


    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public String getBelongBy() {
        return belongBy;
    }

    public void setBelongBy(String belongBy) {
        this.belongBy = belongBy;
    }

    public String getOperType() {
        return operType;
    }

    public void setOperType(String operType) {
        this.operType = operType;
    }

    public int getMessageId() {
        if(OPERATE_CANCEL.equals(operType)) {
            return R.string.module_me_cancel_succeed;
        } else if(OPERATE_QUIT.equals(operType)){
            return R.string.module_me_quit_succeed;
        } else {
            return R.string.module_me_user_submit_succeed;
        }
    }
}
