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
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.DeviceUtils;
import com.lesso.module.me.R;
import com.lesso.module.me.R2;
import com.lesso.module.me.di.component.DaggerCompanyJoinManageComponent;
import com.lesso.module.me.mvp.contract.CompanyJoinManageContract;
import com.lesso.module.me.mvp.presenter.CompanyJoinManagePresenter;
import com.lesso.module.me.mvp.ui.adapter.ChoseCompanyJoinListAdapter;
import com.zhouyou.recyclerview.XRecyclerView;
import com.zhouyou.recyclerview.custom.CustomMoreFooter;
import com.zhouyou.recyclerview.custom.CustomRefreshHeader2;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * user：贺兴波
 * 2019/11/18 10:28
 * Description:加盟物流公司选择列表
 * ================================================
 */
public class CompanyJoinManageActivity extends BaseActivity<CompanyJoinManagePresenter> implements CompanyJoinManageContract.View, TextView.OnEditorActionListener {

    @Inject
    RecyclerView.LayoutManager mLayoutManager;
    @Inject
    ChoseCompanyJoinListAdapter mAdapter;
    @Inject
    Dialog mDialog;

    @BindView(R2.id.recyclerview)
    XRecyclerView mRecyclerView;
    @BindView(R2.id.et_search)
    EditText etSearch;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerCompanyJoinManageComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_company_join_manage; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle(R.string.me_name_company_join);
        initRecyclerView();
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.refresh();
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

        //添加搜索按钮监听
        etSearch.setOnEditorActionListener(this);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mPresenter.setSearchKeywords(etSearch.getText().toString().trim());
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

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void endLoadMore() {
        if (mRecyclerView.isLoadingMore())
            mRecyclerView.loadMoreComplete();
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        /*判断是否是“搜索”键*/
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            // 隐藏键盘
            DeviceUtils.hideSoftKeyboard(this, v);
            //让EditText失去焦点
            etSearch.setFocusable(false);
            etSearch.setFocusableInTouchMode(true);
            mPresenter.getChoseCompanyJoinList(true);
            return true;
        }
        return false;
    }


    @OnClick(R2.id.img_search)
    public void onSearchViewClicked() {
        if (ArmsUtils.isEmpty(etSearch.getText().toString().trim())) {
            showMessage(getResources().getString(R.string.module_me_hint_search_company_name));
            return;
        }
        mPresenter.getChoseCompanyJoinList(true);
    }

    @OnClick(R2.id.img_code_two)
    public void onCodeTwoViewClicked() {
        // TODO: 2019/11/18 二维码扫描
        showMessage(getResources().getString(R.string.module_me_develop_not));
    }
}
