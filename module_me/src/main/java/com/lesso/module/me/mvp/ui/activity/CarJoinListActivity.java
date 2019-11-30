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
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.AppManagerUtil;
import com.jess.arms.utils.ArmsUtils;
import com.lesso.module.me.R;
import com.lesso.module.me.R2;
import com.lesso.module.me.di.component.DaggerCarJoinListComponent;
import com.lesso.module.me.mvp.contract.CarJoinListContract;
import com.lesso.module.me.mvp.model.entity.CarJoinBean;
import com.lesso.module.me.mvp.model.entity.CarJoinReq;
import com.lesso.module.me.mvp.presenter.CarJoinListPresenter;
import com.lesso.module.me.mvp.ui.adapter.CarJoinDetailAdapter;
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
import me.jessyan.armscomponent.commonres.dialog.MyRoundRectangleHintDialog;
import me.jessyan.armscomponent.commonsdk.core.Constants;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * user：贺兴波
 * 2019/11/26 11:37
 * Description:
 * ================================================
 */
public class CarJoinListActivity extends BaseActivity<CarJoinListPresenter>
        implements CarJoinListContract.View, BaseRecyclerViewAdapter.OnItemClickListener<CarJoinBean> {

    public static final String IntentValue = "companyId";

    @Inject
    Dialog mDialog;
    @Inject
    RecyclerView.LayoutManager mLayoutManager;
    @Inject
    CarJoinDetailAdapter mAdapter;
    @Inject
    MyRoundRectangleHintDialog myHintDialog;

    @BindView(R2.id.recyclerview)
    XRecyclerView mRecyclerView;
    @BindView(R2.id.btn_submit)
    TextView btnSubmit;

    private String companyId;

    private int pageSize = 10;
    private int page = 1;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerCarJoinListComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_car_join_list; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle(R.string.car_join_list);
        btnSubmit.setText(R.string.module_me_goto_join_car);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setRefreshHeader(new CustomRefreshHeader2(this));
        mRecyclerView.setLoadingMoreFooter(new CustomMoreFooter(this));
        mRecyclerView.setLoadingMoreEnabled(false);
        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                mPresenter.getCarJoinList(companyId, 1, pageSize);
            }

            @Override
            public void onLoadMore() {
                mPresenter.getCarJoinList(companyId, page + 1, pageSize);
            }
        });
        companyId = (String) getIntent().getSerializableExtra(CarJoinListActivity.IntentValue);
        myHintDialog.setBtnClose(getString(R.string.module_me_think_again));
        mAdapter.setOnOperateListener(new CarJoinDetailAdapter.OnOperateListener() {
            @Override
            public void onCancel(CarJoinBean item, int position) {
                myHintDialog.setTextContent(getString(R.string.module_me_confirm_cancel_join_car,
                        item.getCarNo()));
                myHintDialog.setBtnSubmit(getString(R.string.module_me_i_want_cancel));
                myHintDialog.setOnDialogListener(new MyRoundRectangleHintDialog.OnDialogListener() {
                    @Override
                    public void onItemViewRightListener() {
                        mPresenter.joinCar(CarJoinReq.ofCancel(item, companyId));
                    }
                });
                myHintDialog.show();
            }

            @Override
            public void onQuit(CarJoinBean item, int position) {
                myHintDialog.setTextContent(getString(R.string.module_me_confirm_quit_join_car,
                        item.getCarNo()));
                myHintDialog.setBtnSubmit(getString(R.string.module_me_i_want_quit));
                myHintDialog.setOnDialogListener(new MyRoundRectangleHintDialog.OnDialogListener() {
                    @Override
                    public void onItemViewRightListener() {
                        mPresenter.joinCar(CarJoinReq.ofQuit(item, companyId));
                    }
                });
                myHintDialog.show();
            }

            @Override
            public void onResubmit(CarJoinBean item, int position) {
                myHintDialog.setTextContent(getString(R.string.module_me_confirm_resubmit_join_car,
                        item.getCarNo()));
                myHintDialog.setBtnSubmit(getString(R.string.module_me_i_want_join));
                myHintDialog.setOnDialogListener(new MyRoundRectangleHintDialog.OnDialogListener() {
                    @Override
                    public void onItemViewRightListener() {
                        ArrayList<CarJoinBean> list = new ArrayList<>();
                        list.add(item);
                        mPresenter.joinCar(CarJoinReq.ofSubmit(list, companyId));
                    }
                });
                myHintDialog.show();
            }
        });

        mRecyclerView.setAdapter(mAdapter);
        getDataForNet();

        btnSubmit.setOnClickListener(l ->
                {
                    Intent intent = new Intent(this, MyCarListSelectActivity.class);
                    intent.putExtra(Constants.KEY_COMPANY_ID, companyId);
                    startActivityForResult(intent,
                            Constants.REQUEST_JOIN_CAR);
                }
        );
    }

    private void getDataForNet() {
        mAdapter.clear();
        mAdapter.setState(HelperStateRecyclerViewAdapter.STATE_LOADING);
        mPresenter.getCarJoinList(companyId, 1, pageSize);
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
    public BaseRecyclerViewAdapter.OnItemClickListener getOnItemClickListener() {
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
        getDataForNet();
    }

    @Override
    public void onItemClick(View view, CarJoinBean item, int position) {
        Map<String, Serializable> map = new HashMap<>();
        map.put(AddCarsDetailInfoActivity.IntentValue, item.getGuid());
        map.put(AddCarsDetailInfoActivity.IntentValue_Action, false);
        AppManagerUtil.jump(AddCarsDetailInfoActivity.class, map);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == Constants.REQUEST_JOIN_CAR && resultCode == RESULT_OK) {
            getDataForNet();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
