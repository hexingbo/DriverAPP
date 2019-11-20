package com.lesso.module.waybill.mvp.presenter;

import android.app.Application;

import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.AppManagerUtil;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.DataHelper;
import com.jess.arms.utils.RxLifecycleUtils;
import com.lesso.module.waybill.BuildConfig;
import com.lesso.module.waybill.mvp.contract.OrderAccountsChildContract;
import com.lesso.module.waybill.mvp.model.entity.OrderAccountBean;
import com.lesso.module.waybill.mvp.ui.adapter.OrderAccountListAdapter;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.armscomponent.commonres.enums.OrderAccountStateType;
import me.jessyan.armscomponent.commonsdk.base.bean.HttpResult;
import me.jessyan.armscomponent.commonsdk.base.observer.MyHttpResultObserver;
import me.jessyan.armscomponent.commonsdk.core.Constants;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;


/**
 * ================================================
 * user：贺兴波
 * 2019/11/19 14:34
 * Description:
 * ================================================
 */
@FragmentScope
public class OrderAccountsChildPresenter extends BasePresenter<OrderAccountsChildContract.Model, OrderAccountsChildContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    List<OrderAccountBean> mDatas;

    @Inject
    OrderAccountListAdapter mAdapter;


    private int currentPage = 1, pageSize = 10;
    private String condition;//搜索词（公司或者订单号）
    private String driverDateStart="2019-11-10";//开始时间
    private String driverDateEnd="2019-11-19";//结束时间

    @Inject
    public OrderAccountsChildPresenter(OrderAccountsChildContract.Model model, OrderAccountsChildContract.View rootView) {
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

    public void getOrderAccounts(OrderAccountStateType stateType, boolean pullToRefresh) {
        mModel.getOrderAccounts((pullToRefresh ? currentPage = 1 : (currentPage + 1)), pageSize, stateType,
                DataHelper.getStringSF(AppManagerUtil.getCurrentActivity(), Constants.SP_USER_ID), condition, driverDateStart, driverDateEnd)
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
                .subscribe(new MyHttpResultObserver<HttpResult<List<OrderAccountBean>>>(mErrorHandler) {
                    @Override
                    public void onResult(HttpResult<List<OrderAccountBean>> result) {
                        if (!ArmsUtils.isEmpty(result.getData())) {
                            //1.使用setListAll（覆盖数据）后就不需要再调用notifyDataSetChanged（）
                            //2.如果是addAll()追加
                            if (pullToRefresh) {
                                mAdapter.setListAll(result.getData());
                            } else {
                                mAdapter.addItemsToLast(result.getData());
                            }

                        }
                    }

                });

    }
}
