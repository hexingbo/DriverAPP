package com.lesso.module.me.di.module;

import android.app.Dialog;
import android.support.v4.app.FragmentActivity;

import com.jess.arms.di.scope.ActivityScope;
import com.lesso.module.me.R;
import com.lesso.module.me.mvp.contract.AddCarsDetailInfoContract;
import com.lesso.module.me.mvp.model.AddCarsDetailInfoModel;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import me.jessyan.armscomponent.commonres.dialog.MaterialDialog;
import me.jessyan.armscomponent.commonres.dialog.MyHintDialog;
import me.jessyan.armscomponent.commonres.dialog.ProgresDialog;


/**
 * ================================================
 * user：贺兴波
 * 2019/11/26 10:41
 * Description:
 * ================================================
 */
@Module
public abstract class AddCarsDetailInfoModule {

    @Binds
    abstract AddCarsDetailInfoContract.Model bindAddCarsDetailInfoModel(AddCarsDetailInfoModel model);

    @ActivityScope
    @Provides
    static RxPermissions provideRxPermissions(AddCarsDetailInfoContract.View view) {
        return new RxPermissions((FragmentActivity) view.getActivity());
    }

    @ActivityScope
    @Provides
    static MaterialDialog materialDialog(AddCarsDetailInfoContract.View view) {
        return new MaterialDialog(view.getActivity()).setCanceledOnTouchOutside(true).setTitle(view.getActivity().getResources().getString(R.string.str_name_hint));
    }

    @ActivityScope
    @Provides
    static MyHintDialog provideMyHintDialog(AddCarsDetailInfoContract.View view) {
        return new MyHintDialog(view.getActivity());
    }

    @ActivityScope
    @Provides
    static Dialog provideDialog(AddCarsDetailInfoContract.View view) {
        return new ProgresDialog(view.getActivity());
    }

    @ActivityScope
    @Provides
    static List<File> provideList() {
        return new ArrayList<>();
    }
    
}