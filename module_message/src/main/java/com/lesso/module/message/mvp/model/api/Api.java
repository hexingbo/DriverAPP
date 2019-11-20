package com.lesso.module.message.mvp.model.api;

import com.lesso.module.message.BuildConfig;

/**
 * ================================================
 * 存放一些与 API 有关的东西,如请求地址,请求码等
 * <p>
 * Created by ArmsComponentTemplate on 11/13/2019 16:10
 * ================================================
 */
public interface Api {

    String Module_Message_Doman_Name= "module_message";
    String Module_Message_Doman_Url = BuildConfig.BASE_URL+"/hmy-appdriver";
}
