package com.lesso.module.login.di.module;

import android.app.Dialog;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import me.jessyan.armscomponent.commonres.dialog.ProgresDialog;

import com.lesso.module.login.mvp.contract.FindPasswordContract;
import com.lesso.module.login.mvp.model.FindPasswordModel;


/**
 * ================================================
 * user：贺兴波
 * 2019/11/14 19:01
 * Description:
 * ================================================
 */
@Module
public abstract class FindPasswordModule {

    @Binds
    abstract FindPasswordContract.Model bindFindPasswordModel(FindPasswordModel model);


    @ActivityScope
    @Provides
    static Dialog provideDialog(FindPasswordContract.View view){
        return new ProgresDialog(view.getActivity());
    }

}