package com.lesso.module.login.di.module;

import android.app.Dialog;
import android.support.v4.app.FragmentActivity;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import me.jessyan.armscomponent.commonres.dialog.ProgresDialog;

import com.lesso.module.login.mvp.contract.MainLoginContract;
import com.lesso.module.login.mvp.model.MainLoginModel;
import com.tbruyelle.rxpermissions2.RxPermissions;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 11/13/2019 20:36
 * ================================================
 */
@Module
public abstract class MainLoginModule {

    @Binds
    abstract MainLoginContract.Model bindMainLoginModel(MainLoginModel model);

    @ActivityScope
    @Provides
    static Dialog provideDialog(MainLoginContract.View view){
        return new ProgresDialog(view.getActivity());
    }

    @ActivityScope
    @Provides
    static RxPermissions provideRxPermissions(MainLoginContract.View view) {
        return new RxPermissions((FragmentActivity) view.getActivity());
    }
}