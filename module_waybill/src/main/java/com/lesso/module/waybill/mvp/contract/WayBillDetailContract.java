package com.lesso.module.waybill.mvp.contract;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.jess.arms.base.ILoadlayoutView;
import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.jess.arms.utils.PermissionUtil;
import com.lesso.module.waybill.mvp.model.entity.WayBillDetailBean;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.Observable;
import me.jessyan.armscomponent.commonsdk.base.bean.HttpResult;


/**
 * ================================================
 * user：贺兴波
 * 2019/11/15 23:03
 * Description:
 * ================================================
 */
public interface WayBillDetailContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface ViewI extends IView, ILoadlayoutView {

        Activity getActivity();

        RxPermissions getRxPermissions();

        PermissionUtil.RequestPermission getRequestPermission();

        void checkPermissionSuccess(android.view.View view, @Nullable WayBillDetailBean currentBean, @NonNull String latitude, @NonNull String longitude, @NonNull String addressInfo);

        void setWayBillDetailBean(WayBillDetailBean data);

        String getOrderStatus();
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {

        /**
         * 查询订单详情
         *
         * @param orderId 订单id
         * @return
         */
        Observable<HttpResult<WayBillDetailBean>> getWayBillDetail(@Nullable String orderId);

        /**
         * 保存货运单号
         *
         * @param orderId   订单id
         * @param freightNo 货运单号
         * @return
         */
        Observable<HttpResult> postSaveFreightNo(@Nullable String orderId, @Nullable String freightNo);

        /**
         * 运输打卡
         *
         * @param orderId        订单id
         * @param orderNo        订单号
         * @param carNo          车牌号
         * @param currentUserId  用户id
         * @param latitude       定位经度
         * @param longitude      定位纬度
         * @param checkInAddress 定位地址
         * @return
         */
        Observable<HttpResult> postDriverTransportPunch(@NonNull String orderId, @NonNull String orderNo,
                                                        @NonNull String carNo, @NonNull String currentUserId,
                                                        @NonNull String latitude, @NonNull String longitude, @NonNull String checkInAddress);

        /**
         * 订单收货
         *
         * @param orderId         订单id
         * @param orderNo         订单号
         * @param carNo           车牌号
         * @param currentUserId   用户id
         * @param deliveryAddress 定位地址
         * @return
         */
        Observable<HttpResult> postWayBillReceipt(@NonNull String orderId, @NonNull String orderNo,
                                                  @NonNull String carNo, @NonNull String currentUserId,
                                                  @NonNull String deliveryAddress);

        /**
         * 订单发货提交数据
         *
         * @param currentUserId 司机id
         * @param orderId       订单ID
         * @param orderNo       订单号
         * @param carNo         车牌号
         * @param shipAddress   发货地址
         */
        Observable<HttpResult> postSendWayBill(@Nullable String currentUserId, @Nullable String orderId, @Nullable String orderNo, @Nullable String carNo, @Nullable String shipAddress);
    }
}
