package com.lesso.module.me.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.lesso.module.me.mvp.contract.CarJoinListContract;
import com.lesso.module.me.mvp.model.api.service.ModuleMeService;
import com.lesso.module.me.mvp.model.entity.CarJoinBean;
import com.lesso.module.me.mvp.model.entity.CarJoinReq;
import com.lesso.module.me.mvp.model.entity.QueryCarJoinReq;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import me.jessyan.armscomponent.commonsdk.base.bean.HttpResult;


/**
 * ================================================
 * user：贺兴波
 * 2019/11/26 11:37
 * Description:
 * ================================================
 */
@ActivityScope
public class CarJoinListModel extends BaseModel implements CarJoinListContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public CarJoinListModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<HttpResult<List<CarJoinBean>>> getCarJoinList(QueryCarJoinReq carJoinReq) {
        return mRepositoryManager.obtainRetrofitService(ModuleMeService.class).getCarJoinList(carJoinReq);
    }

    @Override
    public Observable<HttpResult> joinCar(CarJoinReq carJoinReq) {
        return mRepositoryManager.obtainRetrofitService(ModuleMeService.class).joinCar(carJoinReq);
    }
}