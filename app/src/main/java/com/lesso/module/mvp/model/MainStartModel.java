package com.lesso.module.mvp.model;

import android.app.Application;
import android.content.res.TypedArray;

import com.lesso.module.R;
import com.lesso.module.mvp.model.entity.TabEntity;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.lesso.module.mvp.contract.MainStartContract;

import java.util.ArrayList;


/**
 * =============================================
 * 作    者：贺兴波
 * 时    间：2019/10/02 16:30
 * 描    述：
 * =============================================
 */
@ActivityScope
public class MainStartModel extends BaseModel implements MainStartContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public MainStartModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public ArrayList<CustomTabEntity> getTabEntity() {
        ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
        TypedArray mIconUnSelectIds =mApplication.getResources().obtainTypedArray(R.array.main_tab_un_select);
        TypedArray mIconSelectIds = mApplication.getResources().obtainTypedArray(R.array.main_tab_select);
        String[] mainTitles =mApplication.getResources().getStringArray(R.array.main_title);
        for (int i = 0; i < mainTitles.length; i++) {
            int unSelectId = mIconUnSelectIds.getResourceId(i, R.drawable.tab_home_unselect);
            int selectId = mIconSelectIds.getResourceId(i, R.drawable.tab_home_select);
            mTabEntities.add(new TabEntity(mainTitles[i], selectId, unSelectId));
        }
        mIconUnSelectIds.recycle();
        mIconSelectIds.recycle();
        return mTabEntities;
    }
}