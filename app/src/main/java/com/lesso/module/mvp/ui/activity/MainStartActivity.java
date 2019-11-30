package com.lesso.module.mvp.ui.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.base.MessageEvent;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.integration.AppManager;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.PermissionUtil;
import com.lesso.module.R;
import com.lesso.module.di.component.DaggerMainStartComponent;
import com.lesso.module.mvp.contract.MainStartContract;
import com.lesso.module.mvp.presenter.MainStartPresenter;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.yanzhenjie.sofia.StatusView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.BindView;
import me.jessyan.armscomponent.commonres.dialog.MaterialDialog;
import me.jessyan.armscomponent.commonres.dialog.MyHintDialog;
import me.jessyan.armscomponent.commonsdk.core.EventBusHub;
import me.jessyan.armscomponent.commonsdk.core.RouterHub;
import me.jessyan.armscomponent.commonservice.me.bean.MessageFragmentView;
import me.jessyan.armscomponent.commonservice.me.bean.MyFragmentView;
import me.jessyan.armscomponent.commonservice.me.bean.WayBillManagerFragmentView;
import me.jessyan.armscomponent.commonservice.me.service.MessageViewService;
import me.jessyan.armscomponent.commonservice.me.service.MyViewService;
import me.jessyan.armscomponent.commonservice.me.service.WayBillViewService;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * =============================================
 * 作    者：贺兴波
 * 时    间：2019/10/02 16:30
 * 描    述：z主界面
 * =============================================
 */
@Route(path = RouterHub.APP_MainStartActivity)
public class MainStartActivity extends BaseActivity<MainStartPresenter> implements MainStartContract.View, OnTabSelectListener {

    @Inject
    RxPermissions mRxPermissions;
    @Inject
    Dialog mDialog;
    @Inject
    MyHintDialog mMyHintDialog;
    @Inject
    MaterialDialog mPermissionDialog;
    @BindString(R.string.permission_request_location)
    String mStrPermission;

    @Autowired(name = RouterHub.Me_Service_MyViewService)
    MyViewService mMyViewService;
    @Autowired(name = RouterHub.Message_Service_MessageViewService)
    MessageViewService mMessageViewService;
    @Autowired(name = RouterHub.Waybill_Service_WayBillViewService)
    WayBillViewService mWayBillViewService;

    @BindView(R.id.status_views)
    StatusView statusViews;
    @BindView(R.id.fl_main_app)
    FrameLayout flMainApp;
    @BindView(R.id.main_table)
    CommonTabLayout mainTable;

    private int selectIndex = 0;
    private long mPressedTime;

