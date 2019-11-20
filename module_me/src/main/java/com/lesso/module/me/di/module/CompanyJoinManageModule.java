package com.lesso.module.me.di.module;

import android.app.Dialog;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.utils.AppManagerUtil;
import com.lesso.module.me.mvp.contract.CompanyJoinManageContract;
import com.lesso.module.me.mvp.model.CompanyJoinManageModel;
import com.lesso.module.me.mvp.model.entity.CompanyJoinBean;
import com.lesso.module.me.mvp.ui.activity.CompanyJoinDetailInfoActivity;
import com.lesso.module.me.mvp.ui.adapter.ChoseCompanyJoinListAdapter;
import com.zhouyou.recyclerview.adapter.BaseRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import me.jessyan.armscomponent.commonres.base.BaseIntentBean;
import me.jessyan.armscomponent.commonres.dialog.ProgresDialog;
import me.jessyan.armscomponent.commonsdk.utils.LayoutManagerUtil;


/**
 * ================================================
 * user：贺兴波
 * 2019/11/18 10:28
 * Description:
 * ================================================
 */
@Module
public abstract class CompanyJoinManageModule {

    @Binds
    abstract CompanyJoinManageContract.Model bindCompanyJoinManageModel(CompanyJoinManageModel model);

    @ActivityScope
    @Provides
    static Dialog provideDialog(CompanyJoinManageContract.View view) {
        return new ProgresDialog(view.getActivity());
    }

    @ActivityScope
    @Provides
    static RecyclerView.LayoutManager provideLayoutManager(CompanyJoinManageContract.View view) {
        return LayoutManagerUtil.getLinearLayoutManager_VERTICAL(view.getActivity());
    }

    @ActivityScope
    @Provides
    static List<CompanyJoinBean> provideList() {
        return new ArrayList<>();
    }

    @ActivityScope
    @Provides
    static ChoseCompanyJoinListAdapter provideChoseCompanyJoinListAdapter(List<CompanyJoinBean> list, CompanyJoinManageContract.View view) {

        ChoseCompanyJoinListAdapter adapter = new ChoseCompanyJoinListAdapter(list, view.getActivity());
        adapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener<CompanyJoinBean>() {
            @Override
            public void onItemClick(View view, CompanyJoinBean item, int position) {
//                Bundle bundle = new Bundle();
//                bundle.putString(CommonConstant.IntentWayBillDetailKey, item.getOrderId());
//                Utils.navigation(view.getContext(), RouterHub.Waybill_WayBillDetailActivity, bundle);
                AppManagerUtil.jump(CompanyJoinDetailInfoActivity.class, CompanyJoinDetailInfoActivity.IntentValue,
                        new BaseIntentBean<>(item.getCompanyId(), true));
            }
        });
        return adapter;
    }

}