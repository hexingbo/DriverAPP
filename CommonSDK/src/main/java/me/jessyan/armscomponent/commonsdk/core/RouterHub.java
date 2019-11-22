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

import com.alibaba.android.arouter.launcher.ARouter;
import com.jess.arms.base.MyHttpResult;
import com.jess.arms.utils.AppManagerUtil;
import com.jess.arms.utils.LogUtils;

import me.jessyan.armscomponent.commonsdk.utils.SaveOrClearUserInfo;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

/**
 * ================================================
 * RouterHub 用来定义路由器的路由地址, 以组件名作为前缀, 对每个组件的路由地址进行分组, 可以统一查看和管理所有分组的路由地址
 * <p>
 * RouterHub 存在于基础库, 可以被看作是所有组件都需要遵守的通讯协议, 里面不仅可以放路由地址常量, 还可以放跨组件传递数据时命名的各种 Key 值
 * 再配以适当注释, 任何组件开发人员不需要事先沟通只要依赖了这个协议, 就知道了各自该怎样协同工作, 既提高了效率又降低了出错风险, 约定的东西自然要比口头上说强
 * <p>
 * 如果您觉得把每个路由地址都写在基础库的 RouterHub 中, 太麻烦了, 也可以在每个组件内部建立一个私有 RouterHub, 将不需要跨组件的
 * 路由地址放入私有 RouterHub 中管理, 只将需要跨组件的路由地址放入基础库的公有 RouterHub 中管理, 如果您不需要集中管理所有路由地址的话
 * 这也是比较推荐的一种方式
 * <p>
 * 路由地址的命名规则为 组件名 + 页面名, 如订单组件的订单详情页的路由地址可以命名为 "/order/OrderDetailActivity"
 * <p>
 * ARouter 将路由地址中第一个 '/' 后面的字符称为 Group, 比如上面的示例路由地址中 order 就是 Group, 以 order 开头的地址都被分配该 Group 下
 * 切记不同的组件中不能出现名称一样的 Group, 否则会发生该 Group 下的部分路由地址找不到的情况!!!
 * 所以每个组件使用自己的组件名作为 Group 是比较好的选择, 毕竟组件不会重名
 * ================================================
 */
public interface RouterHub {
    /**
     * 组名
     */
    String APP = "/app";//宿主 App 组件
    String Me = "/me";//我的组件
    String Loging = "/loging";//登录组件
    String Message = "/message";//消息组件
    String Waybill = "/waybill";//运单组件
    String Map = "/map";//地图

    /**
     * 服务组件, 用于给每个组件暴露特有的服务
     */
    String SERVICE = "/service";


    /**
     * 宿主 App 分组
     */
    String APP_WelcomeActivity = APP + "/WelcomeActivity";
    String APP_MainStartActivity = APP + "/MainStartActivity";


    //登录
    String Loging_MainLoginActivity = Loging + "/MainLoginActivity";
    String Loging_RegisterUserActivity = Loging + "/RegisterUserActivity";
    String Loging_FindPasswordActivity = Loging + "/FindPasswordActivity";
    String Loging_UpdatePwdActivityActivity = Loging + "/UpdatePwdActivityActivity";

    //我的
    String Me_Service_MyViewService = Me + SERVICE + "/MyViewService";
    String Me_MeMainActivity = Me + "/MeMainActivity";
    String Me_MainMyFragment = Me + "/MainMyActivity";
    String Me_UserAuthenticationActivity = Me + "/UserAuthenticationActivity";

    //消息
    String Message_Service_MessageViewService = Message + SERVICE + "/MessageViewService";
    String Message_MainMessageActivity = Message + "/MainMessageActivity";
    String Message_MessageManagerFragment = Message + "/MessageManagerFragment";

    //运单
    String Waybill_Service_WayBillViewService = Waybill + SERVICE + "/WayBillViewService";
    String Waybill_MainWayBillActivity = Waybill + "/MainWayBillActivity";
    String Waybill_WayBillManagerFragment = Waybill + "/WayBillManagerFragment";
    String Waybill_WayBillDetailActivity = Waybill + "/WayBillDetailActivity";
    String Waybill_OrderAccountsManagerActivity = Waybill + "/OrderAccountsManagerActivity";



}
