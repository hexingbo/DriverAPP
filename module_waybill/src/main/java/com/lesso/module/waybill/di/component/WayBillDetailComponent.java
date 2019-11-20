package com.lesso.module.waybill.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.lesso.module.waybill.di.module.WayBillDetailModule;
import com.lesso.module.waybill.mvp.contract.WayBillDetailContract;

import com.jess.arms.di.scope.ActivityScope;
import com.lesso.module.waybill.mvp.ui.activity.WayBillDetailActivity;


/**
 * ================================================
 * user：贺兴波
 * 2019/11/15 23:03
 * Description:
 * ================================================
 */
@ActivityScope
@Component(modules = WayBillDetailModule.class, dependencies = AppComponent.class)
public interface WayBillDetailComponent {
    void inject(WayBillDetailActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        WayBillDetailComponent.Builder view(WayBillDetailContract.ViewI view);

        WayBillDetailComponent.Builder appComponent(AppComponent appComponent);

        WayBillDetailComponent build();
    }
}