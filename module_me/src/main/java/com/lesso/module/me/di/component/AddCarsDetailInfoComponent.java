package com.lesso.module.me.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.lesso.module.me.di.module.AddCarsDetailInfoModule;
import com.lesso.module.me.mvp.contract.AddCarsDetailInfoContract;

import com.jess.arms.di.scope.ActivityScope;
import com.lesso.module.me.mvp.ui.activity.AddCarsDetailInfoActivity;


/**
 * ================================================
 * user：贺兴波
 * 2019/11/26 10:41
 * Description:
 * ================================================
 */
@ActivityScope
@Component(modules = AddCarsDetailInfoModule.class, dependencies = AppComponent.class)
public interface AddCarsDetailInfoComponent {
    void inject(AddCarsDetailInfoActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        AddCarsDetailInfoComponent.Builder view(AddCarsDetailInfoContract.View view);

        AddCarsDetailInfoComponent.Builder appComponent(AppComponent appComponent);

        AddCarsDetailInfoComponent build();
    }
}