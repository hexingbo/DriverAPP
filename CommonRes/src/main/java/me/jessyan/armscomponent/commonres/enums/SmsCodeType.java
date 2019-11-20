package me.jessyan.armscomponent.commonres.enums;

/**
 * @Author :hexingbo
 * @Date :2019/9/9
 * @FileName： SmsCodeType
 * @Describe : 1,注册验证码 ；2，登陆验证码 ；3，微信绑定时获取验证码；4找回密码；5修改登录密码
 */
public enum SmsCodeType {
    /**
     * 4：注册
     */
    SmsCodeType_Register,
    /**
     * 13、登陆验证码
     */
    SmsCodeType_Login,

    /**
     * 5：找回密码
     */
    SmsCodeType_FindLoginPwd,
    /**
     * 3：修改密码
     */
    SmsCodeType_UpdateLoginPwd,
}
