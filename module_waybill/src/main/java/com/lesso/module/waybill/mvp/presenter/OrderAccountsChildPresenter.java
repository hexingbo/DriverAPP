package com.lesso.module.waybill.mvp.presenter;

import android.app.Application;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.jess.arms.base.MessageEvent;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.integration.EventBusManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.AppManagerUtil;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.DataHelper;
import com.jess.arms.utils.RxLifecycleUtils;
import com.lesso.module.waybill.BuildConfig;
import com.lesso.module.waybill.mvp.contract.OrderAccountsChildContract;
import com.lesso.module.waybill.mvp.model.entity.OrderAccountBean;
import com.lesso.module.waybill.mvp.ui.adapter.OrderAccountListAdapter;
import com.zhouyou.recyclerview.adapter.HelperStateRecyclerViewAdapter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.armscomponent.commonres.enums.OrderAccountStateType;
import me.jessyan.armscomponent.commonsdk.base.bean.HttpResult;
import me.jessyan.armscomponent.commonsdk.core.Constants;
import me.jessyan.armscomponent.commonsdk.core.EventBusHub;
import me.jessyan.armscomponent.commonsdk.core.RouterHub;
import me.jessyan.armscomponent.commonsdk.http.observer.MyHttpResultObserver;
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

    private Date startDate = new Date();
    private Date endDate = new Date();
    private Calendar startDateCalendar = Calendar.getInstance();
    private Calendar endDateCalendar = Calendar.getInstance();

    private OrderAccountStateType stateType;
    private int currentPage = 1, pageSize = 10;
    private String condition = "";//搜索词（公司或者订单号）
    private String driverDateStart = "2019-11-10";//开始时间
    private String driverDateEnd = "2019-11-19";//结束时间

    public String getDriverDateEnd() {
        return driverDateEnd;
    }

    public String getDriverDateStart() {
        return driverDateStart;
    }

    public void setSearchValue(String value) {
        condition = ArmsUtils.isEmpty(value) ? "" : value;
    }

    @Inject
    public OrderAccountsChildPresenter(OrderAccountsChildContract.Model model, OrderAccountsChildContract.View rootView) {
        super(model, rootView);
//        startDate.setDate(startDate.getDay() - 15);
        startDateCalendar.setTime(startDate);
        startDateCalendar.add(Calendar.DATE, -15);
        startDate = startDateCalendar.getTime();
        endDateCalendar.setTime(endDate);
        driverDateStart = getTime(startDate);
        driverDateEnd = getTime(endDate);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }


    /**
     * 获取可以生成对账单的订单数据
     *
     * @param stateType
     * @param pullToRefresh
     */
    public void getOrderAccounts(OrderAccountStateType stateType, boolean pullToRefresh) {
        this.stateType = stateType;
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
                                mAdapter.setListAll(result.getData());//不需要设置任何东西setListAll有数据了会自动到内容页面
                            } else {
                                mAdapter.addItemsToLast(result.getData());
                            }

                        } else {
                            if (pullToRefresh) {
                                mAdapter.setState(HelperStateRecyclerViewAdapter.STATE_EMPTY);
                            }

                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        if (pullToRefresh) {
                            mAdapter.setState(HelperStateRecyclerViewAdapter.STATE_EMPTY);
                        }
                    }

                });

    }

    /**
     * 生成对账单
     *
     * @param orderId
     */
    public void postOrderSaveAccounts(String orderId) {
        if (ArmsUtils.isEmpty(orderId)) {
            mRootView.showMessage("请选择需要生成对账的订单");
            return;
        }
        mModel.postOrderSaveAccounts(orderId.substring(0, orderId.lastIndexOf(",")), DataHelper.getStringSF(AppManagerUtil.getCurrentActivity(), Constants.SP_USER_ID), driverDateStart, driverDateEnd)
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
                        EventBusManager.getInstance().post(new MessageEvent(EventBusHub.Message_UpdateOrderAccountsList));
                    }

                });

    }

    /**
     * 时间选择器弹窗
     *
     * @param context
     */
    public void showTimePickerView(Context context, final boolean isSelectStartTime) {

        if (isSelectStartTime) {
            startDate.setYear(startDate.getYear() - 20);
            startDateCalendar.setTime(startDate);
            endDateCalendar.setTime(endDate);
        } else {
            startDateCalendar.setTime(startDate);
            endDateCalendar.setTime(new Date());
        }


        TimePickerView pvTime = new TimePickerView.Builder(context, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date2, View v) {//选中事件回调
                if (isSelectStartTime) {
                    startDate = date2;
                    driverDateStart = getTime(startDate);
                    mRootView.setStartTimeValue(driverDateStart);
                } else {
                    endDate = date2;
                    driverDateEnd = getTime(endDate);
                    mRootView.setEndTimeValue(driverDateEnd);
                }

            }
        })
                .setType(TimePickerView.Type.YEAR_MONTH_DAY)//默认全部显示
                .setCancelText("取消")//取消按钮文字
                .setSubmitText("确定")//确认按钮文字
                .setContentSize(20)//滚轮文字大小
                .setTitleSize(20)//标题文字大小
//                        .setTitleText("请选择时间")//标题文字
                .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                .isCyclic(false)//是否循环滚动
                .setTextColorCenter(Color.BLACK)//设置选中项的颜色
                .setTitleColor(Color.BLACK)//标题文字颜色
                .setSubmitColor(Color.BLUE)//确定按钮文字颜色
                .setCancelColor(Color.BLUE)//取消按钮文字颜色
//                        .setTitleBgColor(0xFF666666)//标题背景颜色 Night mode
//                        .setBgColor(0xFF333333)//滚轮背景颜色 Night mode
//                        .setRange(calendar.get(Calendar.YEAR) - 20, calendar.get(Calendar.YEAR))//默认是1900-2100年
//                .setDate(calendar)// 如果不设置的话，默认是系统时间*/
                .setRangDate(startDateCalendar, endDateCalendar)//起始终止年月日设定
//                        .setLabel("年","月","日","时","分","秒")
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
//                        .isDialog(true)//是否显示为对话框样式
                .build();
        pvTime.setDate(endDateCalendar);//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
        pvTime.show();
    }

    public String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

}
