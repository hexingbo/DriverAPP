package com.lesso.module.di.module;

import android.app.Dialog;
import android.support.v4.app.FragmentActivity;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import me.jessyan.armscomponent.commonres.dialog.MaterialDialog;
import me.jessyan.armscomponent.commonres.dialog.MyHintDialog;
import me.jessyan.armscomponent.commonres.dialog.ProgresDialog;

import com.jess.arms.di.scope.ActivityScope;
import com.lesso.module.R;
import com.lesso.module.mvp.contract.MainStartContract;
import com.lesso.module.mvp.model.MainStartModel;
import com.tbruyelle.rxpermissions2.RxPermissions;


/**
 * =============================================
 * 作    者：贺兴波
 * 时    间：2019/10/02 16:30
 * 描    述：
 * =============================================
 */
@Module
public abstract class MainStartModule {

    @Binds
    abstract MainStartContract.Model bindMainStartModel(MainStartModel model);

    @ActivityScope
    @Provides
    static RxPermissions provideRxPermissions(MainStartContract.View view) {
        return new RxPermissions((FragmentActivity) view.getActivity());
    }

    @ActivityScope
    @Provides
    static MaterialDialog materialDialog(MainStartContract.View view) {
        return new MaterialDialog(view.getActivity()).setCanceledOnTouchOutside(true).setTitle(view.getActivity().getResources().getString(R.string.str_name_hint));
    }

    @ActivityScope
    @Provides
    static MyHintDialog provideMyHintDialog(MainStartContract.View view) {
        return new MyHintDialog(view.getActivity());
    }

    @ActivityScope
    @Provides
    static Dialog provideDialog(MainStartContract.View view) {
        return new ProgresDialog(view.getActivity());
    }

}