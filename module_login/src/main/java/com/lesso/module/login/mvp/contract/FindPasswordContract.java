package com.lesso.module.login.mvp.contract;

import android.content.Context;

import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;

import io.reactivex.Observable;
import me.jessyan.armscomponent.commonres.enums.SmsCodeType;
import me.jessyan.armscomponent.commonsdk.base.bean.HttpResult;
import me.jessyan.armscomponent.commonsdk.utils.CountDownTimerUtils;


/**
 * ================================================
 * user：贺兴波
 * 2019/11/14 19:01
 * Description:
 * ================================================
 */
public interface FindPasswordContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {

        Context getActivity();

        CountDownTimerUtils.CountDownTimerRun getCountDownTimerRun();
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        /**
         * 发送短信验证码
         *
         * @param phone
         * @param smsCodeType
         * @return
         */
        Observable<HttpResult> sendSMSCode(String phone, SmsCodeType smsCodeType);


        /**
         * 找回密码
         * @param account
         * @param code
         * @param password
         * @return
         */
        Observable<HttpResult> postFindPasswordApp(String account, String code, String password);

    }
}