    Fragment mCurrentFragment;
    private Fragment myFragment;
    private Fragment messageFragment;
    private Fragment wayBillManagerFragment;


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMainStartComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_main_start; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);//在Activity/Fragment类里面进入Arouter 注入
        loadFragmentView();
        ArrayList<CustomTabEntity> mTabEntities = mPresenter.getTabEntity();
        mainTable.setTabData(mTabEntities);
        mainTable.setOnTabSelectListener(this);
        mPresenter.checkPermission();
    }

    @Override
    public void onTabSelect(int position) {
        if (position == selectIndex) {
            return;
        }
        statusViews.setVisibility(View.VISIBLE);
        selectIndex = position;
        switch (position) {
            case 0:
                selectedWayBillManagerFragment();
                break;
            case 1:
                selectedMessageFragment();
                break;
            case 2:
                statusViews.setVisibility(View.GONE);
                selectedMyFragment();
                break;
            case 3:
                mainTable.showMsg(2, 10);//设置红点
                break;
            default:
                break;
        }
    }

    @Override
    public void onTabReselect(int position) {
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

    private void loadFragmentView() {
        //当非集成调试阶段, 宿主 App 由于没有依赖其他组件, 所以使用不了对应组件提供的服务
        if (mWayBillViewService == null) {
            return;
        }
        if (wayBillManagerFragment == null) {
            WayBillManagerFragmentView wayBillManagerFragmentView = mWayBillViewService.getWayBillManagerFragmentView();
            wayBillManagerFragment = wayBillManagerFragmentView.getWayBillManagert();
        }
        initDefaultFragment(wayBillManagerFragment);
    }


    /**
     * 初始化第一个默认的fragment
     */
    protected void initDefaultFragment(Fragment fragment) {
        //开启一个事务
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        //add：往碎片集合中添加一个碎片；
        //replace：移除之前所有的碎片，替换新的碎片（remove和add的集合体）(很少用，不推荐，因为是重新加载，所以消耗流量)
        //参数：1.公共父容器的的id  2.fragment的碎片
        transaction.add(R.id.fl_main_app, fragment);
        transaction.addToBackStack(null);

        //提交事务
        transaction.commit();
        mCurrentFragment = fragment;
    }

    /**
     * 显示“我的模块”Fragment
     */
    private void selectedMyFragment() {
        //当非集成调试阶段, 宿主 App 由于没有依赖其他组件, 所以使用不了对应组件提供的服务
        //当非集成调试阶段, 宿主 App 由于没有依赖其他组件, 所以使用不了对应组件提供的服务
        if (mMyViewService == null) {
            return;
        }
        if (myFragment == null) {
            MyFragmentView myFragmentView = mMyViewService.getMyFragmentView();
            myFragment = myFragmentView.getMyFragment();
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (myFragment.isAdded()) {
            transaction.hide(mCurrentFragment).show(myFragment);
        } else {
            transaction.hide(mCurrentFragment).add(R.id.fl_main_app, myFragment);
            transaction.addToBackStack(null);
        }
        mCurrentFragment = myFragment;
        transaction.commitAllowingStateLoss();
    }

    /**
     * 显示“消息模块”Fragment
     */
    private void selectedMessageFragment() {
        //当非集成调试阶段, 宿主 App 由于没有依赖其他组件, 所以使用不了对应组件提供的服务
        if (mMessageViewService == null) {
            return;
        }
        if (messageFragment == null) {
            MessageFragmentView messageFragmentView = mMessageViewService.getMessageFragmentView();
            messageFragment = messageFragmentView.getMessageFragment();
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (messageFragment.isAdded()) {
            transaction.hide(mCurrentFragment).show(messageFragment);
        } else {
            transaction.hide(mCurrentFragment).add(R.id.fl_main_app, messageFragment);
            transaction.addToBackStack(null);
        }
        mCurrentFragment = messageFragment;
        transaction.commitAllowingStateLoss();
    }

    /**
     * 显示“运单模块”Fragment
     */
    private void selectedWayBillManagerFragment() {
        //当非集成调试阶段, 宿主 App 由于没有依赖其他组件, 所以使用不了对应组件提供的服务
        if (mWayBillViewService == null) {
            return;
        }
        if (wayBillManagerFragment == null) {
            WayBillManagerFragmentView wayBillManagerFragmentView = mWayBillViewService.getWayBillManagerFragmentView();
            wayBillManagerFragment = wayBillManagerFragmentView.getWayBillManagert();
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (wayBillManagerFragment.isAdded()) {
            transaction.hide(mCurrentFragment).show(wayBillManagerFragment);
        } else {
            transaction.hide(mCurrentFragment).add(R.id.fl_main_app, wayBillManagerFragment);
            transaction.addToBackStack(null);
        }
        mCurrentFragment = wayBillManagerFragment;
        transaction.commitAllowingStateLoss();
    }

    @Override
    public void onBackPressed() {
        //获取第一次按键时间
        long mNowTime = System.currentTimeMillis();
        //比较两次按键时间差
        if ((mNowTime - mPressedTime) > 2000) {
            ArmsUtils.makeText(getApplicationContext(),
                    "再按一次退出" + ArmsUtils.getString(getApplicationContext(), R.string.app_name));
            mPressedTime = mNowTime;
        } else {
//            super.onBackPressed();
            AppManager.getAppManager().appExit();
        }
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void setLocationAddress(String result) {
        showMessage(result);
    }

    @Override
    public RxPermissions getRxPermissions() {
        return mRxPermissions;
    }

    @Override
    public PermissionUtil.RequestPermission getRequestPermission() {
        return new PermissionUtil.RequestPermission() {
            @Override
            public void onRequestPermissionSuccess() {
                //request permission success, do something.
            }

            @Override
            public void onRequestPermissionFailure(List<String> permissions) {
                //如果用户拒绝了其中一个授权请求，则提醒用户
//                showMessage("Request permissions failure");
                showMessage(mStrPermission);
            }

            @Override
            public void onRequestPermissionFailureWithAskNeverAgain(List<String> permissions) {
                //如果用户拒绝了其中一个授权请求，且勾选了不再提醒，则需要引导用户到权限管理页面开启
//                showMessage("Need to go to the settings");
                mPermissionDialog.setMessage("请前往设置中心开启相应权限");
                mPermissionDialog.show();
            }
        };
    }

    @Override
    protected void getEventBusHub_Activity(MessageEvent message) {
        super.getEventBusHub_Activity(message);
        if (message.getType().equals(EventBusHub.Message_SelectedWayBillManagerFragment)) {
            if (mainTable.getCurrentTab() != 0) {
                mainTable.setCurrentTab(0);
                onTabSelect(0);
            }
        }
    }
}
