package com.lesso.module.waybill.di.module;

import com.jess.arms.di.scope.FragmentScope;
import com.lesso.module.waybill.mvp.contract.WayBillManagerContract;
import com.lesso.module.waybill.mvp.model.WayBillManagerModel;
import com.lesso.module.waybill.mvp.ui.adapter.MyPagerAdapter;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;


/**
 * ================================================
 * user：贺兴波
 * 2019/11/13 22:22
 * Description:
 * ================================================
 */
@Module
public abstract class WayBillManagerModule {

    @Binds
    abstract WayBillManagerContract.Model bindWayBillManagerModel(WayBillManagerModel model);

    @FragmentScope
    @Provides
    static MyPagerAdapter provideMyPagerAdapter(WayBillManagerContract.Model model, WayBillManagerContract.View view) {
        return  new MyPagerAdapter(view.getFragmentManager(), model.getTitles(), model.getFragments());
    }
}