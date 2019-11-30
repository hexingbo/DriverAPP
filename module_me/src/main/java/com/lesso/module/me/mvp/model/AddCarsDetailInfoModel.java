package com.lesso.module.me.mvp.model;

import android.app.Application;
import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.jess.arms.utils.LogUtils;
import com.lesso.module.me.mvp.contract.AddCarsDetailInfoContract;
import com.lesso.module.me.mvp.model.api.service.ModuleMeService;
import com.lesso.module.me.mvp.model.entity.CarTeamDetailBean;
import com.lesso.module.me.mvp.model.entity.SubmitDeleteCarBean;
import com.lesso.module.me.mvp.model.entity.UploadCardFileResultBean;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import me.jessyan.armscomponent.commonres.enums.UploadFileUserCardType;
import me.jessyan.armscomponent.commonsdk.base.bean.HttpResult;
import me.jessyan.armscomponent.commonsdk.base.bean.RequestBodyBean;
import me.jessyan.armscomponent.commonsdk.utils.RequestBodyUtil;
import okhttp3.RequestBody;


/**
 * ================================================
 * user：贺兴波
 * 2019/11/26 10:41
 * Description:
 * ================================================
 */
@ActivityScope
public class AddCarsDetailInfoModel extends BaseModel implements AddCarsDetailInfoContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public AddCarsDetailInfoModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }


    @Override
    public Observable<HttpResult<UploadCardFileResultBean>> postUploadWlCarTeamFile(@Nullable String currentUserId, @Nullable UploadFileUserCardType fileTypes, @Nullable List<File> fileArr) {
        Map<String, Object> mapValues = new HashMap<>();
//        mapValues.put("currentUserId ", currentUserId);//订单id
        mapValues.put("identifyFileByOcr", "");//订单id
        mapValues.put("fileType", fileTypes);//订单id
        Map<String, File> map = new HashMap<>();
        map.put("fileArr", fileArr.get(0));
        RequestBody requestBody = RequestBodyUtil.getRequestBodyValueAndFile(map, new RequestBodyBean(null, mapValues));
//        LogUtils.debugInfo("hxb--->", "上传参数：" + requestBody.());
        return mRepositoryManager.obtainRetrofitService(ModuleMeService.class).postUploadWlCarTeamFile
                (requestBody);
    }


    @Override
    public Observable<HttpResult> postAddWlCarTeam(@Nullable CarTeamDetailBean bean) {
        return mRepositoryManager.obtainRetrofitService(ModuleMeService.class).postAddWlCarTeam(bean);
    }

    @Override
    public Observable<HttpResult<CarTeamDetailBean>> getCarTeamDetail(@Nullable String carId) {
        return mRepositoryManager.obtainRetrofitService(ModuleMeService.class).getCarTeamDetail(carId);
    }

    @Override
    public Observable<HttpResult> postDeleteWlCarTeam(String guid) {
        return mRepositoryManager.obtainRetrofitService(ModuleMeService.class).postDeleteWlCarTeam(new SubmitDeleteCarBean(guid));
    }
}