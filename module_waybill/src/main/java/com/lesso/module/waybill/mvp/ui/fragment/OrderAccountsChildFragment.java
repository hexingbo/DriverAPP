package com.lesso.module.waybill.mvp.ui.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.jess.arms.base.BaseLazyLoadFragment;
import com.jess.arms.base.MessageEvent;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.AppManagerUtil;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.DataHelper;
import com.jess.arms.utils.DeviceUtils;
import com.lesso.module.waybill.R;
import com.lesso.module.waybill.R2;
import com.lesso.module.waybill.di.component.DaggerOrderAccountsChildComponent;
import com.lesso.module.waybill.mvp.contract.OrderAccountsChildContract;
import com.lesso.module.waybill.mvp.model.entity.OrderAccountBean;
import com.lesso.module.waybill.mvp.presenter.OrderAccountsChildPresenter;
import com.lesso.module.waybill.mvp.ui.adapter.OrderAccountListAdapter;
import com.zhouyou.recyclerview.XRecyclerView;
import com.zhouyou.recyclerview.adapter.HelperStateRecyclerViewAdapter;
import com.zhouyou.recyclerview.custom.CustomMoreFooter;
import com.zhouyou.recyclerview.custom.CustomRefreshHeader2;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import me.jessyan.armscomponent.commonres.constant.CommonConstant;
import me.jessyan.armscomponent.commonres.enums.AuthenticationStatusType;
import me.jessyan.armscomponent.commonres.enums.OrderAccountStateType;
import me.jessyan.armscomponent.commonsdk.core.Constants;
import me.jessyan.armscomponent.commonsdk.core.EventBusHub;
import me.jessyan.armscomponent.commonsdk.core.RouterHub;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * user：贺兴波
 * 2019/11/19 14:34
 * Description:对账列表
 * ================================================
 */
