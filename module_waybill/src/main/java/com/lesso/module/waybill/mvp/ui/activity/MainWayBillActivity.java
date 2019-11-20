package com.lesso.module.waybill.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.PermissionUtil;
import com.lesso.module.waybill.R;
import com.lesso.module.waybill.R2;
import com.lesso.module.waybill.di.component.DaggerMainWayBillComponent;
import com.lesso.module.waybill.mvp.contract.MainWayBillContract;
import com.lesso.module.waybill.mvp.presenter.MainWayBillPresenter;
import com.lesso.module.waybill.mvp.ui.fragment.WayBillManagerFragment;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import me.jessyan.armscomponent.commonres.utils.MyFragmentUtils;
import me.jessyan.armscomponent.commonsdk.core.RouterHub;
import me.jessyan.armscomponent.commonsdk.utils.MapManagerUtils;
import me.jessyan.armscomponent.commonsdk.utils.MapUtils;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * user：贺兴波
 * 2019/11/13 22:17
 * Description:
 * ================================================
 */
@Route(path = RouterHub.Waybill_MainWayBillActivity)
public class MainWayBillActivity extends BaseActivity<MainWayBillPresenter> implements MainWayBillContract.View {

    @Inject
    RxPermissions mRxPermissions;

    @BindView(R2.id.tv_address)
    TextView tvAddress;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMainWayBillComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_main_way_bill; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        ArmsUtils.statuInScreen(this);//全屏,并且沉侵式状态栏
        setTitle(R.string.waybill_name_waybill_manager);
        MyFragmentUtils.addFragment(getSupportFragmentManager(), new WayBillManagerFragment(), R.id.fl_content);
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
    public void onBackPressed() {
        finish();
    }

    @Override
    public RxPermissions getRxPermissions() {
        return mRxPermissions;
    }

    @Override
    public PermissionUtil.RequestPermission getRequestPermission() {
        return  new PermissionUtil.RequestPermission() {
            @Override
            public void onRequestPermissionSuccess() {
                //request permission success, do something.
                //初始化定位
                MapManagerUtils mapManagerUtils = new MapManagerUtils(
                       getActivity(), new AMapLocationListener() {
                    @Override
                    public void onLocationChanged(AMapLocation loc) {
                        if (null != loc) {
                            //解析定位结果
                            String result = MapUtils.getLocationStr(loc);
                            setAddressInfo(result);
                        } else {
                            showMessage("定位失败，loc is null");
                        }
                    }
                });
                mapManagerUtils.startLocation();
            }

            @Override
            public void onRequestPermissionFailure(List<String> permissions) {
                showMessage("Request permissions failure");
            }

            @Override
            public void onRequestPermissionFailureWithAskNeverAgain(List<String> permissions) {
               showMessage("Need to go to the settings");
            }
        };
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void setAddressInfo(String result) {
        tvAddress.setText(result);
    }
}
