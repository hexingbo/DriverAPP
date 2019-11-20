package me.jessyan.armscomponent.commonres.constant;

/**
 * @Author :hexingbo
 * @Date :2019/10/31
 * @FileName： CommonHttpUrl
 * @Describe :存放所以网络请求的api
 */
public class CommonHttpUrl {

    public static final String API_postLoginUserApp = "/carDriver/login";//2、用户登录
    public static final String API_postRegisterUserApp = "/carDriver/userRegister";//3、用户注册
    public static final String API_postSendSmsCode = "/carDriver/sendMessage";// 4、发送短信
    public static final String API_postLoginOutUserApp = "/carDriver/loginout";// 5、退出登录
    public static final String API_getUserInfo = "/carDriver/personalInfo";// 6、查询个人中心用户信息
    public static final String API_getSeachCompanyJoinList = "/user/getLogisticsCompanyByName";// 7、根据公司名称查询物流公司
    public static final String API_getRecommendCompanyJoinList = "/user/getRecommendLogisticsCompany";// 8、查询推荐的物流公司
    public static final String API_getCompanyDetail = "/user/getCompanyDetail";// 9、查询物流公司详情
    public static final String API_postCompanyJoin = "/driver/join";// 10、司机申请加盟
    public static final String API_postCompanyJoinedList = "/driver/join/get";//  11、司机加盟列表
    public static final String API_postCompanyJoinedAction = "/driver/join/management";//  12、司机加盟管理操作
    public static final String API_getWayBillList = "/order/getOrders";//13、查询订单列表
    public static final String API_getWayBillDetail = "/order/detail";//14、查询订单详情
    public static final String API_postSaveFreightNo = "/order/saveFreightNo";//15、保存货运单号
    public static final String API_getMessageList = "/message/list";//16、查询消息列表
    public static final String API_getUpdateAppInfoList = "/tblWlAppUpdate/getPagelist";//17、查询版本更新列表
    public static final String API_getNewAppVersion = "/tblWlAppUpdate/getNewAppVersion";//18、根据条件查询最新版本更新详情
    public static final String API_postSaveOrUpdateAppVerDetail = "/tblWlAppUpdate/saveOrUpdateAppVerDetail";//19、保存或更新版本更新详情

    public static final String API_postDriverVerify = "/tblWlDriverVerifyHistory/driverCertification";//  20、司机认证
    public static final String API_getDriverVerifyDetail = "/carDriver/getCarDriverInfon";//  21、司机认证个人信息查询
    public static final String API_postSaveDriverVerifyInfo = "/carDriver/saveCarDriver";//  22、司机认证个人信息保存
    public static final String API_postSendWayBill = "/order/orderDelivery";//24、订单发货
    public static final String API_postDriverTransportPunch = "/tblWlTransport/driverTransportPunch";//25、司机运输打卡
    public static final String API_postWayBillReceipt = "/order/orderReceipt";//26、订单收货
    public static final String API_postUpdateMessageRead = "/message/updateMessageRead";//27、更新消息已读
    public static final String API_postUploadDriverInfoFile = "/verifyPicture/uploadFileList";//28、身份证、驾驶证附件上传和识别附件信息
    public static final String API_postUploadDriverHeadFile = "/verifyPicture/uploadFiles";//29、个人头像上传
    public static final String API_postFindPasswordAPP = "/carDriver/findPassword";//30、找回密码
    public static final String API_postUpdatePassword = "/carDriver/updatePassword";//31、修改密码
    public static final String API_postOrderSaveAccounts = "/order/saveAccounts";//32、生成对账单
    public static final String API_postOrderAccounts = "/order/getOrderAccounts";//33、对账单列表


}
