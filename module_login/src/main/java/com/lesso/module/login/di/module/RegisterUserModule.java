package com.lesso.module.login.di.module;

import android.app.Dialog;
import android.support.v4.app.FragmentActivity;

import com.jess.arms.di.scope.ActivityScope;
import com.lesso.module.login.mvp.contract.RegisterUserContract;
import com.lesso.module.login.mvp.model.RegisterUserModel;
import com.tbruyelle.rxpermissions2.RxPermissions;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import me.jessyan.armscomponent.commonres.dialog.ProgresDialog;


/**
 * ================================================
 * user：贺兴波
 * 2019/11/14 19:01
 * Description:
 * ================================================
 */
@Module
public abstract class RegisterUserModule {

    @Binds
    abstract RegisterUserContract.Model bindRegisterUserModel(RegisterUserModel model);

    @ActivityScope
    @Provides
    static Dialog provideDialog(RegisterUserContract.View view){
        return new ProgresDialog(view.getActivity());
    }

    @ActivityScope
    @Provides
    static RxPermissions provideRxPermissions(RegisterUserContract.View view) {
        return new RxPermissions((FragmentActivity) view.getActivity());
    }

}