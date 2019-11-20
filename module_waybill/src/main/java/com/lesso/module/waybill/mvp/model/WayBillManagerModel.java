package com.lesso.module.waybill.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.base.BaseLazyLoadFragment;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.FragmentScope;

import javax.inject.Inject;

import com.jess.arms.utils.AppManagerUtil;
import com.lesso.module.waybill.R;
import com.lesso.module.waybill.mvp.contract.WayBillManagerContract;
import com.lesso.module.waybill.mvp.ui.fragment.WayBillManagerChildFragment;

import java.util.ArrayList;


/**
 * ================================================
 * user：贺兴波
 * 2019/11/13 22:22
 * Description:
 * ================================================
 */
@FragmentScope
public class WayBillManagerModel extends BaseModel implements WayBillManagerContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public WayBillManagerModel(IRepositoryManager repositoryManager) {
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
        return AppManagerUtil.appContext().getResources().getStringArray(R.array.waybill_state);
    }

    @Override
    public ArrayList<BaseLazyLoadFragment> getFragments() {
        ArrayList<BaseLazyLoadFragment> mFragments = new ArrayList<>();
        for (String title : getTitles()) {
            WayBillManagerChildFragment childFragment = WayBillManagerChildFragment.newInstance();
            childFragment.setData(title);
            mFragments.add(childFragment);
        }
        return mFragments;
    }
}