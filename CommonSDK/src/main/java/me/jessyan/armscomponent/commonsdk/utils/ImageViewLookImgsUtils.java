package me.jessyan.armscomponent.commonsdk.utils;

import android.app.Activity;

import com.jess.arms.utils.ArmsUtils;
import com.previewlibrary.GPreviewBuilder;
import com.previewlibrary.ZoomMediaLoader;

import java.util.ArrayList;
import java.util.List;

import me.jessyan.armscomponent.commonsdk.base.bean.ImageLookBean;
import me.jessyan.armscomponent.commonsdk.imgaEngine.GroupPhotoImageLoader;

/**
 * @Author :hexingbo
 * @Date :2019/11/22
 * @FileName： ImageViewLookImgsUtils
 * @Describe :
 */
public class ImageViewLookImgsUtils {

    private List<ImageLookBean> listImg = new ArrayList<>();
    private static ImageViewLookImgsUtils imgsUtils;

    public static ImageViewLookImgsUtils init() {
        if (imgsUtils == null) {
            imgsUtils = new ImageViewLookImgsUtils();
            //初始化图片缩放预览
            ZoomMediaLoader.getInstance().init(new GroupPhotoImageLoader());
        }
        return imgsUtils;
    }


    /**
     * 图片预览
     *
     * @param activity
     * @param urls
     * @param selecteIndex
     */
    public void lookImgs(Activity activity, List<String> urls, int selecteIndex) {
        if (!ArmsUtils.isEmpty(urls)) {
            listImg.clear();
            for (String url : urls) {
                listImg.add(new ImageLookBean(url));
            }
            GPreviewBuilder.from(activity)//activity实例必须
                    .setData(listImg)//集合
                    .setCurrentIndex(selecteIndex)
                    .setSingleFling(true)//是否在黑屏区域点击返回
                    .setDrag(false)//是否禁用图片拖拽返回
                    .setType(GPreviewBuilder.IndicatorType.Dot)//指示器类型
                    .start();//启动
        } else {
            ArmsUtils.snackbarText("预览图片失败");
        }
    }

    /**
     * 图片预览
     *
     * @param activity
     * @param url
     */
    public void lookImgs(Activity activity, String url) {
        if (!ArmsUtils.isEmpty(url)) {
            listImg.clear();
            listImg.add(new ImageLookBean(url));
            GPreviewBuilder.from(activity)//activity实例必须
                    .setData(listImg)//集合
                    .setCurrentIndex(0)
                    .setSingleFling(true)//是否在黑屏区域点击返回
                    .setDrag(false)//是否禁用图片拖拽返回
                    .setType(GPreviewBuilder.IndicatorType.Dot)//指示器类型
                    .start();//启动
        } else {
            ArmsUtils.snackbarText("预览图片失败");
        }
    }
}
