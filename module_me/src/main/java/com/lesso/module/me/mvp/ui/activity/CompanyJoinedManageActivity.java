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

import com.alibaba.android.arouter.facade.annotation.Route;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.base.MessageEvent;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.AppManagerUtil;
import com.jess.arms.utils.ArmsUtils;
import com.lesso.module.me.R;
import com.lesso.module.me.R2;
import com.lesso.module.me.di.component.DaggerCompanyJoinedManageComponent;
import com.lesso.module.me.mvp.contract.CompanyJoinedManageContract;
import com.lesso.module.me.mvp.model.entity.CompanyJoinedBean;
import com.lesso.module.me.mvp.presenter.CompanyJoinedManagePresenter;
import com.lesso.module.me.mvp.ui.adapter.CompanyJoinedAdapter;
import com.zhouyou.recyclerview.XRecyclerView;
import com.zhouyou.recyclerview.adapter.BaseRecyclerViewAdapter;
import com.zhouyou.recyclerview.adapter.HelperStateRecyclerViewAdapter;
import com.zhouyou.recyclerview.custom.CustomMoreFooter;
import com.zhouyou.recyclerview.custom.CustomRefreshHeader2;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import me.jessyan.armscomponent.commonres.base.BaseIntentBean;
import me.jessyan.armscomponent.commonres.dialog.MyHintDialog;
import me.jessyan.armscomponent.commonres.enums.CompanyActionType;
import me.jessyan.armscomponent.commonsdk.core.EventBusHub;
import me.jessyan.armscomponent.commonsdk.core.RouterHub;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * user：贺兴波
 * 2019/11/18 10:14
 * Description:加盟公司管理
 * ================================================
 */
@Route(path = RouterHub.Me_CompanyJoinedManageActivity)
public class CompanyJoinedManageActivity extends BaseActivity<CompanyJoinedManagePresenter> implements CompanyJoinedManageContract.View {

    @Inject
    Dialog mDialog;
    @Inject
    RecyclerView.LayoutManager mLayoutManager;
    @Inject
    CompanyJoinedAdapter mAdapter;
    @Inject
    MyHintDialog myHintDialog;

    @BindView(R2.id.recyclerview)
    XRecyclerView mRecyclerView;
    @BindView(R2.id.btn_submit)
    TextView btnSubmit;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerCompanyJoinedManageComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_company_joined_manage; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle(R.string.me_name_company_manager);
        btnSubmit.setText(getString(R.string.module_me_go_join));
        initRecyclerView();
        mRecyclerView.setAdapter(mAdapter);
        getDataForNet();
    }

    private void getDataForNet() {
        mAdapter.clear();
        mAdapter.setState(HelperStateRecyclerViewAdapter.STATE_LOADING);
        mPresenter.postCompanyJoinedList(true);
    }


    private void initRecyclerView() {
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setRefreshHeader(new CustomRefreshHeader2(getActivity()));
        mRecyclerView.setLoadingMoreFooter(new CustomMoreFooter(getActivity()));
        mRecyclerView.setLoadingMoreEnabled(false);
        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                mPresenter.postCompanyJoinedList(true);
            }

            @Override
            public void onLoadMore() {
                mPresenter.postCompanyJoinedList(false);
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
    public BaseRecyclerViewAdapter.OnItemClickListener<CompanyJoinedBean> getOnItemClickListener() {
        return new BaseRecyclerViewAdapter.OnItemClickListener<CompanyJoinedBean>() {
            @Override
            public void onItemClick(View view, CompanyJoinedBean item, int position) {
                CompanyActionType operatorType;
                String msg;
                if (view.getId() == R.id.tv_agree) {//同意
                    operatorType = CompanyActionType.AGREE;
                    msg = "确认同意";
                } else if (view.getId() == R.id.tv_cancel) {//取消
                    operatorType = CompanyActionType.CANCEL;
                    msg = "确认取消？";
                } else if (view.getId() == R.id.tv_est) {//退出
                    operatorType = CompanyActionType.QUIT;
                    msg = "确认退出？";
                } else if (view.getId() == R.id.tv_refuse) {//拒绝
                    operatorType = CompanyActionType.REFUSE;
                    msg = "确认拒绝？";
                } else if (view.getId() == R.id.tv_car_join) {//车辆加盟
                    AppManagerUtil.jump(CarJoinListActivity.class, CarJoinListActivity.IntentValue, item.getCompanyId());
                    return;
                } else {
                    AppManagerUtil.jump(CompanyJoinDetailInfoActivity.class, CompanyJoinDetailInfoActivity.IntentValue, new BaseIntentBean<>(item.getCompanyId(), false));
                    return;
                }
                myHintDialog.setTextContent(msg);
                myHintDialog.setOnDialogListener(new MyHintDialog.OnDialogListener() {
                    @Override
                    public void onItemViewRightListener() {
                        mPresenter.postCompanyJoinedAction(item.getCompanyId(), item.getDriverId(), item.getGuid(), operatorType);
                    }
                });
                myHintDialog.show();
            }
        };
    }

    @Override
    public void endLoadMore() {
        if (mRecyclerView.isLoadingMore())
            mRecyclerView.loadMoreComplete();
    }


    @OnClick(R2.id.btn_submit)
    public void onViewClicked() {
        AppManagerUtil.jump(CompanyJoinManageActivity.class, CompanyJoinManageActivity.IntentValue, true);
    }

    @Override
    protected void getEventBusHub_Activity(MessageEvent message) {
        if (message.getType().equals(EventBusHub.TAG_LOGIN_SUCCESS)
                || message.getType().equals(EventBusHub.Message_UpdateCompanyJoinManageList)) {
            //加盟成功
            getDataForNet();
        }
    }

}
