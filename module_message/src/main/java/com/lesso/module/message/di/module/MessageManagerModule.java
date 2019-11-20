package com.lesso.module.message.di.module;

import android.app.Dialog;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.utils.AppManagerUtil;
import com.lesso.module.message.mvp.contract.MessageManagerContract;
import com.lesso.module.message.mvp.model.MessageManagerModel;
import com.lesso.module.message.mvp.model.entity.MessageBean;
import com.lesso.module.message.mvp.ui.activity.MessageDetailActivity;
import com.lesso.module.message.mvp.ui.adapter.MessageListAdapter;
import com.zhouyou.recyclerview.adapter.BaseRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import me.jessyan.armscomponent.commonres.dialog.ProgresDialog;
import me.jessyan.armscomponent.commonsdk.utils.LayoutManagerUtil;


@Module
public abstract class MessageManagerModule {

    @Binds
    abstract MessageManagerContract.Model bindMessageManagerModel(MessageManagerModel model);

    @FragmentScope
    @Provides
    static Dialog provideDialog(MessageManagerContract.View view) {
        return new ProgresDialog(view.getActivity());
    }

    @FragmentScope
    @Provides
    static RecyclerView.LayoutManager provideLayoutManager(MessageManagerContract.View view) {
        return LayoutManagerUtil.getLinearLayoutManager_VERTICAL(view.getActivity());
    }

    @FragmentScope
    @Provides
    static List<MessageBean> provideList() {
        return new ArrayList<>();
    }

    @FragmentScope
    @Provides
    static MessageListAdapter provideMessageListAdapter(List<MessageBean> list, MessageManagerContract.View view) {
        MessageListAdapter adapter = new MessageListAdapter(list, view.getActivity());
        adapter.setOnItemClickListener(view.getOnItemClickListener() );
        return adapter;
    }


}