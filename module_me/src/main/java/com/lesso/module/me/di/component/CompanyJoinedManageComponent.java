package com.lesso.module.me.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.lesso.module.me.di.module.CompanyJoinedManageModule;
import com.lesso.module.me.mvp.contract.CompanyJoinedManageContract;

import com.jess.arms.di.scope.ActivityScope;
import com.lesso.module.me.mvp.ui.activity.CompanyJoinedManageActivity;


/**
 * ================================================
 * user：贺兴波
 * 2019/11/18 10:14
 * Description:
 * ================================================
 */
@ActivityScope
@Component(modules = CompanyJoinedManageModule.class, dependencies = AppComponent.class)
public interface CompanyJoinedManageComponent {
    void inject(CompanyJoinedManageActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        CompanyJoinedManageComponent.Builder view(CompanyJoinedManageContract.View view);

        CompanyJoinedManageComponent.Builder appComponent(AppComponent appComponent);

        CompanyJoinedManageComponent build();
    }
}