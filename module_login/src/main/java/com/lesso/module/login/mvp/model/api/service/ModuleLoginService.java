/*
 * Copyright 2017 JessYan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.lesso.module.login.mvp.model.api.service;


import com.lesso.module.login.mvp.model.api.Api;
import com.lesso.module.login.mvp.model.entity.LoginResultBean;
import com.lesso.module.login.mvp.model.entity.SendSmsCodeBean;
import com.lesso.module.login.mvp.model.entity.SubmitFindPwdBean;
import com.lesso.module.login.mvp.model.entity.SubmitLoginBean;
import com.lesso.module.login.mvp.model.entity.SubmitRegisterBean;
import com.lesso.module.login.mvp.model.entity.SubmitUpdatePwdBean;

import io.reactivex.Observable;
import me.jessyan.armscomponent.commonres.constant.CommonHttpUrl;
import me.jessyan.armscomponent.commonsdk.base.bean.HttpResult;
import retrofit2.Retrofit;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

import static me.jessyan.retrofiturlmanager.RetrofitUrlManager.DOMAIN_NAME_HEADER;

/**
 * ================================================
 * 展示 {@link Retrofit#create(Class)} 中需要传入的 ApiService 的使用方式
 * 存放关于 zhihu 的一些 API
 * ================================================
 */
public interface ModuleLoginService {

    /**
     * 4、获取短信验证码
     */
//    @FormUrlEncoded
    @Headers({DOMAIN_NAME_HEADER + Api.Module_Login_Doman_Name})
    @POST(CommonHttpUrl.API_postSendSmsCode)
    Observable<HttpResult> postSendSmsCode(@Body SendSmsCodeBean bean);

    /**
     * 2、用户登录
     *
     * @return
     */
    @Headers({DOMAIN_NAME_HEADER + Api.Module_Login_Doman_Name})
    @POST(CommonHttpUrl.API_postLoginUserApp)
    Observable<HttpResult<LoginResultBean>> postLoginUserApp(@Body SubmitLoginBean bean);

    /**
     * 3、用户注册
     *
     * @return
     */
    @Headers({DOMAIN_NAME_HEADER + Api.Module_Login_Doman_Name})
    @POST(CommonHttpUrl.API_postRegisterUserApp)
    Observable<HttpResult<LoginResultBean>> postRegisterUserApp(@Body SubmitRegisterBean bean);

    /**
     * 30、找回密码
     *
     * @return
     */
    @Headers({DOMAIN_NAME_HEADER + Api.Module_Login_Doman_Name})
    @POST(CommonHttpUrl.API_postFindPasswordAPP)
    Observable<HttpResult> postFindPasswordApp(@Body SubmitFindPwdBean bean);

    /**
     * 31、修改密码
     *
     * @return
     */
    @Headers({DOMAIN_NAME_HEADER + Api.Module_Login_Doman_Name})
    @POST(CommonHttpUrl.API_postUpdatePassword)
    Observable<HttpResult> postUpdatePassword(@Body SubmitUpdatePwdBean bean);


}
