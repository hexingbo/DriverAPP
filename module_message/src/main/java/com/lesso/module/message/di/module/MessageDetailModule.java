package com.lesso.module.message.di.module;

import android.app.Dialog;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import me.jessyan.armscomponent.commonres.dialog.ProgresDialog;

import com.jess.arms.di.scope.FragmentScope;
import com.lesso.module.message.mvp.contract.MessageDetailContract;
import com.lesso.module.message.mvp.contract.MessageManagerContract;
import com.lesso.module.message.mvp.model.MessageDetailModel;


/**
 * ================================================
 * user：贺兴波
 * 2019/11/20 12:25
 * Description:
 * ================================================
 */
@Module
public abstract class MessageDetailModule {

    @Binds
    abstract MessageDetailContract.Model bindMessageDetailModel(MessageDetailModel model);

    @ActivityScope
    @Provides
    static Dialog provideDialog(MessageDetailContract.View view) {
        return new ProgresDialog(view.getActivity());
    }
}