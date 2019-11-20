package com.lesso.module.message.mvp.model.api.service;


import com.jess.arms.di.scope.FragmentScope;
import com.lesso.module.message.mvp.model.api.Api;
import com.lesso.module.message.mvp.model.entity.MessageBean;
import com.lesso.module.message.mvp.model.entity.SubmitGetMessageListBean;

import java.util.List;

import io.reactivex.Observable;
import me.jessyan.armscomponent.commonres.constant.CommonHttpUrl;
import me.jessyan.armscomponent.commonsdk.base.bean.HttpResult;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

import static me.jessyan.retrofiturlmanager.RetrofitUrlManager.DOMAIN_NAME_HEADER;

/**
 * @Author :hexingbo
 * @Date :2019/10/4
 * @FileName： ModuleMeService
 * @Describe :
 */
public interface ModuleMessageService {

    /**
     * 16、查询消息列表
     */
    @Headers({DOMAIN_NAME_HEADER + Api.Module_Message_Doman_Name})
    @POST(CommonHttpUrl.API_getMessageList)
    Observable<HttpResult<List<MessageBean>>> getMessageList(@Body SubmitGetMessageListBean bean);

    /**
     * 27、更新消息已读
     */
    @FragmentScope
    @Headers({DOMAIN_NAME_HEADER + Api.Module_Message_Doman_Name})
    @POST(CommonHttpUrl.API_postUpdateMessageRead)
    Observable<HttpResult> postUpdateMessageRead(@Field("userMsgId") String userMsgId);


}
