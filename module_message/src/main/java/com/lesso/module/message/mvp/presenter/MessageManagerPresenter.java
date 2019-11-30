package com.lesso.module.message.mvp.presenter;

import android.app.Application;

import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.DataHelper;
import com.jess.arms.utils.RxLifecycleUtils;
import com.lesso.module.message.BuildConfig;
import com.lesso.module.message.mvp.contract.MessageManagerContract;
import com.lesso.module.message.mvp.model.entity.MessageBean;
import com.lesso.module.message.mvp.ui.adapter.MessageListAdapter;
import com.zhouyou.recyclerview.adapter.HelperStateRecyclerViewAdapter;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.armscomponent.commonsdk.base.bean.HttpResult;
import me.jessyan.armscomponent.commonsdk.core.Constants;
import me.jessyan.armscomponent.commonsdk.http.observer.MyHttpResultObserver;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;


@FragmentScope
public class MessageManagerPresenter extends BasePresenter<MessageManagerContract.Model, MessageManagerContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    List<MessageBean> mData;
    @Inject
    MessageListAdapter mAdapter;

    private int current = 1, size = 10;

    @Inject
    public MessageManagerPresenter(MessageManagerContract.Model model, MessageManagerContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }

    public void getMessageList(boolean pullToRefresh) {
        mModel.getMessageList(pullToRefresh ? current = 1 : current + 1, size,
                DataHelper.getStringSF(mRootView.getActivity(), Constants.SP_USER_ID))
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(
                        //遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                        BuildConfig.HTTP_MaxRetries, BuildConfig.HTTP_RetryDelaySecond))
                .doOnSubscribe(disposable -> {
//                    if (pullToRefresh)
//                        mRootView.showLoading();//显示下拉刷新的进度条
//                    else
//                        mRootView.startLoadMore();//显示上拉加载更多的进度条
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    if (pullToRefresh)
                        mRootView.hideLoading();//隐藏下拉刷新的进度条
                    else
                        mRootView.endLoadMore();//隐藏上拉加载更多的进度条
                })
                //使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new MyHttpResultObserver<HttpResult<List<MessageBean>>>(mErrorHandler) {
                    @Override
                    public void onResult(HttpResult<List<MessageBean>> result) {

                        if (!ArmsUtils.isEmpty(result.getData())) {
                            current += 1;
                            //1.使用setListAll（覆盖数据）后就不需要再调用notifyDataSetChanged（）
                            //2.如果是addAll()追加
                            if (pullToRefresh) {
                                mAdapter.setListAll(result.getData());
                            } else {
                                mAdapter.addItemsToLast(result.getData());
                            }
                        } else {
                            if (pullToRefresh){
                                mAdapter.setState(HelperStateRecyclerViewAdapter.STATE_EMPTY);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        if (pullToRefresh){
                            mAdapter.setState(HelperStateRecyclerViewAdapter.STATE_ERROR);
                        }

                    }
                });
    }
}
