package com.lesso.module.me.mvp.presenter;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.body.ProgressInfo;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.AppManagerUtil;
import com.jess.arms.utils.DataHelper;
import com.jess.arms.utils.LogUtils;
import com.jess.arms.utils.RxLifecycleUtils;
import com.lesso.module.me.BuildConfig;
import com.lesso.module.me.mvp.contract.UserInfoContract;
import com.lesso.module.me.mvp.model.entity.DriverVerifyDetailBean;
import com.lesso.module.me.mvp.model.entity.UploadFileBean;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.armscomponent.commonres.constant.CommonHttpUrl;
import me.jessyan.armscomponent.commonres.enums.UploadFileUserCardType;
import me.jessyan.armscomponent.commonsdk.base.bean.HttpResult;

import com.jess.arms.http.observer.UploadObserver;
import com.jess.arms.http.throwable.HttpThrowable;
import me.jessyan.armscomponent.commonsdk.core.Constants;
import me.jessyan.armscomponent.commonsdk.core.RouterHub;
import me.jessyan.armscomponent.commonsdk.http.observer.MyHttpResultObserver;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;

import static com.lesso.module.me.mvp.model.api.Api.Module_Me_Doman_Url;


/**
 * ================================================
 * user：贺兴波
 * 2019/11/18 10:27
 * Description:
 * ================================================
 */
