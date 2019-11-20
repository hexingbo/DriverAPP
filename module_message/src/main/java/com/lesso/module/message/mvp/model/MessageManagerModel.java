package com.lesso.module.message.mvp.model;

import android.app.Application;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.FragmentScope;

import javax.inject.Inject;

import com.lesso.module.message.mvp.contract.MessageManagerContract;
import com.lesso.module.message.mvp.model.api.service.ModuleMessageService;
import com.lesso.module.message.mvp.model.entity.MessageBean;
import com.lesso.module.message.mvp.model.entity.SubmitGetMessageListBean;

import java.util.List;

import io.reactivex.Observable;
import me.jessyan.armscomponent.commonsdk.base.bean.HttpResult;

/**
 * =============================================
 * 作    者：贺兴波
 * 时    间：2019/11/13
 * 描    述：MessageManagerModel
 * =============================================
 */
@FragmentScope
public class MessageManagerModel extends BaseModel implements MessageManagerContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public MessageManagerModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<HttpResult<List<MessageBean>>> getMessageList(int current, int size, @NonNull String dirverId) {
        return mRepositoryManager.obtainRetrofitService(ModuleMessageService.class).getMessageList(new SubmitGetMessageListBean(current,size,new SubmitGetMessageListBean.ConditionBean(dirverId)));
    }

}