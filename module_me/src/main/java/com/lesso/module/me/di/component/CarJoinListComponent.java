package com.lesso.module.me.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.lesso.module.me.di.module.CarJoinListModule;
import com.lesso.module.me.mvp.contract.CarJoinListContract;
import com.lesso.module.me.mvp.ui.activity.CarJoinListActivity;

import dagger.BindsInstance;
import dagger.Component;


/**
 * ================================================
 * user：贺兴波
 * 2019/11/26 11:37
 * Description:
 * ================================================
 */
@ActivityScope
@Component(modules = CarJoinListModule.class, dependencies = AppComponent.class)
public interface CarJoinListComponent {
    void inject(CarJoinListActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        CarJoinListComponent.Builder view(CarJoinListContract.View view);

        CarJoinListComponent.Builder appComponent(AppComponent appComponent);

        CarJoinListComponent build();
    }
}