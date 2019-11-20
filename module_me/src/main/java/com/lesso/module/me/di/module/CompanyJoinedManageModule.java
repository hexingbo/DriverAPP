package com.lesso.module.me.di.module;

import android.app.Dialog;
import android.support.v7.widget.RecyclerView;

import com.jess.arms.di.scope.ActivityScope;
import com.lesso.module.me.mvp.contract.CompanyJoinedManageContract;
import com.lesso.module.me.mvp.model.CompanyJoinedManageModel;
import com.lesso.module.me.mvp.model.entity.CompanyJoinedBean;
import com.lesso.module.me.mvp.ui.adapter.CompanyJoinedAdapter;

import java.util.ArrayList;
import java.util.List;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import me.jessyan.armscomponent.commonres.dialog.ProgresDialog;
import me.jessyan.armscomponent.commonsdk.utils.LayoutManagerUtil;


/**
 * ================================================
 * user：贺兴波
 * 2019/11/18 10:14
 * Description:
 * ================================================
 */
@Module
public abstract class CompanyJoinedManageModule {

    @Binds
    abstract CompanyJoinedManageContract.Model bindCompanyJoinedManageModel(CompanyJoinedManageModel model);

    @ActivityScope
    @Provides
    static Dialog provideDialog(CompanyJoinedManageContract.View view) {
        return new ProgresDialog(view.getActivity());
    }

    @ActivityScope
    @Provides
    static RecyclerView.LayoutManager provideLayoutManager(CompanyJoinedManageContract.View view) {
        return LayoutManagerUtil.getLinearLayoutManager_VERTICAL(view.getActivity());
    }

    @ActivityScope
    @Provides
    static List<CompanyJoinedBean> provideList() {
        return new ArrayList<>();
    }

    @ActivityScope
    @Provides
    static CompanyJoinedAdapter provideCompanyJoinedAdapter(List<CompanyJoinedBean> list, CompanyJoinedManageContract.View view) {
        CompanyJoinedAdapter adapter = new CompanyJoinedAdapter(list, view.getActivity());
        adapter.setOnItemClickListener(view.getOnItemClickListener());
        return adapter;
    }

}