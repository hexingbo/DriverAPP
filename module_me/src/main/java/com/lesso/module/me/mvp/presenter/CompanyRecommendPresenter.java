package com.lesso.module.me.mvp.presenter;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.integration.EventBusManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.AppManagerUtil;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.DataHelper;
import com.jess.arms.utils.RxLifecycleUtils;
import com.lesso.module.me.BuildConfig;
import com.lesso.module.me.R;
import com.lesso.module.me.mvp.contract.CompanyRecommendContract;
import com.lesso.module.me.mvp.model.entity.CompanyJoinBean;
import com.lesso.module.me.mvp.ui.adapter.ChoseCompanyRecommendListAdapter;
import com.zhouyou.recyclerview.adapter.HelperStateRecyclerViewAdapter;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.armscomponent.commonsdk.base.bean.HttpResult;
import me.jessyan.armscomponent.commonsdk.core.Constants;
import me.jessyan.armscomponent.commonsdk.core.EventBusHub;
import me.jessyan.armscomponent.commonsdk.http.observer.MyHttpResultObserver;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;


/**
 * ================================================
 * user：贺兴波
 * 2019/11/22 09:41
 * Description:
 * ================================================
 */
@ActivityScope
public class CompanyRecommendPresenter extends BasePresenter<CompanyRecommendContract.Model, CompanyRecommendContract.View> {
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
    ChoseCompanyRecommendListAdapter mAdapter;

    private int currentPage = 1, pageSize = 10;

    @Inject
    public CompanyRecommendPresenter(CompanyRecommendContract.Model model, CompanyRecommendContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
        mAdapter.onRelease();
    }

    /**
     * 获取可选择的结盟公司
     */
    public void getChoseCompanyJoinList(boolean pullToRefresh) {
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

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mAdapter.setState(HelperStateRecyclerViewAdapter.STATE_ERROR);
                    }
                });
    }

    /**
     * 更新数据
     *
     * @param list
     * @param pullToRefresh
     */
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
        } else {
            mAdapter.setState(HelperStateRecyclerViewAdapter.STATE_EMPTY);
        }
    }

    /**
     * 提交加盟公司id  多个用“，”隔开
     */
    public void postCompanyJoin() {
        String companyId = "";
        if (ArmsUtils.isEmpty(mAdapter.getList())) {
            mRootView.showMessage(mApplication.getString(R.string.module_me_name_hint_selected_company));
            return;
        }
        for (CompanyJoinBean bean : mAdapter.getList()) {
            companyId = companyId + (bean.isSelected() ? bean.getCompanyId() + "," : "");
        }

        if (ArmsUtils.isEmpty(companyId)) {
            mRootView.showMessage(mApplication.getString(R.string.module_me_name_hint_selected_company));
            return;
        }
        mModel.postCompanyJoin(companyId.substring(0, companyId.lastIndexOf(",")), DataHelper.getStringSF(AppManagerUtil.appContext(), Constants.SP_USER_ID), "1")//加盟类型 0：物流公司邀请；1：司机主动加入
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
                        EventBusManager.getInstance().post(EventBusHub.TAG_LOGIN_SUCCESS);
                        AppManagerUtil.getCurrentActivity().finish();
                    }

                });
    }

}
