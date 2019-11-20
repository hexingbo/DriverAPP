package com.lesso.module.me.di.module;

import android.app.Dialog;

import com.jess.arms.di.scope.ActivityScope;
import com.lesso.module.me.mvp.contract.UpdatePwdContract;
import com.lesso.module.me.mvp.model.UpdatePwdModel;

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
public abstract class UpdatePwdModule {

    @Binds
    abstract UpdatePwdContract.Model bindUpdatePwdModel(UpdatePwdModel model);

    @ActivityScope
    @Provides
    static Dialog provideDialog(UpdatePwdContract.View view){
        return new ProgresDialog(view.getActivity());
    }
}