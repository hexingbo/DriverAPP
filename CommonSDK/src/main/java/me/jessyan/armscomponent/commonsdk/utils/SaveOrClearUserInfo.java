package me.jessyan.armscomponent.commonsdk.utils;


import com.jess.arms.utils.AppManagerUtil;
import com.jess.arms.utils.DataHelper;
import com.jess.arms.utils.DeviceUtils;

import me.jessyan.armscomponent.commonsdk.core.Constants;

/**
 * @Author :hexingbo
 * @Date :2019/11/15
 * @FileName： SaveOrClearUserInfo
 * @Describe :
 */
public class SaveOrClearUserInfo {

    public static void saveUserInfo( String token, String userId, String verifyStatus, String account,String deviceId) {
        DataHelper.setStringSF(AppManagerUtil.appContext(), Constants.SP_TOKEN, token);
        DataHelper.setStringSF(AppManagerUtil.appContext(), Constants.SP_USER_ID, userId);
        DataHelper.setStringSF(AppManagerUtil.appContext(), Constants.SP_VERIFY_STATUS, verifyStatus);
        DataHelper.setStringSF(AppManagerUtil.appContext(), Constants.SP_ACCOUNT, account);
        DataHelper.setStringSF(AppManagerUtil.appContext(), Constants.SP_DEVICE_ID,deviceId);
    }

    public static void clearUserInfo() {
        DataHelper.removeSF(AppManagerUtil.appContext(), Constants.SP_TOKEN);
        DataHelper.removeSF(AppManagerUtil.appContext(), Constants.SP_USER_ID);
        DataHelper.removeSF(AppManagerUtil.appContext(), Constants.SP_VERIFY_STATUS);
        DataHelper.removeSF(AppManagerUtil.appContext(), Constants.SP_ACCOUNT);
    }
}
