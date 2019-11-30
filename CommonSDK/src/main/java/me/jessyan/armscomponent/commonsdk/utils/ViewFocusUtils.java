package me.jessyan.armscomponent.commonsdk.utils;

import android.view.View;

/**
 * @Author :hexingbo
 * @Date :2019/11/27
 * @FileName： ViewFocusUtils
 * @Describe :
 */
public class ViewFocusUtils {

    public static void setRequestFocus(View view){
        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
        view. requestFocus();
    }
}
