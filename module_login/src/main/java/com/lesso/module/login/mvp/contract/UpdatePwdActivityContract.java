package com.lesso.module.login.mvp.contract;

import android.app.Activity;
import android.support.annotation.Nullable;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.lesso.module.login.mvp.model.entity.SubmitUpdatePwdBean;

import io.reactivex.Observable;
import me.jessyan.armscomponent.commonres.enums.SmsCodeType;
import me.jessyan.armscomponent.commonsdk.base.bean.HttpResult;
import me.jessyan.armscomponent.commonsdk.utils.CountDownTimerUtils;


/**
 * ================================================
 * user：贺兴波
 * 2019/11/22 09:47
 * Description:
 * ================================================
 */
public interface UpdatePwdActivityContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {

        Activity getActivity();

    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {

        /**
         * 修改密码
         *
         * @param bean
         * @return
         */
        Observable<HttpResult> postUpdatePassword(@Nullable SubmitUpdatePwdBean bean);
    }
}
