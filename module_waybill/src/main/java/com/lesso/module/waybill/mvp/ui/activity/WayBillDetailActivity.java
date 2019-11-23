package com.lesso.module.waybill.mvp.ui.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.hxb.app.loadlayoutlibrary.State;
import com.jess.arms.base.BaseLoadLayoutActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.AppManagerUtil;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.DataHelper;
import com.jess.arms.utils.PermissionUtil;
import com.lesso.module.waybill.R;
import com.lesso.module.waybill.R2;
import com.lesso.module.waybill.di.component.DaggerWayBillDetailComponent;
import com.lesso.module.waybill.mvp.contract.WayBillDetailContract;
import com.lesso.module.waybill.mvp.model.entity.WayBillDetailBean;
import com.lesso.module.waybill.mvp.presenter.WayBillDetailPresenter;
import com.lesso.module.waybill.mvp.ui.adapter.TransportTrackAdapter;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;
import me.jessyan.armscomponent.commonres.constant.CommonConstant;
import me.jessyan.armscomponent.commonres.dialog.MaterialDialog;
import me.jessyan.armscomponent.commonres.dialog.MyHintDialog;
import me.jessyan.armscomponent.commonsdk.core.Constants;
import me.jessyan.armscomponent.commonsdk.core.RouterHub;
import me.jessyan.armscomponent.commonsdk.utils.MapManagerUtils;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * user：贺兴波
 * 2019/11/15 23:03
 * Description:运单详情
 * ================================================
 */
@Route(path = RouterHub.Waybill_WayBillDetailActivity)
public class WayBillDetailActivity extends BaseLoadLayoutActivity<WayBillDetailPresenter> implements WayBillDetailContract.ViewI {

    @Inject
    RxPermissions mRxPermissions;
    @Inject
    Dialog mDialog;
    @Inject
    MyHintDialog mMyHintDialog;
    @Inject
    MaterialDialog mPermissionDialog;
    @Inject
    RecyclerView.LayoutManager mLayoutManager;
    @Inject
    TransportTrackAdapter mAdapter;

    @BindString(R2.string.permission_request_location)
    String mStrPermission;

    private View itemView;

    @BindView(R2.id.tv_date)
    TextView tvDate;
    @BindView(R2.id.tv_car_number)
    TextView tvCarNumber;
    @BindView(R2.id.tv_driver_transport)
    TextView tvDriverTransport;
    @BindView(R2.id.tv_shipping_warehouse)
    TextView tvShippingWarehouse;
    @BindView(R2.id.tv_delivery_type)
    TextView tvDeliveryType;
    @BindView(R2.id.tv_Bill_numbers)
    TextView tvBillNumbers;
    @BindView(R2.id.tv_ship_address)
    TextView tvShipAddress;
    @BindView(R2.id.tv_harvest_address)
    TextView tvHarvestAddress;
    @BindView(R2.id.tv_receiving_party)
    TextView tvReceivingParty;
    @BindView(R2.id.tv_carriage_party)
    TextView tvCarriageParty;
    @BindView(R2.id.ll)
    LinearLayout ll;
    @BindView(R2.id.tv_order_num)
    TextView tvOrderNum;
    @BindView(R2.id.tv_goods_type)
    TextView tvGoodsType;
    @BindView(R2.id.tv_depart_date)
    TextView tvDepartDate;
    @BindView(R2.id.tv_arrival_date)
    TextView tvArrivalDate;
    @BindView(R2.id.tv_remark)
    TextView tvRemark;
    @BindView(R2.id.tv)
    TextView tv;
    @BindView(R2.id.et_goods_number)
    EditText etGoodsNumber;
    @BindView(R2.id.cv_order_number)
    LinearLayout cvOrderNumber;
    @BindView(R2.id.flv)
    RecyclerView flv;
    @BindView(R2.id.ll_track)
    LinearLayout llTrack;
    @BindView(R2.id.tv_fahuo)
    TextView tvFahuo;
    @BindView(R2.id.ll_order_state_fahuo)
    LinearLayout llOrderStateFahuo;
    @BindView(R2.id.tv_daka)
    TextView tvDaka;
    @BindView(R2.id.tv_shouhuo)
    TextView tvShouhuo;
    @BindView(R2.id.ll_order_state_shouhuo)
    LinearLayout llOrderStateShouhuo;

