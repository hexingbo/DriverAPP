package me.jessyan.armscomponent.commonsdk.utils;

import android.os.Environment;

import com.jess.arms.utils.AppManagerUtil;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;

import java.io.File;

/**
 * @Author :hexingbo
 * @Date :2019/11/21
 * @FileName： PictureSelectorUtils
 * @Describe :
 */
public class PictureSelectorUtils {
    /**
     * 选择图片或拍照
     *
     * @param previewImage   是否可以预览
     * @param enableCrop     是否可以裁剪
     * @param aspect_ratio_x 裁剪的x比列  如16:9 3:2 3:4 1:1 可自定义
     * @param aspect_ratio_y 裁剪的y比列  如16:9 3:2 3:4 1:1 可自定义
     */
    public static void postPictureSelector(boolean previewImage,
                                    boolean enableCrop, int aspect_ratio_x, int aspect_ratio_y) {
        // 进入相册 以下是例子：用不到的api可以不写
        PictureSelector.create(AppManagerUtil.getCurrentActivity())
                .openGallery(PictureMimeType.ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
//                .theme()//主题样式(不设置为默认样式) 也可参考demo values/styles下 例如：R.style.picture.white.style
                .maxSelectNum(1)// 最大图片选择数量 int
                .minSelectNum(1)// 最小选择数量 int
                .imageSpanCount(4)// 每行显示个数 int
                .selectionMode(PictureConfig.SINGLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                .previewImage(previewImage)// 是否可预览图片 true or false
                .previewVideo(false)// 是否可预览视频 true or false
                .enablePreviewAudio(false) // 是否可播放音频 true or false
                .isCamera(true)// 是否显示拍照按钮 true or false
                .imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                .sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                .setOutputCameraPath("/CustomPath")// 自定义拍照保存路径,可不填
                .enableCrop(enableCrop)// 是否裁剪 true or false
                .compress(true)// 是否压缩 true or false
                .glideOverride(200, 200)// int glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                .withAspectRatio(aspect_ratio_x <= 0 ? 1 : aspect_ratio_x, aspect_ratio_y <= 0 ? 1 : aspect_ratio_y)// int 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                .hideBottomControls(false)// 是否显示uCrop工具栏，默认不显示 true or false
                .isGif(false)// 是否显示gif图片 true or false
                .compressSavePath(getPath())//压缩图片保存地址
                .freeStyleCropEnabled(false)// 裁剪框是否可拖拽 true or false
                .circleDimmedLayer(false)// 是否圆形裁剪 true or false
                .showCropFrame(true)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
                .showCropGrid(true)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false
                .openClickSound(false)// 是否开启点击声音 true or false
//                .selectionMedia()// 是否传入已选图片 List<LocalMedia> list
                .previewEggs(true)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false
//                .cropCompressQuality(100)// 裁剪压缩质量 默认90 int
                .minimumCompressSize(100)// 小于100kb的图片不压缩
                .synOrAsy(true)//同步true或异步false 压缩 默认同步
//                .cropWH(widthCrop <= 0 ? 1 : widthCrop, heightCrop <= 0 ? 1 : heightCrop)// 裁剪宽高比，设置如果大于图片本身宽高则无效 int
                .rotateEnabled(false) // 裁剪是否可旋转图片 true or false
                .scaleEnabled(false)// 裁剪是否可放大缩小图片 true or false
                .videoQuality(1)// 视频录制质量 0 or 1 int
                .videoMaxSecond(15)// 显示多少秒以内的视频or音频也可适用 int
                .videoMinSecond(10)// 显示多少秒以内的视频or音频也可适用 int
                .recordVideoSecond(60)//视频秒数录制 默认60s int
                .isDragFrame(true)// 是否可拖动裁剪框(固定)
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
    }
    /**
     * 自定义压缩存储地址
     *
     * @return
     */
    private static String getPath() {
        String path = Environment.getExternalStorageDirectory() + "/hsj/image/";
        File file = new File(path);
        if (file.mkdirs()) {
            return path;
        }
        return path;
    }


}
