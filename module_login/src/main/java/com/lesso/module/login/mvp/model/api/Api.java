package com.lesso.module.login.mvp.model.api;

import com.lesso.module.login.BuildConfig;

/**
 * ================================================
 * 存放一些与 API 有关的东西,如请求地址,请求码等
 * <p>
 * Created by ArmsComponentTemplate on 11/13/2019 20:27
 * ================================================
 */
public interface Api {
    String Module_Login_Doman_Name= "module_login";
    String Module_Login_Doman_Url = BuildConfig.BASE_URL+"/hmy-appdriver";
}
