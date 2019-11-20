package com.lesso.module.waybill.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.lesso.module.waybill.di.module.OrderAccountsChildModule;
import com.lesso.module.waybill.mvp.contract.OrderAccountsChildContract;

import com.jess.arms.di.scope.FragmentScope;
import com.lesso.module.waybill.mvp.ui.fragment.OrderAccountsChildFragment;


/**
 * ================================================
 * user：贺兴波
 * 2019/11/19 14:34
 * Description:
 * ================================================
 */
@FragmentScope
@Component(modules = OrderAccountsChildModule.class, dependencies = AppComponent.class)
public interface OrderAccountsChildComponent {
    void inject(OrderAccountsChildFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        OrderAccountsChildComponent.Builder view(OrderAccountsChildContract.View view);

        OrderAccountsChildComponent.Builder appComponent(AppComponent appComponent);

        OrderAccountsChildComponent build();
    }
}