package com.lesso.module.message.mvp.model;

import android.app.Application;
import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.lesso.module.message.mvp.contract.MessageDetailContract;
import com.lesso.module.message.mvp.model.api.service.ModuleMessageService;
import com.lesso.module.message.mvp.model.entity.SubmitMessageReadBean;

import io.reactivex.Observable;
import me.jessyan.armscomponent.commonsdk.base.bean.HttpResult;


/**
 * ================================================
 * user：贺兴波
 * 2019/11/20 12:25
 * Description:
 * ================================================
 */
@ActivityScope
public class MessageDetailModel extends BaseModel implements MessageDetailContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public MessageDetailModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<HttpResult> postUpdateMessageRead(@Nullable String userMsgId) {
        return mRepositoryManager.obtainRetrofitService(ModuleMessageService.class).postUpdateMessageRead(new SubmitMessageReadBean(userMsgId));
    }
}