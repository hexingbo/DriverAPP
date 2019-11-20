package com.lesso.module.me.mvp.presenter;

import android.app.Application;

import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.armscomponent.commonsdk.base.bean.HttpResult;
import me.jessyan.armscomponent.commonsdk.base.observer.MyHttpResultObserver;
import me.jessyan.armscomponent.commonsdk.core.Constants;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;

import javax.inject.Inject;

import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.DataHelper;
import com.jess.arms.utils.RxLifecycleUtils;
import com.lesso.module.me.BuildConfig;
import com.lesso.module.me.mvp.contract.CompanyJoinManageContract;
import com.lesso.module.me.mvp.model.entity.CompanyJoinBean;
import com.lesso.module.me.mvp.model.entity.UserInfoBean;
import com.lesso.module.me.mvp.ui.adapter.ChoseCompanyJoinListAdapter;

import java.util.List;


/**
 * ================================================
 * user：贺兴波
 * 2019/11/18 10:28
 * Description:
 * ================================================
 */
@ActivityScope
public class CompanyJoinManagePresenter extends BasePresenter<CompanyJoinManageContract.Model, CompanyJoinManageContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    List<CompanyJoinBean> mDatas;

    @Inject
    ChoseCompanyJoinListAdapter mAdapter;

    private int currentPage = 1, pageSize = 10;
    private String companyName;

    @Inject
    public CompanyJoinManagePresenter(CompanyJoinManageContract.Model model, CompanyJoinManageContract.View rootView) {
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
     * 获取可选择的结盟公司
     */
    public void getChoseCompanyJoinList(boolean pullToRefresh) {
        if (!ArmsUtils.isEmpty(companyName)) {
            mModel.getSeachCompanyJoinList(companyName)
                    .subscribeOn(Schedulers.io())
                    .retryWhen(new RetryWithDelay(
                            //遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                            BuildConfig.HTTP_MaxRetries, BuildConfig.HTTP_RetryDelaySecond))
                    .doOnSubscribe(disposable -> {
                        if (pullToRefresh)
                            mRootView.showLoading();//显示下拉刷新的进度条
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
                    .subscribe(new MyHttpResultObserver<HttpResult<List<CompanyJoinBean>>>(mErrorHandler) {
                        @Override
                        public void onResult(HttpResult<List<CompanyJoinBean>> result) {
                            if (!ArmsUtils.isEmpty(result.getData())) {
                                //1.使用setListAll（覆盖数据）后就不需要再调用notifyDataSetChanged（）
                                //2.如果是addAll()追加
                                updateListDatas(result.getData(), pullToRefresh);
                            }
                        }

                    });
        } else {
            mModel.getRecommendCompanyJoinList(pageSize)
                    .subscribeOn(Schedulers.io())
                    .retryWhen(new RetryWithDelay(
                            //遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                            BuildConfig.HTTP_MaxRetries, BuildConfig.HTTP_RetryDelaySecond))
                    .doOnSubscribe(disposable -> {
                        if (pullToRefresh)
                            mRootView.showLoading();//显示下拉刷新的进度条
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
                    .subscribe(new MyHttpResultObserver<HttpResult<List<CompanyJoinBean>>>(mErrorHandler) {
                        @Override
                        public void onResult(HttpResult<List<CompanyJoinBean>> result) {
                            updateListDatas(result.getData(), pullToRefresh);
                        }

                    });
        }
    }

    private void updateListDatas(List<CompanyJoinBean> list, boolean pullToRefresh) {
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

    public void setSearchKeywords(String searchKeywords) {
        companyName = searchKeywords;
    }
}
