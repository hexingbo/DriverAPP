package com.lesso.module.me.di.module;

import android.app.Dialog;
import android.support.v7.widget.RecyclerView;

import com.jess.arms.di.scope.ActivityScope;
import com.lesso.module.me.mvp.contract.MyCarsListManagerContract;
import com.lesso.module.me.mvp.model.MyCarsListManagerModel;
import com.lesso.module.me.mvp.model.entity.CarJoinBean;

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
 * 2019/11/26 10:38
 * Description:
 * ================================================
 */
@Module
public abstract class MyCarsListManagerModule {

    @Binds
    abstract MyCarsListManagerContract.Model bindMyCarsListManagerModel(MyCarsListManagerModel model);

    @ActivityScope
    @Provides
    static Dialog provideDialog(MyCarsListManagerContract.View view) {
        return new ProgresDialog(view.getActivity());
    }

    @ActivityScope
    @Provides
    static RecyclerView.LayoutManager provideLayoutManager(MyCarsListManagerContract.View view) {
        return LayoutManagerUtil.getLinearLayoutManager_VERTICAL(view.getActivity());
    }

//    @ActivityScope
//    @Provides
//    static CarJoinAdapter provideCarJoinAdapter(List<CarJoinBean> list,
//                                                MyCarsListManagerContract.View view) {
//        CarJoinAdapter adapter = new CarJoinAdapter(list, view.getActivity());
//        adapter.setOnItemClickListener(view.getOnItemClickListener());
//        return adapter;
//    }

    @ActivityScope
    @Provides
    static List<CarJoinBean> provideList() {
        return new ArrayList<>();
    }
}