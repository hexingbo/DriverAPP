package com.lesso.module.me.mvp.ui.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.base.MessageEvent;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.AppManagerUtil;
import com.jess.arms.utils.ArmsUtils;
import com.lesso.module.me.R;
import com.lesso.module.me.R2;
import com.lesso.module.me.di.component.DaggerMyCarsListManagerComponent;
import com.lesso.module.me.mvp.contract.MyCarsListManagerContract;
import com.lesso.module.me.mvp.model.entity.CarJoinBean;
import com.lesso.module.me.mvp.presenter.MyCarsListManagerPresenter;
import com.lesso.module.me.mvp.ui.adapter.CarJoinAdapter;
import com.zhouyou.recyclerview.XRecyclerView;
import com.zhouyou.recyclerview.adapter.BaseRecyclerViewAdapter;
import com.zhouyou.recyclerview.adapter.HelperStateRecyclerViewAdapter;
import com.zhouyou.recyclerview.custom.CustomMoreFooter;
import com.zhouyou.recyclerview.custom.CustomRefreshHeader2;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import me.jessyan.armscomponent.commonsdk.core.EventBusHub;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * user：贺兴波
 * 2019/11/26 10:38
 * Description:我的车辆管理
 * ================================================
 */
public class MyCarsListManagerActivity extends BaseActivity<MyCarsListManagerPresenter>
        implements MyCarsListManagerContract.View,
        BaseRecyclerViewAdapter.OnItemClickListener<CarJoinBean> {

    @Inject
    Dialog mDialog;
    @Inject
    RecyclerView.LayoutManager mLayoutManager;

    CarJoinAdapter mAdapter;

    @BindView(R2.id.recyclerview)
    XRecyclerView mRecyclerView;
    @BindView(R2.id.btn_submit)
    TextView btnSubmit;

    protected int pageSize = 10;
    private int page = 1;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMyCarsListManagerComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_my_cars_list_manager; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle(R.string.module_me_name_my_cars);
        btnSubmit.setText(R.string.module_me_name_add_car_info);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setRefreshHeader(new CustomRefreshHeader2(this));
        mRecyclerView.setLoadingMoreFooter(new CustomMoreFooter(this));
        mRecyclerView.setLoadingMoreEnabled(false);
        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page = 1;
                mPresenter.getCarJoinList(page, pageSize);
            }

            @Override
            public void onLoadMore() {
                mPresenter.getCarJoinList(page + 1, pageSize);
            }
        });
        mAdapter = new CarJoinAdapter(new ArrayList<>(), this);
        mAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(mAdapter);
        getDataForNet();

    }


    private void getDataForNet() {
        mAdapter.clear();
        mAdapter.setState(HelperStateRecyclerViewAdapter.STATE_LOADING);
        mPresenter.getCarJoinList(page = 1, pageSize);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

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
    public BaseRecyclerViewAdapter.OnItemClickListener<CarJoinBean> getOnItemClickListener() {
        return this;
    }

    @Override
    public void onCarJoinList(List<CarJoinBean> result, int page) {
        this.page = page;
        if (this.page == 1) {
            mAdapter.setListAll(result);
        } else {
            mAdapter.addItemsToLast(result);
            boolean hasMore = result.size() == pageSize;
            mRecyclerView.setLoadingMoreEnabled(hasMore);
        }
        mRecyclerView.refreshComplete();
        mRecyclerView.loadMoreComplete();
    }

    @Override
    public void onJoinCarSucceed() {

    }


    @Override
    public void onItemClick(View view, CarJoinBean item, int position) {
        Map<String, Serializable> map = new HashMap<>();
        map.put(AddCarsDetailInfoActivity.IntentValue, item.getGuid());
        map.put(AddCarsDetailInfoActivity.IntentValue_Action, true);
        AppManagerUtil.jump(AddCarsDetailInfoActivity.class, map);
    }

    @Override
    protected void getEventBusHub_Activity(MessageEvent message) {
        super.getEventBusHub_Activity(message);
        if (message.getType().equals(EventBusHub.Message_UpdateMyCarsListManagerActivit)) {
            getDataForNet();
        }

    }

    @OnClick(R2.id.btn_submit)
    public void onViewClicked() {
        Map<String , Serializable> map = new HashMap<>();
        map.put(AddCarsDetailInfoActivity.IntentValue, "");
        map.put(AddCarsDetailInfoActivity.IntentValue_Action, true);
        AppManagerUtil.jump(AddCarsDetailInfoActivity.class, map);
    }
}
