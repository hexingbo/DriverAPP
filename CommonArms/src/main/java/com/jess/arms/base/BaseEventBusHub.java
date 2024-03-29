package com.jess.arms.base;

/**
 * ================================================
 * AndroidEventBus 作为本方案提供的另一种跨组件通信方式 (第一种是接口下沉, 在 app 的 MainActivity 中已展示, 通过 ARouter 实现)
 * AndroidEventBus 比 greenrobot 的 EventBus 多了一个 Tag, 在组件化中更容定位和管理事件
 * <p>
 * EventBusHub 用来定义 AndroidEventBus 的 Tag 字符串, 以组件名作为 Tag 前缀, 对每个组件的事件进行分组
 * Tag 的命名规则为 组件名 + 页面名 + 动作
 * 比如需要使用 AndroidEventBus 通知订单组件的订单详情页进行刷新, 可以将这个刷新方法的 Tag 命名为 "order/OrderDetailActivity/refresh"
 * <p>
 * Tips: 基础库中的 EventBusHub 仅用来存放需要跨组件通信的事件的 Tag, 如果某个事件只想在组件内使用 AndroidEventBus 进行通信
 * 那就让组件自行管理这个事件的 Tag
 * <p>
 * ================================================
 */
public interface BaseEventBusHub {

    String TAG_LOGIN_SUCCESS = "loging/loging_success";
}
