package com.lesso.module.login.mvp.model;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;
import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.jess.arms.utils.DeviceUtils;
import com.lesso.module.login.mvp.contract.MainLoginContract;
import com.lesso.module.login.mvp.model.api.service.ModuleLoginService;
import com.lesso.module.login.mvp.model.entity.LoginResultBean;
import com.lesso.module.login.mvp.model.entity.SendSmsCodeBean;
import com.lesso.module.login.mvp.model.entity.SubmitLoginBean;

import io.reactivex.Observable;
import me.jessyan.armscomponent.commonres.enums.LoginType;
import me.jessyan.armscomponent.commonres.enums.SmsCodeType;
import me.jessyan.armscomponent.commonsdk.base.bean.HttpResult;
import me.jessyan.armscomponent.commonsdk.core.RouterHub;


@ActivityScope
public class MainLoginModel extends BaseModel implements MainLoginContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public MainLoginModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable sendSMSCode(String phone, SmsCodeType smsCodeType) {
        int smsCode = smsCodeType == SmsCodeType.SmsCodeType_Login ? 13 : 13;
        return mRepositoryManager.obtainRetrofitService(ModuleLoginService.class).postSendSmsCode(new SendSmsCodeBean(phone, smsCode));
    }

    @Override
    public Observable<HttpResult<LoginResultBean>> postLoginUserApp(String user, String pwd, String smsCode, LoginType loginType) {
        return mRepositoryManager.obtainRetrofitService(ModuleLoginService.class).postLoginUserApp(
                new SubmitLoginBean(user, smsCode, pwd, DeviceUtils.getDeviceId(mApplication), loginType == LoginType.LoginType_SmsCode ? 2 : 1));
    }

    @Override
    public void goMainRegisterUserActivity() {
        ARouter.getInstance().build(RouterHub.Loging_RegisterUserActivity).navigation(mApplication);
    }

    @Override
    public void goMainForgetPasswordActivity() {
        ARouter.getInstance().build(RouterHub.Loging_FindPasswordActivity).navigation(mApplication);
    }
}