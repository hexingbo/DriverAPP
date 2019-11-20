package com.lesso.module.waybill.mvp.presenter;

import android.app.Application;

import com.jess.arms.base.BaseLazyLoadFragment;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.cache.Cache;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import com.lesso.module.waybill.mvp.contract.OrderAccountsManagerContract;

import java.util.ArrayList;


/**
 * ================================================
 * user：贺兴波
 * 2019/11/19 12:56
 * Description:
 * ================================================
 */
@ActivityScope
public class OrderAccountsManagerPresenter extends BasePresenter<OrderAccountsManagerContract.Model, OrderAccountsManagerContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public OrderAccountsManagerPresenter(OrderAccountsManagerContract.Model model, OrderAccountsManagerContract.View rootView) {
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

    public String[] getTitles() {
        return mModel.getTitles();
    }

    public ArrayList<BaseLazyLoadFragment> getFragments() {
        return mModel.getFragments();
    }
}
