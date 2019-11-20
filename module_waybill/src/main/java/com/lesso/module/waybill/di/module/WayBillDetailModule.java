package com.lesso.module.waybill.di.module;

import android.app.Dialog;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;

import com.jess.arms.di.scope.ActivityScope;
import com.lesso.module.waybill.R;
import com.lesso.module.waybill.mvp.contract.WayBillDetailContract;
import com.lesso.module.waybill.mvp.model.WayBillDetailModel;
import com.lesso.module.waybill.mvp.model.entity.WayBillDetailBean;
import com.lesso.module.waybill.mvp.ui.adapter.TransportTrackAdapter;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;
import java.util.List;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import me.jessyan.armscomponent.commonres.dialog.MaterialDialog;
import me.jessyan.armscomponent.commonres.dialog.MyHintDialog;
import me.jessyan.armscomponent.commonres.dialog.ProgresDialog;
import me.jessyan.armscomponent.commonsdk.utils.LayoutManagerUtil;


/**
 * ================================================
 * user：贺兴波
 * 2019/11/15 23:03
 * Description:
 * ================================================
 */
@Module
public abstract class WayBillDetailModule {

    @Binds
    abstract WayBillDetailContract.Model bindWayBillDetailModel(WayBillDetailModel model);


    @ActivityScope
    @Provides
    static RxPermissions provideRxPermissions(WayBillDetailContract.ViewI view) {
        return new RxPermissions((FragmentActivity) view.getActivity());
    }

    @ActivityScope
    @Provides
    static MaterialDialog materialDialog(WayBillDetailContract.ViewI view) {
        return new MaterialDialog(view.getActivity()).setCanceledOnTouchOutside(true).setTitle(view.getActivity().getResources().getString(R.string.str_name_hint));
    }

    @ActivityScope
    @Provides
    static MyHintDialog provideMyHintDialog(WayBillDetailContract.ViewI view) {
        return new MyHintDialog(view.getActivity());
    }

    @ActivityScope
    @Provides
    static Dialog provideDialog(WayBillDetailContract.ViewI view) {
        return new ProgresDialog(view.getActivity());
    }


    @ActivityScope
    @Provides
    static RecyclerView.LayoutManager provideLayoutManager(WayBillDetailContract.ViewI view) {
        return LayoutManagerUtil.getLinearLayoutManager_VERTICAL(view.getActivity());
    }

    @ActivityScope
    @Provides
    static List<WayBillDetailBean.TransportTrackBean> provideList() {
        return new ArrayList<>();
    }

    @ActivityScope
    @Provides
    static TransportTrackAdapter provideTransportTrackAdapter(List<WayBillDetailBean.TransportTrackBean> list, WayBillDetailContract.ViewI view) {
        TransportTrackAdapter adapter = new TransportTrackAdapter(list, view.getOrderStatus(), view.getActivity());
        return adapter;
    }


}