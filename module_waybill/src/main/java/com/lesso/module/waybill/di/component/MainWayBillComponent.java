package com.lesso.module.waybill.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.lesso.module.waybill.di.module.MainWayBillModule;
import com.lesso.module.waybill.mvp.contract.MainWayBillContract;

import com.jess.arms.di.scope.ActivityScope;
import com.lesso.module.waybill.mvp.ui.activity.MainWayBillActivity;


/**
 * ================================================
 * user：贺兴波
 * 2019/11/13 22:17
 * Description:
 * ================================================
 */
@ActivityScope
@Component(modules = MainWayBillModule.class, dependencies = AppComponent.class)
public interface MainWayBillComponent {
    void inject(MainWayBillActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        MainWayBillComponent.Builder view(MainWayBillContract.View view);

        MainWayBillComponent.Builder appComponent(AppComponent appComponent);

        MainWayBillComponent build();
    }
}