    private WayBillDetailBean currentBean;


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerWayBillDetailComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_way_bill_detail; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle(R.string.module_waybill_name_waybill_detail);
        flv.setLayoutManager(mLayoutManager);
        flv.setAdapter(mAdapter);
        mPresenter.setOrderId(getIntent().getStringExtra(CommonConstant.IntentWayBillDetailKey_OrderId));
    }

    @Override
    public void showLoading() {
        if (State.SUCCESS == mLoadLayout.getLayerType())
            mDialog.show();
    }

    @Override
    public void hideLoading() {
        if (State.SUCCESS == mLoadLayout.getLayerType())
            mDialog.dismiss();
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
    public RxPermissions getRxPermissions() {
        return mRxPermissions;
    }

    @Override
    public PermissionUtil.RequestPermission getRequestPermission() {
        return new PermissionUtil.RequestPermission() {
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
//                            String result = MapUtils.getLocationStr(loc);
                            checkPermissionSuccess(itemView, currentBean, loc.getLatitude() + "", loc.getLongitude() + "", loc.getAddress());
                        } else {
                            showMessage("定位失败，loc is null");
                        }
                    }
                });
                mapManagerUtils.startLocation();
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
    public void checkPermissionSuccess(View view, @Nullable WayBillDetailBean currentBean, @NonNull String latitude, @NonNull String longitude, @NonNull String addressInfo) {
        if (view.getId() == R.id.tv_fahuo) {
            //发货
            mMyHintDialog.setTextContent("确认发货？");
            mMyHintDialog.setOnDialogListener(new MyHintDialog.OnDialogListener() {
                @Override
                public void onItemViewRightListener() {
                    mPresenter.postSendWayBill(currentBean.getOrderId(), currentBean.getOrderNo(), currentBean.getCarNo(),
                            DataHelper.getStringSF(AppManagerUtil.getCurrentActivity(), Constants.SP_USER_ID), addressInfo);
                }
            });
            mMyHintDialog.show();

        } else if (view.getId() == R.id.tv_shouhuo) {
            //收货
            mMyHintDialog.setTextContent("确认收货？");
            mMyHintDialog.setOnDialogListener(new MyHintDialog.OnDialogListener() {
                @Override
                public void onItemViewRightListener() {
                    mPresenter.postWayBillReceipt(currentBean.getOrderId(), currentBean.getOrderNo(), currentBean.getCarNo(),
                            DataHelper.getStringSF(AppManagerUtil.getCurrentActivity(), Constants.SP_USER_ID), addressInfo);
                }
            });
            mMyHintDialog.show();
        } else if (view.getId() == R.id.tv_daka) {
            //打卡
            mPresenter.postDriverTransportPunch(currentBean.getOrderId(), currentBean.getOrderNo(), currentBean.getCarNo(),
                    DataHelper.getStringSF(AppManagerUtil.getCurrentActivity(), Constants.SP_USER_ID), latitude, longitude, addressInfo);
        } else {
            ARouter.getInstance().build(RouterHub.Waybill_WayBillDetailActivity)
                    .withString(CommonConstant.IntentWayBillDetailKey_OrderId, currentBean.getOrderId())
                    .navigation(AppManagerUtil.getCurrentActivity());
        }
    }

    @Override
    public void setWayBillDetailBean(WayBillDetailBean bean) {
        if (bean == null) {
            return;
        }
        currentBean = bean;
        //是否显示运输轨迹
        llTrack.setVisibility(ArmsUtils.isEmpty(bean.getTransportTrack()) ? View.GONE : View.VISIBLE);

        tvDate.setText("预计发货日期：" + bean.getPlanDeliverDate());
        tvReceivingParty.setText("收货方：" + bean.getReceivingParty());
        tvCarriageParty.setText("承运方：" + bean.getLogisticsCompany());

        tvOrderNum.setText("订单号：" + bean.getOrderNo());
        tvGoodsType.setText("货物：" + bean.getGoodsName());
        tvRemark.setText("备注：" + bean.getRemark());

        tvDepartDate.setText("发车时间：" + bean.getDeliverDate());
        tvArrivalDate.setText("到货时间：" + bean.getReceiptDate());
        tvDeliveryType.setText("发货类型：" + bean.getCustomerTypeName());
        tvShippingWarehouse.setText("发货仓：" + bean.getIssuingBay());
        tvCarNumber.setText("车牌号：" + bean.getCarNo());
        tvShipAddress.setText(bean.getShipperAddress());
        tvHarvestAddress.setText(bean.getReceivingPartyAddress());
        tvDriverTransport.setText("司机：" + bean.getDriverBy());

        tvBillNumbers.setText("货运单号：" + bean.getFreightNo());
        //货运单单号只有收货完成，有货运单字段才显示 
//        tvBillNumbers.setVisibility(TextUtils.isEmpty(bean.getFreightNo()) ? View.GONE : View.VISIBLE);
        cvOrderNumber.setVisibility(TextUtils.isEmpty(bean.getFreightNo()) ? View.GONE : View.VISIBLE);

        changeOrderStatusView(bean.getOrderStatus());
    }

    @Override
    public String getOrderStatus() {
        return currentBean != null ? currentBean.getOrderStatus() : "";
    }

    private void changeOrderStatusView(@Nullable String orderState) {
        //订单状态(G(待派车),T(已派车，未签约),A(待发货)，B(待收货),F(已完成))
        switch (orderState) {
            case "G"://待派车
                break;
            case "T"://已派车，未签约
                break;
            case "A"://待发货
                llOrderStateFahuo.setVisibility(View.VISIBLE);
                break;
            case "B"://待收货
                llOrderStateShouhuo.setVisibility(View.VISIBLE);
                break;
            case "F"://已完成
                break;
        }
    }

    @OnClick({R2.id.tv_fahuo, R2.id.tv_daka, R2.id.tv_shouhuo})
    public void onViewClicked(View view) {
        //发货
        itemView = view;
        mPresenter.checkPermission();
    }


}
