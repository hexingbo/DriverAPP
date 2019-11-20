package com.lesso.module.me.mvp.presenter;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.AppManagerUtil;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.DataHelper;
import com.jess.arms.utils.RxLifecycleUtils;
import com.lesso.module.me.BuildConfig;
import com.lesso.module.me.mvp.contract.CompanyJoinedManageContract;
import com.lesso.module.me.mvp.model.entity.CompanyJoinedBean;
import com.lesso.module.me.mvp.ui.adapter.CompanyJoinedAdapter;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.armscomponent.commonres.enums.CompanyActionType;
import me.jessyan.armscomponent.commonsdk.base.bean.HttpResult;
import me.jessyan.armscomponent.commonsdk.base.observer.MyHttpResultObserver;
import me.jessyan.armscomponent.commonsdk.core.Constants;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;


/**
 * ================================================
 * user：贺兴波
 * 2019/11/18 10:14
 * Description:
 * ================================================
 */
@ActivityScope
public class CompanyJoinedManagePresenter extends BasePresenter<CompanyJoinedManageContract.Model, CompanyJoinedManageContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    List<CompanyJoinedBean> mDatas;

    @Inject
    CompanyJoinedAdapter mAdapter;

    private int currentPage = 1, pageSize = 10;

    @Inject
    public CompanyJoinedManagePresenter(CompanyJoinedManageContract.Model model, CompanyJoinedManageContract.View rootView) {
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

    /**
     * 加盟公司管理列表
     *
     * @param pullToRefresh
     */
    public void postCompanyJoinedList(boolean pullToRefresh) {
        mModel.postCompanyJoinedList(pullToRefresh ? currentPage = 1 : currentPage + 1, pageSize,
                DataHelper.getStringSF(AppManagerUtil.appContext(), Constants.SP_USER_ID))
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(
                        //遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                        BuildConfig.HTTP_MaxRetries, BuildConfig.HTTP_RetryDelaySecond))
                .doOnSubscribe(disposable -> {
                    if (pullToRefresh) {
                        mDatas.clear();
                        mAdapter.notifyDataSetChanged();
                        mRootView.showLoading();//显示下拉刷新的进度条
                    }
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
                .subscribe(new MyHttpResultObserver<HttpResult<List<CompanyJoinedBean>>>(mErrorHandler) {
                    @Override
                    public void onResult(HttpResult<List<CompanyJoinedBean>> result) {
                        if (!ArmsUtils.isEmpty(result.getData())) {
                            //1.使用setListAll（覆盖数据）后就不需要再调用notifyDataSetChanged（）
                            //2.如果是addAll()追加
                            updateListDatas(result.getData(), pullToRefresh);
                        }
                    }

                });
    }

    /**
     * 加盟公司管理列表操作对象 同意、拒绝、取消、退出等
     *
     * @param companyId
     * @param driverId
     * @param joinId
     * @param operatorType
     */
    public void postCompanyJoinedAction(String companyId, String driverId, String joinId, CompanyActionType operatorType) {
        mModel.postCompanyJoinedAction(companyId, driverId, joinId, operatorType)
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
                        postCompanyJoinedList(true);
                    }

                });
    }

    private void updateListDatas(List<CompanyJoinedBean> list, boolean pullToRefresh) {
        if (!ArmsUtils.isEmpty(list)) {
            //1.使用setListAll（覆盖数据）后就不需要再调用notifyDataSetChanged（）
            //2.如果是addAll()追加
            if (pullToRefresh) {
                mDatas.clear();
                mDatas.addAll(list);
                mAdapter.setListAll(list);
            } else {
                currentPage += 1;
                mAdapter.addItemsToLast(list);
            }
        }
    }
}
