package com.lesso.module.waybill.mvp.model.api.service;


import com.lesso.module.waybill.mvp.model.api.Api;
import com.lesso.module.waybill.mvp.model.entity.OrderAccountBean;
import com.lesso.module.waybill.mvp.model.entity.SubmitDriverTransportPunchBean;
import com.lesso.module.waybill.mvp.model.entity.SubmitGetOrderAccountsBean;
import com.lesso.module.waybill.mvp.model.entity.SubmitOrderSaveAccountsBean;
import com.lesso.module.waybill.mvp.model.entity.SubmitSaveFreightNoBean;
import com.lesso.module.waybill.mvp.model.entity.SubmitWayBillListBean;
import com.lesso.module.waybill.mvp.model.entity.SubmitWayBillReceiptBean;
import com.lesso.module.waybill.mvp.model.entity.SubmitWayBillSendBean;
import com.lesso.module.waybill.mvp.model.entity.UpdateDetailBean;
import com.lesso.module.waybill.mvp.model.entity.WayBillDetailBean;
import com.lesso.module.waybill.mvp.model.entity.WayBillListBean;

import java.util.List;

import io.reactivex.Observable;
import me.jessyan.armscomponent.commonres.constant.CommonHttpUrl;
import me.jessyan.armscomponent.commonsdk.base.bean.HttpResult;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

import static me.jessyan.retrofiturlmanager.RetrofitUrlManager.DOMAIN_NAME_HEADER;

/**
 * @Author :hexingbo
 * @Date :2019/10/4
 * @FileName： ModuleMeService
 * @Describe :
 */
public interface ModuleWayBillService {
    /**
     * 13、查询订单列表
     */
    @Headers({DOMAIN_NAME_HEADER + Api.Module_Waybill_Doman_Name})
    @POST(CommonHttpUrl.API_getWayBillList)
    Observable<HttpResult<List<WayBillListBean>>> getWayBillList(@Body SubmitWayBillListBean bean);

    /**
     * 14、查询订单详情
     */
    @Headers({DOMAIN_NAME_HEADER + Api.Module_Waybill_Doman_Name})
    @GET(CommonHttpUrl.API_getWayBillDetail)
    Observable<HttpResult<WayBillDetailBean>> getWayBillDetail(@Query("orderId") String orderId);

    /**
     * 15、保存货运单号
     */
    @Headers({DOMAIN_NAME_HEADER + Api.Module_Waybill_Doman_Name})
    @POST(CommonHttpUrl.API_postSaveFreightNo)
    Observable<HttpResult> postSaveFreightNo(@Body SubmitSaveFreightNoBean bean);

    /**
     * 24、订单发货
     */
    @Headers({DOMAIN_NAME_HEADER + Api.Module_Waybill_Doman_Name})
    @POST(CommonHttpUrl.API_postSendWayBill)
    Observable<HttpResult> postSendWayBill(@Body SubmitWayBillSendBean bean);

    /**
     * 25、司机运输打卡
     */
    @Headers({DOMAIN_NAME_HEADER + Api.Module_Waybill_Doman_Name})
    @POST(CommonHttpUrl.API_postDriverTransportPunch)
    Observable<HttpResult> postDriverTransportPunch(@Body SubmitDriverTransportPunchBean bean);

    /**
     * 26、订单收货
     */
    @Headers({DOMAIN_NAME_HEADER + Api.Module_Waybill_Doman_Name})
    @POST(CommonHttpUrl.API_postWayBillReceipt)
    Observable<HttpResult> postWayBillReceipt(@Body SubmitWayBillReceiptBean bean);

    /**
     * 32、生成对账单
     */
    @Headers({DOMAIN_NAME_HEADER + Api.Module_Waybill_Doman_Name})
    @POST(CommonHttpUrl.API_postOrderSaveAccounts)
    Observable<HttpResult> postOrderSaveAccounts(@Body SubmitOrderSaveAccountsBean bean);

    /**
     * 33、对账单列表
     */
    @Headers({DOMAIN_NAME_HEADER + Api.Module_Waybill_Doman_Name})
    @POST(CommonHttpUrl.API_postOrderAccounts)
    Observable<HttpResult<List<OrderAccountBean>>> getOrderAccounts(@Body SubmitGetOrderAccountsBean bean);

    @Headers({DOMAIN_NAME_HEADER + Api.Module_Waybill_Doman_Name})
    @GET(CommonHttpUrl.API_getNewAppVersion)
    Observable<HttpResult<UpdateDetailBean>> getUpdateDetail(@Query("appSource")String appSource);
}
