package com.lesso.module.waybill.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.base.BaseLazyLoadFragment;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.jess.arms.utils.AppManagerUtil;
import com.lesso.module.waybill.R;
import com.lesso.module.waybill.mvp.contract.OrderAccountsManagerContract;
import com.lesso.module.waybill.mvp.ui.fragment.OrderAccountsChildFragment;

import java.util.ArrayList;

import javax.inject.Inject;


/**
 * ================================================
 * user：贺兴波
 * 2019/11/19 12:56
 * Description:
 * ================================================
 */
@ActivityScope
public class OrderAccountsManagerModel extends BaseModel implements OrderAccountsManagerContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public OrderAccountsManagerModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public String[] getTitles() {
        return AppManagerUtil.appContext().getResources().getStringArray(R.array.order_accounts_state);
    }

    @Override
    public ArrayList<BaseLazyLoadFragment> getFragments() {
        ArrayList<BaseLazyLoadFragment> mFragments = new ArrayList<>();
        for (String title : getTitles()) {
            OrderAccountsChildFragment childFragment = OrderAccountsChildFragment.newInstance();
            childFragment.setData(title);
            mFragments.add(childFragment);
        }
        return mFragments;
    }
}