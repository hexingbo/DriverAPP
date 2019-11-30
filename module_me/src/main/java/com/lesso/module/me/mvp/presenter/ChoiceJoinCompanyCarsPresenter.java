package com.lesso.module.me.mvp.presenter;

import android.app.Application;

import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import com.lesso.module.me.mvp.contract.ChoiceJoinCompanyCarsContract;


/**
 * ================================================
 * user：贺兴波
 * 2019/11/26 10:42
 * Description:
 * ================================================
 */
@ActivityScope
public class ChoiceJoinCompanyCarsPresenter extends BasePresenter<ChoiceJoinCompanyCarsContract.Model, ChoiceJoinCompanyCarsContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public ChoiceJoinCompanyCarsPresenter(ChoiceJoinCompanyCarsContract.Model model, ChoiceJoinCompanyCarsContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }
}
