package com.lesso.module.message.mvp.contract;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.lesso.module.message.mvp.model.entity.MessageBean;
import com.zhouyou.recyclerview.adapter.BaseRecyclerViewAdapter;

import java.util.List;

import io.reactivex.Observable;
import me.jessyan.armscomponent.commonres.base.ILoadlayoutView;
import me.jessyan.armscomponent.commonsdk.base.bean.HttpResult;


public interface MessageManagerContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView, ILoadlayoutView {

        Activity getActivity();

        void endLoadMore();

        BaseRecyclerViewAdapter.OnItemClickListener<MessageBean> getOnItemClickListener();
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<HttpResult<List<MessageBean>>> getMessageList(int current, int size, @NonNull String dirverId);
    }
}
