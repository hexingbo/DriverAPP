package com.lesso.module.waybill.mvp.contract;

import android.app.Activity;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.jess.arms.utils.PermissionUtil;
import com.tbruyelle.rxpermissions2.RxPermissions;


/**
 * ================================================
 * user：贺兴波
 * 2019/11/13 22:17
 * Description:
 * ================================================
 */
public interface MainWayBillContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {

        Activity getActivity();

        RxPermissions getRxPermissions();

        PermissionUtil.RequestPermission getRequestPermission();

        void setAddressInfo(String result);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {

    }
}
