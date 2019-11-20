/*
 * Copyright 2018 JessYan
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
package me.jessyan.armscomponent.commonsdk.core;

/**
 * =============================================
 * 作    者：贺兴波
 * 时    间：2019/8/2
 * 描    述：CommonSDK 的 Constants 可以定义公用的常量,
 * 比如关于业务的常量或者正则表达式,
 * 每个组件的 Constants 可以定义组件自己的私有常量
 * =============================================
 */
public interface Constants {

    String SP_LOGIN_USER = "sp_login_user";//登录时保存的账号
    String SP_TOKEN = "token";
    String SP_USER_ID = "user_id";//用户id
    String SP_VERIFY_STATUS = "verifyStatus";
    String SP_ACCOUNT = "account";
    String SP_DEVICE_ID = "device_id";//设备号
    String SP_VERIFY_COUNT = "verify_count";

}
