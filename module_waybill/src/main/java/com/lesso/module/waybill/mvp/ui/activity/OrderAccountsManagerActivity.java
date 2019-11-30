package com.lesso.module.waybill.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.flyco.tablayout.SlidingTabLayout;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.base.MessageEvent;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.lesso.module.waybill.R;
import com.lesso.module.waybill.R2;
import com.lesso.module.waybill.di.component.DaggerOrderAccountsManagerComponent;
import com.lesso.module.waybill.mvp.contract.OrderAccountsManagerContract;
import com.lesso.module.waybill.mvp.presenter.OrderAccountsManagerPresenter;
import com.lesso.module.waybill.mvp.ui.adapter.MyPagerAdapter;

import javax.inject.Inject;

import butterknife.BindView;
import me.jessyan.armscomponent.commonsdk.core.RouterHub;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * user：贺兴波
 * 2019/11/19 12:56
 * Description:对账单管理
 * ================================================
 */
@Route(path = RouterHub.Waybill_OrderAccountsManagerActivity)
public class OrderAccountsManagerActivity extends BaseActivity<OrderAccountsManagerPresenter> implements OrderAccountsManagerContract.View {

    @BindView(R2.id.tab_menu)
    SlidingTabLayout tabMenu;
    @BindView(R2.id.vp_content)
    ViewPager vpContent;

    @Inject
    MyPagerAdapter mAdapter;


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerOrderAccountsManagerComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_order_accounts_manager; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle(R.string.module_waybill_name_order_accounts_manager);
        vpContent.setAdapter(mAdapter);
        vpContent.setOffscreenPageLimit(mPresenter.getFragments().size());
        vpContent.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                mPresenter.getFragments().get(i).tryLoadData();
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        vpContent.setCurrentItem(0);
        tabMenu.setViewPager(vpContent);
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
    protected void getEventBusHub_Activity(MessageEvent message) {

    }
}
