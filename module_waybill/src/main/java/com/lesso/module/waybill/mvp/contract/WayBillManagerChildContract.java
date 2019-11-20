package com.lesso.module.waybill.mvp.contract;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.jess.arms.utils.PermissionUtil;
import com.lesso.module.waybill.mvp.model.entity.WayBillListBean;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zhouyou.recyclerview.adapter.BaseRecyclerViewAdapter;

import java.util.List;

import io.reactivex.Observable;
import me.jessyan.armscomponent.commonres.enums.WayBillStateType;
import me.jessyan.armscomponent.commonsdk.base.bean.HttpResult;


/**
 * ================================================
 * user：贺兴波
 * 2019/11/15 17:52
 * Description:
 * ================================================
 */
public interface WayBillManagerChildContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {

        Activity getActivity();

        RxPermissions getRxPermissions();

        PermissionUtil.RequestPermission getRequestPermission();

        WayBillStateType getWayBillStateType();

        BaseRecyclerViewAdapter.OnItemClickListener<WayBillListBean> getOnItemClickListener();

        void endLoadMore();

        void checkPermissionSuccess(android.view.View view, @Nullable WayBillListBean currentBean, @NonNull String latitude, @NonNull String longitude, @NonNull String addressInfo);

    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        /**
         * 获取运单列表数据
         *
         * @param current
         * @param size
         * @param driverId
         * @param stateType
         * @return
         */
        Observable<HttpResult<List<WayBillListBean>>> getWayBillList(int current, int size, @NonNull String driverId, @NonNull WayBillStateType stateType);


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
