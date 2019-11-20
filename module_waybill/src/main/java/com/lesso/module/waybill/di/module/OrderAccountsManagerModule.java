package com.lesso.module.waybill.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.lesso.module.waybill.mvp.contract.OrderAccountsManagerContract;
import com.lesso.module.waybill.mvp.model.OrderAccountsManagerModel;
import com.lesso.module.waybill.mvp.ui.adapter.MyPagerAdapter;


/**
 * ================================================
 * user：贺兴波
 * 2019/11/19 12:56
 * Description:
 * ================================================
 */
@Module
public abstract class OrderAccountsManagerModule {

    @Binds
    abstract OrderAccountsManagerContract.Model bindOrderAccountsManagerModel(OrderAccountsManagerModel model);

    @ActivityScope
    @Provides
    static MyPagerAdapter provideMyPagerAdapter(OrderAccountsManagerContract.Model model, OrderAccountsManagerContract.View view) {
        return  new MyPagerAdapter(view.getSupportFragmentManager(), model.getTitles(), model.getFragments());
    }
}