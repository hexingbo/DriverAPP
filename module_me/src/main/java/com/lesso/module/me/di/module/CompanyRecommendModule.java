package com.lesso.module.me.di.module;

import android.app.Dialog;
import android.support.v7.widget.RecyclerView;

import com.jess.arms.di.scope.ActivityScope;
import com.lesso.module.me.mvp.contract.CompanyRecommendContract;
import com.lesso.module.me.mvp.model.CompanyRecommendModel;
import com.lesso.module.me.mvp.model.entity.CompanyJoinBean;
import com.lesso.module.me.mvp.ui.adapter.ChoseCompanyRecommendListAdapter;
import com.zhouyou.recyclerview.manager.StateGridLayoutManager;

import java.util.ArrayList;
import java.util.List;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import me.jessyan.armscomponent.commonres.dialog.ProgresDialog;


/**
 * ================================================
 * user：贺兴波
 * 2019/11/22 09:41
 * Description:
 * ================================================
 */
@Module
public abstract class CompanyRecommendModule {

    @Binds
    abstract CompanyRecommendContract.Model bindCompanyRecommendModel(CompanyRecommendModel model);

    @ActivityScope
    @Provides
    static Dialog provideDialog(CompanyRecommendContract.View view) {
        return new ProgresDialog(view.getActivity());
    }

    @ActivityScope
    @Provides
    static RecyclerView.LayoutManager provideLayoutManager(CompanyRecommendContract.View view) {
      return new StateGridLayoutManager(view.getActivity(),3);
    }

    @ActivityScope
    @Provides
    static List<CompanyJoinBean> provideList() {
        return new ArrayList<>();
    }

    @ActivityScope
    @Provides
    static ChoseCompanyRecommendListAdapter provideChoseCompanyRecommendListAdapter(List<CompanyJoinBean> list, CompanyRecommendContract.View view) {

        ChoseCompanyRecommendListAdapter adapter = new ChoseCompanyRecommendListAdapter(list, view.getActivity());
        return adapter;
    }
}