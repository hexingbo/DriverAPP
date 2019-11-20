package com.lesso.module.me.di.module;

import android.app.Dialog;

import com.jess.arms.di.scope.ActivityScope;
import com.lesso.module.me.mvp.contract.CompanyJoinDetailInfoContract;
import com.lesso.module.me.mvp.model.CompanyJoinDetailInfoModel;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import me.jessyan.armscomponent.commonres.dialog.ProgresDialog;


/**
 * ================================================
 * user：贺兴波
 * 2019/11/18 10:32
 * Description:
 * ================================================
 */
@Module
public abstract class CompanyJoinDetailInfoModule {

    @Binds
    abstract CompanyJoinDetailInfoContract.Model bindCompanyJoinDetailInfoModel(CompanyJoinDetailInfoModel model);

    @ActivityScope
    @Provides
    static Dialog provideDialog(CompanyJoinDetailInfoContract.View view){
        return new ProgresDialog(view.getActivity());
    }


}