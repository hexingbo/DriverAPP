package com.lesso.module.me.di.module;

import android.app.Dialog;

import com.jess.arms.di.scope.ActivityScope;
import com.lesso.module.me.mvp.contract.UserInfoContract;
import com.lesso.module.me.mvp.model.UserInfoModel;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import me.jessyan.armscomponent.commonres.dialog.ProgresDialog;


/**
 * ================================================
 * user：贺兴波
 * 2019/11/18 10:27
 * Description:
 * ================================================
 */
@Module
public abstract class UserInfoModule {

    @Binds
    abstract UserInfoContract.Model bindUserInfoModel(UserInfoModel model);

    @ActivityScope
    @Provides
    static Dialog provideDialog(UserInfoContract.View view){
        return new ProgresDialog(view.getActivity());
    }
}