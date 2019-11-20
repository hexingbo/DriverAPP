package com.lesso.module.waybill.mvp.model;

import android.app.Application;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.lesso.module.waybill.mvp.contract.OrderAccountsChildContract;
import com.lesso.module.waybill.mvp.model.api.service.ModuleWayBillService;
import com.lesso.module.waybill.mvp.model.entity.OrderAccountBean;
import com.lesso.module.waybill.mvp.model.entity.SubmitGetOrderAccountsBean;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import me.jessyan.armscomponent.commonres.enums.OrderAccountStateType;
import me.jessyan.armscomponent.commonsdk.base.bean.HttpResult;


/**
 * ================================================
 * user：贺兴波
 * 2019/11/19 14:34
 * Description:
 * ================================================
 */
@FragmentScope
public class OrderAccountsChildModel extends BaseModel implements OrderAccountsChildContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public OrderAccountsChildModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }


    @Override
    public Observable<HttpResult<List<OrderAccountBean>>> getOrderAccounts(int current, int size,
                                                                           @NonNull OrderAccountStateType stateType,
                                                                           @NonNull String driverId, String condition,
                                                                           String driverDateStart, String driverDateEnd) {
        return mRepositoryManager.obtainRetrofitService(ModuleWayBillService.class)
                .getOrderAccounts(new SubmitGetOrderAccountsBean(current, size,
                        new SubmitGetOrderAccountsBean.EntityBean(stateType.name(), driverId, condition, driverDateStart, driverDateEnd)));
    }
}