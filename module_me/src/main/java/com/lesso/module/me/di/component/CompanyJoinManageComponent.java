package com.lesso.module.me.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.lesso.module.me.di.module.CompanyJoinManageModule;
import com.lesso.module.me.mvp.contract.CompanyJoinManageContract;

import com.jess.arms.di.scope.ActivityScope;
import com.lesso.module.me.mvp.ui.activity.CompanyJoinManageActivity;


/**
 * ================================================
 * user：贺兴波
 * 2019/11/18 10:28
 * Description:
 * ================================================
 */
@ActivityScope
@Component(modules = CompanyJoinManageModule.class, dependencies = AppComponent.class)
public interface CompanyJoinManageComponent {
    void inject(CompanyJoinManageActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        CompanyJoinManageComponent.Builder view(CompanyJoinManageContract.View view);

        CompanyJoinManageComponent.Builder appComponent(AppComponent appComponent);

        CompanyJoinManageComponent build();
    }
}