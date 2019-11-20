package com.lesso.module.waybill.di.module;

import android.app.Dialog;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;

import com.jess.arms.di.scope.FragmentScope;
import com.lesso.module.waybill.R;
import com.lesso.module.waybill.mvp.contract.WayBillManagerChildContract;
import com.lesso.module.waybill.mvp.model.WayBillManagerChildModel;
import com.lesso.module.waybill.mvp.model.entity.WayBillListBean;
import com.lesso.module.waybill.mvp.ui.adapter.WayBillListAdapter;
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
 * 2019/11/15 17:52
 * Description:
 * ================================================
 */
@Module
public abstract class WayBillManagerChildModule {

    @Binds
    abstract WayBillManagerChildContract.Model bindWayBillManagerChildModel(WayBillManagerChildModel model);

    @FragmentScope
    @Provides
    static RxPermissions provideRxPermissions(WayBillManagerChildContract.View view) {
        return new RxPermissions((FragmentActivity) view.getActivity());
    }

    @FragmentScope
    @Provides
    static MaterialDialog materialDialog(WayBillManagerChildContract.View view) {
        return new MaterialDialog(view.getActivity()).setCanceledOnTouchOutside(true).setTitle(view.getActivity().getResources().getString(R.string.str_name_hint));
    }

    @FragmentScope
    @Provides
    static Dialog provideDialog(WayBillManagerChildContract.View view) {
        return new ProgresDialog(view.getActivity());
    }

    @FragmentScope
    @Provides
    static MyHintDialog provideMyHintDialog(WayBillManagerChildContract.View view) {
        return new MyHintDialog(view.getActivity());
    }

    @FragmentScope
    @Provides
    static RecyclerView.LayoutManager provideLayoutManager(WayBillManagerChildContract.View view) {
        return LayoutManagerUtil.getLinearLayoutManager_VERTICAL(view.getActivity());
    }

    @FragmentScope
    @Provides
    static List<WayBillListBean> provideList() {
        return new ArrayList<>();
    }

    @FragmentScope
    @Provides
    static WayBillListAdapter provideWayBillListAdapter(List<WayBillListBean> list, WayBillManagerChildContract.View view) {

        WayBillListAdapter adapter = new WayBillListAdapter(list, view.getWayBillStateType(), view.getActivity());
        adapter.setOnItemClickListener(view.getOnItemClickListener());
        return adapter;
    }

}