package com.lesso.module.waybill.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.lesso.module.waybill.di.module.OrderAccountsManagerModule;
import com.lesso.module.waybill.mvp.contract.OrderAccountsManagerContract;

import com.jess.arms.di.scope.ActivityScope;
import com.lesso.module.waybill.mvp.ui.activity.OrderAccountsManagerActivity;


/**
 * ================================================
 * user：贺兴波
 * 2019/11/19 12:56
 * Description:
 * ================================================
 */
@ActivityScope
@Component(modules = OrderAccountsManagerModule.class, dependencies = AppComponent.class)
public interface OrderAccountsManagerComponent {
    void inject(OrderAccountsManagerActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        OrderAccountsManagerComponent.Builder view(OrderAccountsManagerContract.View view);

        OrderAccountsManagerComponent.Builder appComponent(AppComponent appComponent);

        OrderAccountsManagerComponent build();
    }
}