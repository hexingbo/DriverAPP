package com.lesso.module.waybill.mvp.ui.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jess.arms.base.BaseLazyLoadFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.DataHelper;
import com.lesso.module.waybill.R;
import com.lesso.module.waybill.R2;
import com.lesso.module.waybill.di.component.DaggerOrderAccountsChildComponent;
import com.lesso.module.waybill.mvp.contract.OrderAccountsChildContract;
import com.lesso.module.waybill.mvp.presenter.OrderAccountsChildPresenter;
import com.lesso.module.waybill.mvp.ui.adapter.OrderAccountListAdapter;
import com.zhouyou.recyclerview.XRecyclerView;
import com.zhouyou.recyclerview.adapter.HelperStateRecyclerViewAdapter;
import com.zhouyou.recyclerview.custom.CustomMoreFooter;
import com.zhouyou.recyclerview.custom.CustomRefreshHeader2;

import javax.inject.Inject;

import butterknife.BindView;
import me.jessyan.armscomponent.commonres.enums.AuthenticationStatusType;
import me.jessyan.armscomponent.commonres.enums.OrderAccountStateType;
import me.jessyan.armscomponent.commonsdk.core.Constants;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * user：贺兴波
 * 2019/11/19 14:34
 * Description:
 * ================================================
 */
public class OrderAccountsChildFragment extends BaseLazyLoadFragment<OrderAccountsChildPresenter> implements OrderAccountsChildContract.View {

    OrderAccountStateType stateType = OrderAccountStateType.B;

    @Inject
    OrderAccountListAdapter mAdapter;
    @Inject
    RecyclerView.LayoutManager mLayoutManager;
    @Inject
    Dialog mDialog;

    @BindView(R2.id.recyclerview)
    XRecyclerView mRecyclerView;

    public static OrderAccountsChildFragment newInstance() {
        OrderAccountsChildFragment fragment = new OrderAccountsChildFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerOrderAccountsChildComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_order_accounts_child, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initRecyclerView();
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initRecyclerView() {
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setRefreshHeader(new CustomRefreshHeader2(getActivity()));
        mRecyclerView.setLoadingMoreFooter(new CustomMoreFooter(getActivity()));
        mRecyclerView.setLoadingMoreEnabled(false);
        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                lazyLoadData();
            }

            @Override
            public void onLoadMore() {
                mPresenter.getOrderAccounts(stateType, false);
            }
        });
    }

    /**
     * 通过此方法可以使 Fragment 能够与外界做一些交互和通信, 比如说外部的 Activity 想让自己持有的某个 Fragment 对象执行一些方法,
     * 建议在有多个需要与外界交互的方法时, 统一传 {@link Message}, 通过 what 字段来区分不同的方法, 在 {@link #setData(Object)}
     * 方法中就可以 {@code switch} 做不同的操作, 这样就可以用统一的入口方法做多个不同的操作, 可以起到分发的作用
     * <p>
     * 调用此方法时请注意调用时 Fragment 的生命周期, 如果调用 {@link #setData(Object)} 方法时 {@link Fragment#onCreate(Bundle)} 还没执行
     * 但在 {@link #setData(Object)} 里却调用了 Presenter 的方法, 是会报空的, 因为 Dagger 注入是在 {@link Fragment#onCreate(Bundle)} 方法中执行的
     * 然后才创建的 Presenter, 如果要做一些初始化操作,可以不必让外部调用 {@link #setData(Object)}, 在 {@link #initData(Bundle)} 中初始化就可以了
     * <p>
     * Example usage:
     * <pre>
     * public void setData(@Nullable Object data) {
     *     if (data != null && data instanceof Message) {
     *         switch (((Message) data).what) {
     *             case 0:
     *                 loadData(((Message) data).arg1);
     *                 break;
     *             case 1:
     *                 refreshUI();
     *                 break;
     *             default:
     *                 //do something
     *                 break;
     *         }
     *     }
     * }
     *
     * // call setData(Object):
     * Message data = new Message();
     * data.what = 0;
     * data.arg1 = 1;
     * fragment.setData(data);
     * </pre>
     *
     * @param data 当不需要参数时 {@code data} 可以为 {@code null}
     */
    @Override
    public void setData(@Nullable Object data) {
        String a = (String) data;
        switch (a) {
            case "待确认":
                stateType = OrderAccountStateType.Z;
                break;
            case "确认中":
                stateType = OrderAccountStateType.A;
                break;
            case "已确认":
                stateType = OrderAccountStateType.B;
                break;
            default:
                stateType = OrderAccountStateType.B;
        }

    }

    @Override
    public void showLoading() {
        if (!mRecyclerView.isRefresh() || !mRecyclerView.isLoadingMore()) {
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
    }

    @Override
    public void endLoadMore() {
        if (mRecyclerView.isLoadingMore())
            mRecyclerView.loadMoreComplete();
    }

    @Override
    public OrderAccountStateType getOrderAccountStateType() {
        return stateType;
    }

    @Override
    protected void lazyLoadData() {
        if (!DataHelper.getStringSF(getContext(), Constants.SP_VERIFY_STATUS).equals(AuthenticationStatusType.D.name())) {
            mAdapter.setState(HelperStateRecyclerViewAdapter.STATE_ERROR);
        } else {
            mAdapter.setState(HelperStateRecyclerViewAdapter.STATE_LOADING);
            mPresenter.getOrderAccounts(getOrderAccountStateType(), true);
        }
    }

    @Override
    public void onDestroy() {
        mAdapter.clear();//清除掉适配器中数据
        super.onDestroy();
    }

}
