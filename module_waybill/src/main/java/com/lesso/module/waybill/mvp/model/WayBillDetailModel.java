package com.lesso.module.waybill.mvp.model;

import android.app.Application;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.lesso.module.waybill.mvp.contract.WayBillDetailContract;
import com.lesso.module.waybill.mvp.model.api.service.ModuleWayBillService;
import com.lesso.module.waybill.mvp.model.entity.SubmitDriverTransportPunchBean;
import com.lesso.module.waybill.mvp.model.entity.SubmitSaveFreightNoBean;
import com.lesso.module.waybill.mvp.model.entity.SubmitWayBillReceiptBean;
import com.lesso.module.waybill.mvp.model.entity.SubmitWayBillSendBean;
import com.lesso.module.waybill.mvp.model.entity.WayBillDetailBean;

import io.reactivex.Observable;
import me.jessyan.armscomponent.commonsdk.base.bean.HttpResult;


/**
 * ================================================
 * user：贺兴波
 * 2019/11/15 23:03
 * Description:
 * ================================================
 */
@ActivityScope
public class WayBillDetailModel extends BaseModel implements WayBillDetailContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public WayBillDetailModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<HttpResult<WayBillDetailBean>> getWayBillDetail(@Nullable String orderId) {
        return mRepositoryManager.obtainRetrofitService(ModuleWayBillService.class).getWayBillDetail(orderId);
    }

    @Override
    public Observable<HttpResult> postSaveFreightNo(@Nullable String orderId, @Nullable String freightNo) {
        return mRepositoryManager.obtainRetrofitService(ModuleWayBillService.class).postSaveFreightNo(new SubmitSaveFreightNoBean(orderId, freightNo));
    }

    @Override
    public Observable<HttpResult> postDriverTransportPunch(@NonNull String orderId, @NonNull String orderNo,
                                                           @NonNull String carNo, @NonNull String currentUserId,
                                                           @NonNull String latitude, @NonNull String longitude,
                                                           @NonNull String checkInAddress) {
        return mRepositoryManager.obtainRetrofitService(ModuleWayBillService.class)
                .postDriverTransportPunch(new SubmitDriverTransportPunchBean(orderId, orderNo, carNo, currentUserId, latitude, longitude, checkInAddress));
    }

    @Override
    public Observable<HttpResult> postWayBillReceipt(@NonNull String orderId, @NonNull String orderNo,
                                                     @NonNull String carNo, @NonNull String currentUserId,
                                                     @NonNull String deliveryAddress) {
        return mRepositoryManager.obtainRetrofitService(ModuleWayBillService.class)
                .postWayBillReceipt(new SubmitWayBillReceiptBean(orderId, orderNo, carNo, currentUserId, deliveryAddress));
    }

    /**
     * 订单发货提交数据
     *
     * @param currentUserId 司机id
     * @param orderId       订单ID
     * @param orderNo       订单号
     * @param carNo         车牌号
     * @param shipAddress   发货地址
     */
    @Override
    public Observable<HttpResult> postSendWayBill(@Nullable String currentUserId, @Nullable String orderId, @Nullable String orderNo, @Nullable String carNo, @Nullable String shipAddress) {
        return mRepositoryManager.obtainRetrofitService(ModuleWayBillService.class).postSendWayBill(
                new SubmitWayBillSendBean(currentUserId,orderId,orderNo,carNo,shipAddress));
    }

}