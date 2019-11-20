package com.lesso.module.me.di.module;

import android.app.Dialog;

import com.jess.arms.di.scope.ActivityScope;
import com.lesso.module.me.mvp.contract.AboutUsContract;
import com.lesso.module.me.mvp.contract.MainMyContract;
import com.lesso.module.me.mvp.model.AboutUsModel;

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
public abstract class AboutUsModule {

    @Binds
    abstract AboutUsContract.Model bindAboutUsModel(AboutUsModel model);


    @ActivityScope
    @Provides
    static Dialog provideDialog(AboutUsContract.View view){
        return new ProgresDialog(view.getActivity());
    }
}