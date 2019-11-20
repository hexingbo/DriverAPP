package com.lesso.module.login.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.lesso.module.login.mvp.contract.FindPasswordContract;
import com.lesso.module.login.mvp.model.api.service.ModuleLoginService;
import com.lesso.module.login.mvp.model.entity.SendSmsCodeBean;
import com.lesso.module.login.mvp.model.entity.SubmitFindPwdBean;

import javax.inject.Inject;

import io.reactivex.Observable;
import me.jessyan.armscomponent.commonres.enums.SmsCodeType;
import me.jessyan.armscomponent.commonsdk.base.bean.HttpResult;


/**
 * ================================================
 * user：贺兴波
 * 2019/11/14 19:01
 * Description:
 * ================================================
 */
@ActivityScope
public class FindPasswordModel extends BaseModel implements FindPasswordContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public FindPasswordModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<HttpResult> sendSMSCode(String phone, SmsCodeType smsCodeType) {
        int smsCode = smsCodeType == SmsCodeType.SmsCodeType_FindLoginPwd ? 5 : 5;
        return mRepositoryManager.obtainRetrofitService(ModuleLoginService.class).postSendSmsCode(new SendSmsCodeBean(phone, smsCode));
    }

    @Override
    public Observable<HttpResult> postFindPasswordApp(String account, String code, String password) {
        return mRepositoryManager.obtainRetrofitService(ModuleLoginService.class).postFindPasswordApp(
                new SubmitFindPwdBean( account, code, password));
    }
}