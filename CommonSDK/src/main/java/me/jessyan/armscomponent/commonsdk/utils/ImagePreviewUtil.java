package me.jessyan.armscomponent.commonsdk.utils;

import android.app.Activity;

import com.jess.arms.utils.ArmsUtils;
import com.previewlibrary.GPreviewBuilder;

import java.util.List;

import me.jessyan.armscomponent.commonsdk.base.bean.ImageLookBean;

/**
 * @Author :hexingbo
 * @Date :2019/11/20
 * @FileName： ImagePreviewUtil
 * @Describe :
 */
public class ImagePreviewUtil {

    public static void lookImgs(Activity activity, List<ImageLookBean> listImg, int selecteIndex) {
        if (!ArmsUtils.isEmpty(listImg)) {
            GPreviewBuilder.from(activity)//activity实例必须
                    .setData(listImg)//集合
                    .setCurrentIndex(selecteIndex)
                    .setSingleFling(true)//是否在黑屏区域点击返回
                    .setDrag(false)//是否禁用图片拖拽返回
                    .setType(GPreviewBuilder.IndicatorType.Dot)//指示器类型
                    .start();//启动
        } else {
            ArmsUtils.snackbarText("查看图片获取失败");
        }
    }

}
