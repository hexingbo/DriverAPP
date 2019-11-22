package com.lesso.module.waybill.mvp.presenter;

import android.Manifest;
import android.app.Application;
import android.support.annotation.NonNull;

import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.DataHelper;
import com.jess.arms.utils.PermissionUtil;
import com.jess.arms.utils.RxLifecycleUtils;
import com.lesso.module.waybill.BuildConfig;
import com.lesso.module.waybill.R;
import com.lesso.module.waybill.mvp.contract.WayBillManagerChildContract;
import com.lesso.module.waybill.mvp.model.entity.WayBillListBean;
import com.lesso.module.waybill.mvp.ui.adapter.WayBillListAdapter;
import com.zhouyou.recyclerview.adapter.HelperStateRecyclerViewAdapter;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.armscomponent.commonres.enums.WayBillStateType;
import me.jessyan.armscomponent.commonsdk.base.bean.HttpResult;
import me.jessyan.armscomponent.commonsdk.core.Constants;
import me.jessyan.armscomponent.commonsdk.core.RouterHub;
import me.jessyan.armscomponent.commonsdk.http.observer.MyHttpResultObserver;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;


/**
 * ================================================
 * user：贺兴波
 * 2019/11/15 17:52
 * Description:
 * ================================================
 */
@FragmentScope
public class WayBillManagerChildPresenter extends BasePresenter<WayBillManagerChildContract.Model, WayBillManagerChildContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;
    @Inject
    List<WayBillListBean> mDatas;
    @Inject
    WayBillListAdapter mAdapter;

    private int currentPage = 1, pageSize = 10;
    private WayBillStateType stateType;

    public void setStateType(WayBillStateType stateType) {
        this.stateType = stateType;
        getWayBillList(true);
    }

    @Inject
    public WayBillManagerChildPresenter(WayBillManagerChildContract.Model model, WayBillManagerChildContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mAdapter.onRelease();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }

    public void getWayBillList(boolean pullToRefresh) {
        mModel.getWayBillList((pullToRefresh ? currentPage = 1 : (currentPage + 1)), pageSize,
                DataHelper.getStringSF(mRootView.getActivity(), Constants.SP_USER_ID), stateType)
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
                .subscribe(new MyHttpResultObserver<HttpResult<List<WayBillListBean>>>(mErrorHandler) {
                    @Override
                    public void onResult(HttpResult<List<WayBillListBean>> result) {
                        if (!ArmsUtils.isEmpty(result.getData())) {
                            //1.使用setListAll（覆盖数据）后就不需要再调用notifyDataSetChanged（）
                            //2.如果是addAll()追加
                            if (pullToRefresh) {
                                mAdapter.setListAll(result.getData());//不需要设置任何东西setListAll有数据了会自动到内容页面
                            } else {
                                mAdapter.addItemsToLast(result.getData());
                            }

                        }
                    }

                });
    }

    /**
     * 权限检测
     */
    public void checkPermission() {
        if (mRootView.getRequestPermission() != null&& mRootView.getRxPermissions()!=null)
            //请求外部存储权限用于适配android6.0的权限管理机制
            PermissionUtil.requestPermission(mRootView.getRequestPermission(), mRootView.getRxPermissions(), mErrorHandler,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION);
    }

    /**
     * 运输打卡
     *
     * @param orderId        订单id
     * @param orderNo        订单号
     * @param carNo          车牌号
     * @param currentUserId  用户id
     * @param latitude       定位经度
     * @param longitude      定位纬度
     * @param checkInAddress 定位地址
     * @return
     */
    public void postDriverTransportPunch(@NonNull String orderId, @NonNull String orderNo,
                                         @NonNull String carNo, @NonNull String currentUserId,
                                         @NonNull String latitude, @NonNull String longitude, @NonNull String checkInAddress) {
        mModel.postDriverTransportPunch(orderId, orderNo, carNo, currentUserId, latitude, longitude, checkInAddress)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(
                        //遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                        BuildConfig.HTTP_MaxRetries, BuildConfig.HTTP_RetryDelaySecond))
                .doOnSubscribe(disposable -> {
                    mRootView.showLoading();//显示下拉刷新的进度条
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.hideLoading();//隐藏下拉刷新的进度条
                })
                //使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new MyHttpResultObserver<HttpResult>(mErrorHandler) {
                    @Override
                    public void onResult(HttpResult result) {
                        mRootView.showMessage(mApplication.getString(R.string.module_waybill_name_daka_success));
                    }

                });
    }

    /**
     * 订单收货
     *
     * @param orderId         订单id
     * @param orderNo         订单号
     * @param carNo           车牌号
     * @param currentUserId   用户id
     * @param deliveryAddress 定位地址
     * @return
     */
    public void postWayBillReceipt(@NonNull String orderId, @NonNull String orderNo,
                                   @NonNull String carNo, @NonNull String currentUserId,
                                   @NonNull String deliveryAddress) {
        mModel.postWayBillReceipt(orderId, orderNo, carNo, currentUserId, deliveryAddress)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(
                        //遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                        BuildConfig.HTTP_MaxRetries, BuildConfig.HTTP_RetryDelaySecond))
                .doOnSubscribe(disposable -> {
                    mRootView.showLoading();//显示下拉刷新的进度条
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.hideLoading();//隐藏下拉刷新的进度条
                })
                //使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new MyHttpResultObserver<HttpResult>(mErrorHandler) {
                    @Override
                    public void onResult(HttpResult result) {
                        mRootView.showMessage(mApplication.getString(R.string.module_waybill_name_shouhuo_success));
                        getWayBillList(true);
                    }

                });
    }

    /**
     * 订单发货
     *
     * @param orderId       订单id
     * @param orderNo       订单号
     * @param carNo         车牌号
     * @param currentUserId 用户id
     * @param address       定位地址
     * @return
     */
    public void postSendWayBill(@NonNull String currentUserId, @NonNull String orderId, @NonNull String orderNo,
                                @NonNull String carNo, @NonNull String address) {
        mModel.postSendWayBill(currentUserId, orderId, orderNo, carNo, address)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(
                        //遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                        BuildConfig.HTTP_MaxRetries, BuildConfig.HTTP_RetryDelaySecond))
                .doOnSubscribe(disposable -> {
                    mRootView.showLoading();//显示下拉刷新的进度条
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.hideLoading();//隐藏下拉刷新的进度条
                })
                //使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new MyHttpResultObserver<HttpResult>(mErrorHandler) {
                    @Override
                    public void onResult(HttpResult result) {
                        mRootView.showMessage(mApplication.getString(R.string.module_waybill_name_send_success));
                        getWayBillList(true);
                    }

                });
    }
}
