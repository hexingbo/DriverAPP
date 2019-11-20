package com.lesso.module.waybill.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.lesso.module.waybill.di.module.WayBillManagerChildModule;
import com.lesso.module.waybill.mvp.contract.WayBillManagerChildContract;

import com.jess.arms.di.scope.FragmentScope;
import com.lesso.module.waybill.mvp.ui.fragment.WayBillManagerChildFragment;


/**
 * ================================================
 * user：贺兴波
 * 2019/11/15 17:52
 * Description:
 * ================================================
 */
@FragmentScope
@Component(modules = WayBillManagerChildModule.class, dependencies = AppComponent.class)
public interface WayBillManagerChildComponent {
    void inject(WayBillManagerChildFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        WayBillManagerChildComponent.Builder view(WayBillManagerChildContract.View view);

        WayBillManagerChildComponent.Builder appComponent(AppComponent appComponent);

        WayBillManagerChildComponent build();
    }
}