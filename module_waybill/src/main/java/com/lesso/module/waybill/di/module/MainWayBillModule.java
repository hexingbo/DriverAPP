package com.lesso.module.waybill.di.module;

import android.support.v4.app.FragmentActivity;

import com.jess.arms.di.scope.ActivityScope;
import com.lesso.module.waybill.mvp.contract.MainWayBillContract;
import com.lesso.module.waybill.mvp.model.MainWayBillModel;
import com.tbruyelle.rxpermissions2.RxPermissions;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;


/**
 * ================================================
 * user：贺兴波
 * 2019/11/13 22:17
 * Description:
 * ================================================
 */
@Module
public abstract class MainWayBillModule {

    @Binds
    abstract MainWayBillContract.Model bindMainWayBillModel(MainWayBillModel model);

    @ActivityScope
    @Provides
    static RxPermissions provideRxPermissions(MainWayBillContract.View view) {
        return new RxPermissions((FragmentActivity) view.getActivity());
    }

}