@ActivityScope
public class UserInfoPresenter extends BasePresenter<UserInfoContract.Model, UserInfoContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    private DriverVerifyDetailBean mDriverVerifyDetailBean;
    private UploadFileUserCardType fileTypes;
    private List<File> fileArr = new ArrayList<>();

    @Inject
    public UserInfoPresenter(UserInfoContract.Model model, UserInfoContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }

    /**
     * 权限请求
     */
    public void checkPermission(UploadFileUserCardType fileTypes) {
        this.fileTypes = fileTypes;
        fileArr.clear();
        if (mRootView.getRequestPermission() != null && mRootView.getRxPermissions() != null)
            mModel.checkPermission(mRootView.getRxPermissions(), mRootView.getRequestPermission(), mErrorHandler);

    }

    /**
     * 上传文件
     */
    public void postUploadFile(File file) {
        if (file == null) {
            mRootView.showMessage("请选择你要上传的头像");
            return;
        }
        mRootView.setImageViewPicture(file.getPath(), fileTypes);
        UploadObserver observer = new UploadObserver<HttpResult<UploadFileBean>>
                (fileTypes == UploadFileUserCardType.HeadPhoto ? (Module_Me_Doman_Url + CommonHttpUrl.API_postUploadDriverHeadFile)
                        : Module_Me_Doman_Url + CommonHttpUrl.API_postUploadDriverInfoFile) {

            @Override
            public void onProgress(ProgressInfo progressInfo) {
                LogUtils.debugInfo("上传进度：" + progressInfo.getCurrentbytes() + "%");
            }

            @Override
            public void onResult(HttpResult<UploadFileBean> result) {
                mRootView.showMessage("上传成功");
                if (mDriverVerifyDetailBean != null) {
                    mDriverVerifyDetailBean.setHeadSrc(result.getData().getHeadPath());
                }
            }

            @Override
            public void onError(long progressInfoId, HttpThrowable throwable) {
                mRootView.showMessage(throwable.message);
                mRootView.hideLoading();//隐藏下拉刷新的进度条
            }

        };

        if (fileTypes == UploadFileUserCardType.HeadPhoto) {
            postUploadDriverHeadFile(file, observer);
        } else {
            fileArr.add(file);
            postUploadDriverInfoFile(fileArr, observer);
        }

    }

    /**
     * 上传头像
     *
     * @param personFile
     * @param mUploadObserver
     */
    private void postUploadDriverHeadFile(File personFile, UploadObserver mUploadObserver) {

        mModel.postUploadDriverHeadFile(DataHelper.getStringSF(AppManagerUtil.getCurrentActivity(), Constants.SP_USER_ID), personFile)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(
                        //遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                        BuildConfig.HTTP_MaxRetries, BuildConfig.HTTP_RetryDelaySecond))
                .doOnSubscribe(disposable -> {
                    mRootView.showLoading();//显示下拉刷新的进度条
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.hideLoading();//隐藏下拉刷新的进度条
                })
                //使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(mUploadObserver);

    }

    /**
     * 上传证件照
     *
     * @param fileArr
     * @param mUploadObserver
     */
    private void postUploadDriverInfoFile(List<File> fileArr, UploadObserver mUploadObserver) {
        mModel.postUploadDriverInfoFile(fileTypes, fileArr)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(
                        //遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                        BuildConfig.HTTP_MaxRetries, BuildConfig.HTTP_RetryDelaySecond))
                .doOnSubscribe(disposable -> {
                    mRootView.showLoading();//显示下拉刷新的进度条
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.hideLoading();//隐藏下拉刷新的进度条
                })
                //使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(mUploadObserver);
    }


    /**
     * 获取用户信息
     */
    public void getDriverVerifyDetail() {
        mModel.getDriverVerifyDetail(
                DataHelper.getStringSF(AppManagerUtil.getCurrentActivity(), Constants.SP_USER_ID))
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(
                        //遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                        BuildConfig.HTTP_MaxRetries, BuildConfig.HTTP_RetryDelaySecond))
                .doOnSubscribe(disposable -> {
                    mRootView.showLoading();//显示下拉刷新的进度条
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.hideLoading();//隐藏下拉刷新的进度条
                })
                //使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new MyHttpResultObserver<HttpResult<DriverVerifyDetailBean>>(mErrorHandler) {
                    @Override
                    public void onResult(HttpResult<DriverVerifyDetailBean> result) {
                        mDriverVerifyDetailBean = result.getData();
                        mRootView.setDriverVerifyDetailBean(mDriverVerifyDetailBean);
                    }

                });

    }

    /**
     * 选择图片
     */
    public void getPictureSelector() {
        PictureSelector.create(mRootView.getActivity())
                .openGallery(PictureMimeType.ofImage())
                .forResult(PictureConfig.CHOOSE_REQUEST);
//        // 进入相册 以下是例子：不需要的api可以不写
//        PictureSelector.create(mRootView.getActivity())
//                .openGallery(PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()
////                .theme(me.jessyan.armscomponent.commonres.R.style.picture_default_style)//主题样式(不设置为默认样式) 也可参考demo values/styles下 例如：R.style.picture.white.style
//                .maxSelectNum(1)// 最大图片选择数量 int
//                .minSelectNum(1)// 最小选择数量 int
//                .imageSpanCount(4)// 每行显示个数 int
//                .selectionMode(PictureConfig.SINGLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
//                .previewImage(true)// 是否可预览图片 true or false
//                .previewVideo(false)// 是否可预览视频 true or false
//                .enablePreviewAudio(false) // 是否可播放音频  true or false
////                .compressGrade()// luban压缩档次，默认3档 Luban.THIRD_GEAR、Luban.FIRST_GEAR、Luban.CUSTOM_GEAR
//                .isCamera(true)// 是否显示拍照按钮 ture or false
//                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
//                .sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
//                .setOutputCameraPath("/HuoManyYun/App")// 自定义拍照保存路径,可不填
//                .compress(true)// 是否压缩 true or false
//                .compressMode(PictureConfig.SYSTEM_COMPRESS_MODE)//系统自带 or 鲁班压缩 PictureConfig.SYSTEM_COMPRESS_MODE or LUBAN_COMPRESS_MODE
////                .glideOverride()// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度 int
//                .isGif(false)// 是否显示gif图片 true or false
//                .openClickSound(false)// 是否开启点击声音 true or false
//                .selectionMedia(null)// 是否传入已选图片 List<LocalMedia> list
//                .previewEggs(true)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false
////                .compressMaxKB()//压缩最大值kb compressGrade()为Luban.CUSTOM_GEAR有效 int
////                .compressWH() // 压缩宽高比 compressGrade()为Luban.CUSTOM_GEAR有效 int
////                .videoQuality()// 视频录制质量 0 or 1 int
////                .videoSecond()//显示多少秒以内的视频or音频也可适用 int
////                .recordVideoSecond()//录制视频秒数 默认60s int
//                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
    }
}
