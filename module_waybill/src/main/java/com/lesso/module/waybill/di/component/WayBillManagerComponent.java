package com.lesso.module.waybill.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.lesso.module.waybill.di.module.WayBillManagerModule;
import com.lesso.module.waybill.mvp.contract.WayBillManagerContract;

import com.jess.arms.di.scope.FragmentScope;
import com.lesso.module.waybill.mvp.ui.fragment.WayBillManagerFragment;


/**
 * ================================================
 * user：贺兴波
 * 2019/11/13 22:22
 * Description:
 * ================================================
 */
@FragmentScope
@Component(modules = WayBillManagerModule.class, dependencies = AppComponent.class)
public interface WayBillManagerComponent {
    void inject(WayBillManagerFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        WayBillManagerComponent.Builder view(WayBillManagerContract.View view);

        WayBillManagerComponent.Builder appComponent(AppComponent appComponent);

        WayBillManagerComponent build();
    }
}