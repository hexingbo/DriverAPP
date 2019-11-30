package com.lesso.module.me.mvp.contract;

import android.app.Activity;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.lesso.module.me.mvp.model.entity.CarJoinBean;
import com.lesso.module.me.mvp.model.entity.CarJoinReq;
import com.lesso.module.me.mvp.model.entity.QueryCarJoinReq;
import com.zhouyou.recyclerview.adapter.BaseRecyclerViewAdapter;

import java.util.List;

import io.reactivex.Observable;
import me.jessyan.armscomponent.commonsdk.base.bean.HttpResult;


/**
 * ================================================
 * user：贺兴波
 * 2019/11/26 10:38
 * Description:
 * ================================================
 */
public interface MyCarsListManagerContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {

        Activity getActivity();

        BaseRecyclerViewAdapter.OnItemClickListener<CarJoinBean> getOnItemClickListener();

        void onCarJoinList(List<CarJoinBean> result, int page);

        void onJoinCarSucceed();
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {

        Observable<HttpResult<List<CarJoinBean>>> getCarJoinList(QueryCarJoinReq carJoinReq);

        Observable<HttpResult> joinCar(CarJoinReq carJoinReq);
    }
}
