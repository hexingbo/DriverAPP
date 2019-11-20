package com.lesso.module.me.di.module;

import android.app.Dialog;

import com.jess.arms.di.scope.FragmentScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import me.jessyan.armscomponent.commonres.dialog.ProgresDialog;

import com.lesso.module.me.mvp.contract.MainMyContract;
import com.lesso.module.me.mvp.model.MainMyModel;


/**
 * =============================================
 * 作    者：贺兴波
 * 时    间：2019/09/27 11:24
 * 描    述：
 * =============================================
 */
@Module
public abstract class MainMyModule {

    @Binds
    abstract MainMyContract.Model bindMainMyModel(MainMyModel model);

    @FragmentScope
    @Provides
    static Dialog provideDialog(MainMyContract.View view){
        return new ProgresDialog(view.getActivity());
    }
}