package com.lesso.module.message.mvp.ui.fragment;

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
import android.widget.FrameLayout;

import com.alibaba.android.arouter.launcher.ARouter;
import com.hxb.app.loadlayoutlibrary.LoadLayout;
import com.hxb.app.loadlayoutlibrary.OnLoadListener;
import com.hxb.app.loadlayoutlibrary.State;
import com.jess.arms.base.BaseEventBusHub;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.base.MessageEvent;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.integration.EventBusManager;
import com.jess.arms.utils.AppManagerUtil;
import com.jess.arms.utils.ArmsUtils;
import com.lesso.module.message.R;
import com.lesso.module.message.R2;
import com.lesso.module.message.di.component.DaggerMessageManagerComponent;
import com.lesso.module.message.mvp.contract.MessageManagerContract;
import com.lesso.module.message.mvp.model.entity.MessageBean;
import com.lesso.module.message.mvp.presenter.MessageManagerPresenter;
import com.lesso.module.message.mvp.ui.adapter.MessageListAdapter;
import com.zhouyou.recyclerview.XRecyclerView;
import com.zhouyou.recyclerview.adapter.BaseRecyclerViewAdapter;
import com.zhouyou.recyclerview.custom.CustomMoreFooter;
import com.zhouyou.recyclerview.custom.CustomRefreshHeader2;

import javax.inject.Inject;

import butterknife.BindView;
import me.jessyan.armscomponent.commonres.constant.CommonConstant;
import me.jessyan.armscomponent.commonres.enums.MessagePushType;
import me.jessyan.armscomponent.commonsdk.core.EventBusHub;
import me.jessyan.armscomponent.commonsdk.core.RouterHub;
import me.jessyan.armscomponent.commonsdk.utils.Utils;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * =============================================
 * 作    者：贺兴波
 * 时    间：2019/11/13
 * 描    述：MessageManagerFragment
 * =============================================
 */
public class MessageManagerFragment extends BaseFragment<MessageManagerPresenter> implements MessageManagerContract.View, OnLoadListener {

    @Inject
    RecyclerView.LayoutManager mLayoutManager;
    @Inject
    MessageListAdapter mAdapter;
    @Inject
    Dialog mDialog;

    @BindView(R2.id.recyclerview)
    XRecyclerView mRecyclerView;

    protected FrameLayout frameLayout;
    protected LoadLayout mLoadLayout;

    public static MessageManagerFragment newInstance() {
        MessageManagerFragment fragment = new MessageManagerFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerMessageManagerComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(com.jess.arms.R.layout.public_base_loadlayout_fragment, container, false);
        frameLayout = rootView.findViewById(com.jess.arms.R.id.fl_content);
        mLoadLayout = rootView.findViewById(com.jess.arms.R.id.base_load_layout);

        View contentView = inflater.inflate(R.layout.fragment_message_manager, container, false);
        if (contentView != null) {
            frameLayout.addView(contentView);
        }
        mLoadLayout.setOnLoadListener(this);
        mLoadLayout.setLoadingViewId(R.layout.view_custom_loading_data);
        return rootView;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initRecyclerView();
        mRecyclerView.setAdapter(mAdapter);
        onLoad();
    }

    private void initRecyclerView() {
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setRefreshHeader(new CustomRefreshHeader2(getActivity()));
        mRecyclerView.setLoadingMoreFooter(new CustomMoreFooter(getActivity()));
        mRecyclerView.setLoadingMoreEnabled(false);
        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                mPresenter.getMessageList(true);
            }

            @Override
            public void onLoadMore() {
                mPresenter.getMessageList(false);
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

    }

    @Override
    public void showLoading() {
        mDialog.show();
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
    public void onLoad() {
        mPresenter.getMessageList(true);
    }

    @Override
    public void endLoadMore() {
        if (mRecyclerView.isLoadingMore())
            mRecyclerView.loadMoreComplete();
    }

    @Override
    public BaseRecyclerViewAdapter.OnItemClickListener<MessageBean> getOnItemClickListener() {

        return new BaseRecyclerViewAdapter.OnItemClickListener<MessageBean>() {
            @Override
            public void onItemClick(View view, MessageBean item, int position) {
                //item 点击事件
//                currentMessageBean = item;
//                currentPosition = position;
//                AppManagerUtil.jump(MessageDetailActivity.class, MessageDetailActivity.IntentValue, item);

                if (item.getLinkUrl().contains(MessagePushType.DriverBalance.name())) {
                    //对账列表
                    Utils.navigation(mContext, RouterHub.Waybill_OrderAccountsManagerActivity);
                } else if (item.getLinkUrl().contains(MessagePushType.DriverJoin.name())) {
                    //加盟列表
                    Utils.navigation(mContext, RouterHub.Me_CompanyJoinedManageActivity);

                } else if (item.getLinkUrl().contains(MessagePushType.DriverOrder.name())) {
                    //运单列表
                    EventBusManager.getInstance().post(new MessageEvent(EventBusHub.Message_SelectedWayBillManagerFragment));
                } else if (item.getLinkUrl().contains(MessagePushType.OrderDetail.name())) {
                    //订单详情
                    if (item.getLinkUrl().contains("=")) {
                        String id = item.getLinkUrl().substring(item.getLinkUrl().lastIndexOf("="), item.getLinkUrl().length());
                        ARouter.getInstance().build(RouterHub.Waybill_WayBillDetailActivity)
                                .withString(CommonConstant.IntentWayBillDetailKey_OrderId, id)
                                .navigation(AppManagerUtil.getCurrentActivity());
                    }

                }
            }
        };
    }

    @Override
    public void setLayoutState_LOADING() {
        mLoadLayout.setLayoutState(State.LOADING);
    }

    @Override
    public void setLayoutState_SUCCESS() {
        mLoadLayout.setLayoutState(State.SUCCESS);
    }

    @Override
    public void setLayoutState_FAILED() {
        if (mLoadLayout.getLayerType() != State.SUCCESS)
            mLoadLayout.setLayoutState(State.FAILED);
    }

    @Override
    public void setLayoutState_NO_DATA() {
        if (mLoadLayout.getLayerType() != State.SUCCESS)
            mLoadLayout.setLayoutState(State.NO_DATA);
    }


    private MessageBean currentMessageBean;
    private int currentPosition;

    @Override
    protected void getEventBusHub_Fragment(MessageEvent message) {
        super.getEventBusHub_Fragment(message);
        if (message.getType().equals(BaseEventBusHub.TAG_LOGIN_SUCCESS) && message.getType().equals(EventBusHub.Message_MessageManagerList_UpdateData)) {
            currentMessageBean.setHaveRead(1);
            mAdapter.notifyItemChanged(currentPosition);
        }
    }
}
