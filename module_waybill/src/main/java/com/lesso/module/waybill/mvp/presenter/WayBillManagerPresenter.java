package com.lesso.module.waybill.mvp.presenter;

import android.app.Application;

import com.jess.arms.base.BaseFragment;
import com.jess.arms.base.BaseLazyLoadFragment;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import com.lesso.module.waybill.mvp.contract.WayBillManagerContract;

import java.util.ArrayList;


/**
 * ================================================
 * user：贺兴波
 * 2019/11/13 22:22
 * Description:
 * ================================================
 */
@FragmentScope
public class WayBillManagerPresenter extends BasePresenter<WayBillManagerContract.Model, WayBillManagerContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public WayBillManagerPresenter(WayBillManagerContract.Model model, WayBillManagerContract.View rootView) {
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
