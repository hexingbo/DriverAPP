package com.lesso.module.waybill.di.module;

import android.app.Dialog;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.utils.AppManagerUtil;
import com.lesso.module.waybill.mvp.contract.OrderAccountsChildContract;
import com.lesso.module.waybill.mvp.model.OrderAccountsChildModel;
import com.lesso.module.waybill.mvp.model.entity.OrderAccountBean;
import com.lesso.module.waybill.mvp.ui.adapter.OrderAccountListAdapter;
import com.zhouyou.recyclerview.adapter.BaseRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import me.jessyan.armscomponent.commonres.constant.CommonConstant;
import me.jessyan.armscomponent.commonres.dialog.ProgresDialog;
import me.jessyan.armscomponent.commonres.enums.WayBillStateType;
import me.jessyan.armscomponent.commonsdk.core.RouterHub;
import me.jessyan.armscomponent.commonsdk.utils.LayoutManagerUtil;


/**
 * ================================================
 * user：贺兴波
 * 2019/11/19 14:34
 * Description:
 * ================================================
 */
@Module
public abstract class OrderAccountsChildModule {

    @Binds
    abstract OrderAccountsChildContract.Model bindOrderAccountsChildModel(OrderAccountsChildModel model);

    @FragmentScope
    @Provides
    static Dialog provideDialog(OrderAccountsChildContract.View view) {
        return new ProgresDialog(view.getActivity());
    }

    @FragmentScope
    @Provides
    static RecyclerView.LayoutManager provideLayoutManager(OrderAccountsChildContract.View view) {
        return LayoutManagerUtil.getLinearLayoutManager_VERTICAL(view.getActivity());
    }

    @FragmentScope
    @Provides
    static List<OrderAccountBean> provideList() {
        return new ArrayList<>();
    }

    @FragmentScope
    @Provides
    static OrderAccountListAdapter provideOrderAccountListAdapter(List<OrderAccountBean> list, OrderAccountsChildContract.View view) {

        OrderAccountListAdapter adapter = new OrderAccountListAdapter(list, view.getOrderAccountStateType(),view.getActivity());
        adapter.setOnAdapterViewClickListener(view.getOnAdapterViewClickListener());
        return adapter;
    }

}