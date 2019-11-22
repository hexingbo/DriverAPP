package me.jessyan.armscomponent.commonsdk.base.app;

import android.content.Context;

import com.amap.api.location.AMapLocationClient;
import com.jess.arms.base.BaseApplication;
import com.previewlibrary.ZoomMediaLoader;

import me.jessyan.armscomponent.commonsdk.imgaEngine.GroupPhotoImageLoader;

/**
 * @Author :hexingbo
 * @Date :2019/10/6
 * @FileName： MyBaseApplication
 * @Describe :
 */
public class MyBaseApplication extends BaseApplication {

    public static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }
}
