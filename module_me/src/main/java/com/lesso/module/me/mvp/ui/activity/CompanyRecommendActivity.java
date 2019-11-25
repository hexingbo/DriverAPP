package com.lesso.module.me.mvp.ui.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.integration.EventBusManager;
import com.jess.arms.utils.ArmsUtils;
import com.lesso.module.me.R;
import com.lesso.module.me.R2;
import com.lesso.module.me.di.component.DaggerCompanyRecommendComponent;
import com.lesso.module.me.mvp.contract.CompanyRecommendContract;
import com.lesso.module.me.mvp.presenter.CompanyRecommendPresenter;
import com.lesso.module.me.mvp.ui.adapter.ChoseCompanyJoinListAdapter;
import com.lesso.module.me.mvp.ui.adapter.ChoseCompanyRecommendListAdapter;
import com.zhouyou.recyclerview.XRecyclerView;
import com.zhouyou.recyclerview.adapter.HelperStateRecyclerViewAdapter;
import com.zhouyou.recyclerview.custom.CustomMoreFooter;
import com.zhouyou.recyclerview.custom.CustomRefreshHeader2;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import me.jessyan.armscomponent.commonsdk.core.EventBusHub;
import me.jessyan.armscomponent.commonsdk.core.RouterHub;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * user：贺兴波
 * 2019/11/22 09:41
 * Description: 首次注册成功后推荐的加盟物流公司选择
 * ================================================
 */
@Route(path = RouterHub.Me_CompanyRecommendActivity)
public class CompanyRecommendActivity extends BaseActivity<CompanyRecommendPresenter> implements CompanyRecommendContract.View {

    @BindView(R2.id.public_toolbar_text_rigth)
    TextView mPpublicToolbarTextRigth;
    @BindView(R2.id.recyclerview)
    XRecyclerView mRecyclerView;
    @BindView(R2.id.btn_submit)
    TextView mBtnSubmit;

    @Inject
    RecyclerView.LayoutManager mLayoutManager;
    @Inject
    ChoseCompanyRecommendListAdapter mAdapter;
    @Inject
    Dialog mDialog;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerCompanyRecommendComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_company_recommend; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle(R.string.module_me_compa_recommend);
        mBtnSubmit.setText(R.string.module_me_apply_join);
        mPpublicToolbarTextRigth.setText(getString(R.string.module_me_skip_view));

        initRecyclerView();
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setState(HelperStateRecyclerViewAdapter.STATE_LOADING);
    }

    private void initRecyclerView() {
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setRefreshHeader(new CustomRefreshHeader2(getActivity()));
        mRecyclerView.setLoadingMoreFooter(new CustomMoreFooter(getActivity()));
        mRecyclerView.setLoadingMoreEnabled(false);
        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                mPresenter.getChoseCompanyJoinList(true);
            }

            @Override
            public void onLoadMore() {
            }
        });

    }

    @Override
    public void showLoading() {
        if (!mRecyclerView.isRefresh()) {
            mDialog.show();
        }

    }

    @Override
    public void hideLoading() {
        mDialog.dismiss();
        if (mRecyclerView.isRefresh()) {
            mRecyclerView.refreshComplete();
        }
    }


    @Override
    public void endLoadMore() {
        if (mRecyclerView.isLoadingMore())
            mRecyclerView.loadMoreComplete();
    }


    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }


    @OnClick({R2.id.public_toolbar_text_rigth, R2.id.btn_submit})
    public void onViewClicked(View view) {
        if (view.getId() == R.id.public_toolbar_text_rigth) {
            //跳过，去到首页mainActivity
            EventBusManager.getInstance().post(EventBusHub.TAG_LOGIN_SUCCESS);
            finish();
        } else if (view.getId() == R.id.btn_submit) {
            //批量加盟物流公司
            mPresenter.postCompanyJoin();
        }
    }

    @Override
    public Activity getActivity() {
        return this;
    }
}