public class OrderAccountsChildFragment extends BaseLazyLoadFragment<OrderAccountsChildPresenter>
        implements OrderAccountsChildContract.View, OrderAccountListAdapter.OnAdapterViewClickListener, TextView.OnEditorActionListener {

    OrderAccountStateType stateType = OrderAccountStateType.B;

    @Inject
    OrderAccountListAdapter mAdapter;
    @Inject
    RecyclerView.LayoutManager mLayoutManager;
    @Inject
    Dialog mDialog;

    @BindView(R2.id.recyclerview)
    XRecyclerView mRecyclerView;
    @BindView(R2.id.ll_create_accounts)
    LinearLayout llCreateAccounts;
    @BindView(R2.id.cb_all)
    CheckBox cbAll;
    @BindView(R2.id.tv_create_accounts_total_money)
    TextView tvCreateAccountsTotalMoney;
    @BindView(R2.id.tv_create_accounts)
    TextView tvCreateAccounts;
    @BindView(R2.id.et_search)
    EditText etSearch;
    @BindView(R2.id.tv_start_time)
    TextView tvStartTime;
    @BindView(R2.id.tv_end_time)
    TextView tvEndTime;

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
        llCreateAccounts.setVisibility(stateType == OrderAccountStateType.Z ? View.VISIBLE : View.GONE);
    }

    private void initRecyclerView() {
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setRefreshHeader(new CustomRefreshHeader2(getActivity()));
        mRecyclerView.setLoadingMoreFooter(new CustomMoreFooter(getActivity()));
        mRecyclerView.setLoadingMoreEnabled(false);
        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                mPresenter.getOrderAccounts(stateType, true);
            }

            @Override
            public void onLoadMore() {
                mPresenter.getOrderAccounts(stateType, false);
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
                mPresenter.setSearchValue(s.toString());
            }
        });

        setStartTimeValue(mPresenter.getDriverDateStart());
        setEndTimeValue(mPresenter.getDriverDateEnd());
        mRecyclerView.setFocusable(true);
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
    public OrderAccountListAdapter.OnAdapterViewClickListener getOnAdapterViewClickListener() {
        return this;
    }

    @Override
    public void setStartTimeValue(String time) {
        tvStartTime.setText(time);
    }

    @Override
    public void setEndTimeValue(String time) {
        tvEndTime.setText(time);
    }

    @Override
    public void onCheckedItemChanged(boolean isSelectedAll, ArrayList<OrderAccountBean> selectedList) {
        cbAll.setChecked(isSelectedAll);
        if (selectedList != null) {
            double totalMoney = 0.00;
            for (OrderAccountBean bean : selectedList) {
                totalMoney += (bean.getTransportCosts().contains(".") ? Double.valueOf(bean.getTransportCosts()) : Integer.valueOf(bean.getTransportCosts()));
            }
            tvCreateAccountsTotalMoney.setText(totalMoney + "");
        }
    }

    @Override
    public void onCheckedItem(int position, OrderAccountBean item) {
        ARouter.getInstance().build(RouterHub.Waybill_WayBillDetailActivity)
                .withString(CommonConstant.IntentWayBillDetailKey_OrderId, item.getOrderId())
                .navigation(AppManagerUtil.getCurrentActivity());
    }

    @Override
    protected void lazyLoadData() {
        mAdapter.clear();
        if (ArmsUtils.isEmpty(DataHelper.getStringSF(getContext(), Constants.SP_VERIFY_STATUS)) || !DataHelper.getStringSF(getContext(), Constants.SP_VERIFY_STATUS).equals(AuthenticationStatusType.D.name())) {
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

    @OnClick({R2.id.cb_all, R2.id.tv_create_accounts, R2.id.tv_start_time, R2.id.tv_end_time, R2.id.img_search})
    public void onViewClicked(View view) {
        //让EditText失去焦点
        etSearch.setFocusable(false);
        etSearch.setFocusableInTouchMode(true);
        view.setFocusable(true);
        // 隐藏键盘
        DeviceUtils.hideSoftKeyboard(getContext(), view);

        if (view.getId() == R.id.cb_all) {
            //全选
            mAdapter.setSelectedAll(cbAll.isChecked());
            onCheckedItemChanged(cbAll.isChecked(), mAdapter.getSelectedList());
        } else if (view.getId() == R.id.tv_create_accounts) {
            //创建对账单
            String orderId = "";
            if (!ArmsUtils.isEmpty(mAdapter.getSelectedList())) {
                for (OrderAccountBean bean : mAdapter.getSelectedList()) {
                    orderId += bean.getOrderId() + ",";
                }
            }
            mPresenter.postOrderSaveAccounts(orderId);
        } else if (view.getId() == R.id.tv_start_time) {
            mPresenter.showTimePickerView(getContext(), true);
        } else if (view.getId() == R.id.tv_end_time) {
            mPresenter.showTimePickerView(getContext(), false);
        } else if (view.getId() == R.id.img_search) {
            lazyLoadData();
        }
    }

    @Override
    protected void getEventBusHub_Fragment(MessageEvent message) {
        super.getEventBusHub_Fragment(message);
        if (message.getType().equals(EventBusHub.Message_UpdateOrderAccountsList)) {
            lazyLoadData();
        }
    }

    /**
     * 输入框搜索监听
     *
     * @param v
     * @param actionId
     * @param event
     * @return
     */
    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        /*判断是否是“搜索”键*/
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
//           String searcKeyValue = etSearch.getText().toString().trim();
//            if (TextUtils.isEmpty(etSearch)) {
//                RingToast.show("请输入需要搜索的关键字");
//                return true;
//            }
            // 隐藏键盘
            DeviceUtils.hideSoftKeyboard(getContext(), v);
            //让EditText失去焦点
            etSearch.setFocusable(false);
            etSearch.setFocusableInTouchMode(true);
            lazyLoadData();
            return true;
        }
        return false;
    }

}
