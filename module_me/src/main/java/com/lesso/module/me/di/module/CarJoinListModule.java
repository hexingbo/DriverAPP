package com.lesso.module.me.di.module;

import android.app.Dialog;
import android.support.v7.widget.RecyclerView;

import com.jess.arms.di.scope.ActivityScope;
import com.lesso.module.me.mvp.contract.CarJoinListContract;
import com.lesso.module.me.mvp.model.CarJoinListModel;
import com.lesso.module.me.mvp.ui.adapter.CarJoinDetailAdapter;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import me.jessyan.armscomponent.commonres.dialog.MyRoundRectangleHintDialog;
import me.jessyan.armscomponent.commonres.dialog.ProgresDialog;
import me.jessyan.armscomponent.commonsdk.utils.LayoutManagerUtil;


/**
 * ================================================
 * user：贺兴波
 * 2019/11/26 11:37
 * Description:
 * ================================================
 */
@Module
public abstract class CarJoinListModule {

    @Binds
    abstract CarJoinListContract.Model bindCarJoinListModel(CarJoinListModel model);

    @ActivityScope
    @Provides
    static Dialog provideDialog(CarJoinListContract.View view) {
        return new ProgresDialog(view.getActivity());
    }

    @ActivityScope
    @Provides
    static RecyclerView.LayoutManager provideLayoutManager(CarJoinListContract.View view) {
        return LayoutManagerUtil.getLinearLayoutManager_VERTICAL(view.getActivity());
    }

    @ActivityScope
    @Provides
    static CarJoinDetailAdapter provideCarJoinDetailAdapter(CarJoinListContract.View view) {
        CarJoinDetailAdapter adapter = new CarJoinDetailAdapter(view.getActivity());
        adapter.setOnItemClickListener(view.getOnItemClickListener());
        return adapter;
    }

    @ActivityScope
    @Provides
    static MyRoundRectangleHintDialog provideMyRoundRectangleHintDialog(CarJoinListContract.View view) {
        return new MyRoundRectangleHintDialog(view.getActivity());
    }
}