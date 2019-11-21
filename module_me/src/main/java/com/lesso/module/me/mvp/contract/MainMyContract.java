package com.lesso.module.me.mvp.contract;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.lesso.module.me.mvp.model.entity.UserInfoBean;

import io.reactivex.Observable;
import me.jessyan.armscomponent.commonres.base.ILoadlayoutView;
import me.jessyan.armscomponent.commonsdk.base.bean.HttpResult;


/**
 * =============================================
 * 作    者：贺兴波
 * 时    间：2019/09/27 11:24
 * 描    述：
 * =============================================
 */
public interface MainMyContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView , ILoadlayoutView {

        Activity getActivity();

        void setUserInfo(UserInfoBean bean);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {

        /**
         * 查询个人中心用户信息
         *
         * @param driverId
         * @return
         */
        Observable<HttpResult<UserInfoBean>> getUserInfo(@NonNull String driverId);

        Observable<HttpResult> postLoginOut(@NonNull String account, @NonNull String deviceNo);
    }
}
