package com.lesso.module.login.mvp.contract;

import android.app.Activity;

import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;
import com.lesso.module.login.mvp.model.entity.LoginResultBean;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.Observable;
import me.jessyan.armscomponent.commonres.enums.LoginType;
import me.jessyan.armscomponent.commonres.enums.SmsCodeType;
import me.jessyan.armscomponent.commonsdk.base.bean.HttpResult;
import me.jessyan.armscomponent.commonsdk.utils.CountDownTimerUtils;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 11/13/2019 20:36
 * ================================================
 */
public interface MainLoginContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {

        //设置切换登录方式界面对应显示
        void changeLoginTypeView(LoginType loginType);

        Activity getActivity();

        RxPermissions getRxPermissions();

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
         * 用户登录
         *
         * @param user
         * @param pwd
         * @param sms
         * @param loginType
         * @return
         */
        Observable<HttpResult<LoginResultBean>> postLoginUserApp(String user, String pwd, String sms, LoginType loginType);

        /**
         * 用户注册
         */
        void goMainRegisterUserActivity();

        /**
         * 用户找回密码
         */
        void goMainForgetPasswordActivity();
    }
}
