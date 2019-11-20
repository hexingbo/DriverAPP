package com.lesso.module.mvp.contract;

import android.app.Activity;

import com.flyco.tablayout.listener.CustomTabEntity;
import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;
import com.jess.arms.utils.PermissionUtil;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;


/**
 * =============================================
 * 作    者：贺兴波
 * 时    间：2019/10/02 16:30
 * 描    述：
 * =============================================
 */
public interface MainStartContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {

        Activity getActivity();

        void setLocationAddress(String result);

        RxPermissions getRxPermissions();

        PermissionUtil.RequestPermission getRequestPermission();
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        /**
         * 获取底部导航菜单数据
         *
         * @return
         */
        ArrayList<CustomTabEntity> getTabEntity();
    }
}
