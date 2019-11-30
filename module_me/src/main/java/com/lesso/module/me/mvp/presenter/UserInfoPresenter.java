package com.lesso.module.me.mvp.presenter;

import android.app.Application;
import android.widget.ImageView;

import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.jess.arms.base.MessageEvent;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.body.ProgressInfo;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.http.observer.UploadObserver;
import com.jess.arms.http.throwable.HttpThrowable;
import com.jess.arms.integration.AppManager;
import com.jess.arms.integration.EventBusManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.AppManagerUtil;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.DataHelper;
import com.jess.arms.utils.LogUtils;
import com.jess.arms.utils.PermissionUtil;
import com.jess.arms.utils.RxLifecycleUtils;
import com.lesso.module.me.BuildConfig;
import com.lesso.module.me.R;
import com.lesso.module.me.mvp.contract.UserInfoContract;
import com.lesso.module.me.mvp.model.entity.DriverVerifyDetailBean;
import com.lesso.module.me.mvp.model.entity.UploadCardFileResultBean;
import com.lesso.module.me.mvp.model.entity.UploadHeadFileResultBean;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.armscomponent.commonres.constant.CommonHttpUrl;
import me.jessyan.armscomponent.commonres.enums.UploadFileUserCardType;
import me.jessyan.armscomponent.commonsdk.base.bean.HttpResult;
import me.jessyan.armscomponent.commonsdk.core.Constants;
import me.jessyan.armscomponent.commonsdk.core.EventBusHub;
import me.jessyan.armscomponent.commonsdk.http.observer.MyHttpResultObserver;
import me.jessyan.armscomponent.commonsdk.imgaEngine.config.CommonImageConfigImpl;
import me.jessyan.armscomponent.commonsdk.utils.ImageViewLookImgsUtils;
import me.jessyan.armscomponent.commonsdk.utils.PictureSelectorUtils;
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

    public DriverVerifyDetailBean getDriverVerifyDetailBean() {
        if (mDriverVerifyDetailBean == null) {
            mDriverVerifyDetailBean = new DriverVerifyDetailBean();
        }
        return mDriverVerifyDetailBean;
    }

    @Inject
    public UserInfoPresenter(UserInfoContract.Model model, UserInfoContract.View rootView) {
        super(model, rootView);
        fileArr.clear();
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
        if (mRootView.getRxPermissions() != null)
            PermissionUtil.launchCameraAndExternalStorage(new PermissionUtil.RequestPermission() {
                @Override
                public void onRequestPermissionSuccess() {
                    //request permission success, do something.
                    getPictureSelector();
                }

                @Override
                public void onRequestPermissionFailure(List<String> permissions) {
                    mRootView.showMessage("Request permissions failure");
                }

                @Override
                public void onRequestPermissionFailureWithAskNeverAgain(List<String> permissions) {
                    mRootView.showMessage("Need to go to the settings");
                }
            }, mRootView.getRxPermissions(), mErrorHandler);

    }

    /**
     * 上传文件
     */
    public void postUploadFile(File file) {
        fileArr.clear();
        if (file == null) {
            mRootView.showMessage("请选择你要上传的文件");
            return;
        }
        mRootView.setImageViewPicture(file.getPath(), fileTypes, getDriverVerifyDetailBean());
        if (fileTypes == UploadFileUserCardType.HeadPhoto) {
            postUploadDriverHeadFile(file);
        } else {
            fileArr.add(file);
            LogUtils.warnInfo("hxb--->", "上传文件个数：" + fileArr.size());
            postUploadDriverInfoFile(fileArr, fileTypes);
        }

    }

    /**
     * 上传头像
     *
     * @param personFile
     */
    private void postUploadDriverHeadFile(File personFile) {
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
                .subscribe(new UploadObserver<HttpResult<UploadHeadFileResultBean>>
                        (Module_Me_Doman_Url + CommonHttpUrl.API_postUploadDriverHeadFile) {

                    @Override
                    public void onProgress(ProgressInfo progressInfo) {
                        LogUtils.debugInfo("上传进度：" + progressInfo.getCurrentbytes() + "%");
                    }

                    @Override
                    public void onResult(HttpResult<UploadHeadFileResultBean> result) {
                        mRootView.showMessage("上传成功");
                        getDriverVerifyDetailBean().setHeadSrc(result.getData().getHeadPath());
                    }

                    @Override
                    public void onError(long progressInfoId, HttpThrowable throwable) {
                        EventBusManager.getInstance().post(new MessageEvent(EventBusHub.Message_UpdateUserInfo));
                        mRootView.showMessage(throwable.message);
                        mRootView.hideLoading();//隐藏下拉刷新的进度条
                    }

                });

    }

    /**
     * 上传证件照
     *
     * @param fileArr
     * @param fileTypes
     */
    private void postUploadDriverInfoFile(List<File> fileArr, UploadFileUserCardType fileTypes) {
        mModel.postUploadDriverInfoFile(DataHelper.getStringSF(AppManagerUtil.getCurrentActivity(), Constants.SP_USER_ID), fileTypes, fileArr)
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
                .subscribe(new UploadObserver<HttpResult<UploadCardFileResultBean>>
                        (Module_Me_Doman_Url + CommonHttpUrl.API_postUploadDriverInfoFile) {

                    @Override
                    public void onProgress(ProgressInfo progressInfo) {
                        LogUtils.debugInfo("上传进度：" + progressInfo.getCurrentbytes() + "%");
                    }

                    @Override
                    public void onResult(HttpResult<UploadCardFileResultBean> result) {
                        mRootView.showMessage("上传成功");
                        if (!ArmsUtils.isEmpty(result.getData()))
                            switch (fileTypes) {
                                case IdCard:
                                    //设置身份证号码
                                    if (!ArmsUtils.isEmpty(result.getData().getFileTextInfo())
                                            && !ArmsUtils.isEmpty(result.getData().getFileTextInfo().getIdCard())
                                            && result.getData().getFileTextInfo().getIdCard().isSuccess())
                                        mRootView.setUserCardNumber(result.getData().getFileTextInfo().getIdCard().getData());

                                    if (!ArmsUtils.isEmpty(result.getData().getFilePathInfo()))
                                        getDriverVerifyDetailBean().setIdCardPath(result.getData().getFilePathInfo().getIdCard());
                                    break;
                                case IdCardBack:
                                    if (!ArmsUtils.isEmpty(result.getData().getFilePathInfo()))
                                        getDriverVerifyDetailBean().setIdCardBackPath(result.getData().getFilePathInfo().getIdCardBack());
                                    break;
                                case LifePhoto:
                                    if (!ArmsUtils.isEmpty(result.getData().getFilePathInfo()))
                                        getDriverVerifyDetailBean().setLifePhotoPath(result.getData().getFilePathInfo().getLifePhoto());
                                    break;
                                case DriverCard:
                                    //设置驾驶证号码
                                    if (!ArmsUtils.isEmpty(result.getData().getFileTextInfo()) &&
                                            !ArmsUtils.isEmpty(result.getData().getFileTextInfo().getDriverCardBack()) &&
                                            result.getData().getFileTextInfo().getDriverCardBack().isSuccess())
                                        mRootView.setDriverCardNumber(result.getData().getFileTextInfo().getDriverCardBack().getData());

                                    if (!ArmsUtils.isEmpty(result.getData().getFilePathInfo()))
                                        getDriverVerifyDetailBean().setDriverCardPath(result.getData().getFilePathInfo().getDriverCard());
                                    break;
                                case DriverCardBack:
                                    if (!ArmsUtils.isEmpty(result.getData().getFilePathInfo()))
                                        getDriverVerifyDetailBean().setDriverCardBackPath(result.getData().getFilePathInfo().getDriverCardBack());
                                    break;
                            }

                    }

                    @Override
                    public void onError(long progressInfoId, HttpThrowable throwable) {
                        mRootView.showMessage(throwable.message);
                        mRootView.hideLoading();//隐藏下拉刷新的进度条
                    }

                });
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
     * 保存用户信息
     */
    public void postSaveDriverVerifyInfo(String userName, String userCard, String driverCard) {
        if (ArmsUtils.isEmpty(userName)) {
            mRootView.showMessage("请输入姓名");
            return;
        }
        if (ArmsUtils.isEmpty(userCard)) {
            mRootView.showMessage("请输入身份证号");
            return;
        }
        if (ArmsUtils.isEmpty(driverCard)) {
            mRootView.showMessage("请输入驾驶证号");
            return;
        }

        if (ArmsUtils.isEmpty(getDriverVerifyDetailBean().getHeadSrc())) {
            mRootView.showMessage("请上传头像");
            return;
        }
        if (ArmsUtils.isEmpty(getDriverVerifyDetailBean().getIdCardPath())) {
            mRootView.showMessage("请上传身份证正面照");
            return;
        }
        if (ArmsUtils.isEmpty(getDriverVerifyDetailBean().getIdCardBackPath())) {
            mRootView.showMessage("请上传身份证背面照");
            return;
        }
        if (ArmsUtils.isEmpty(getDriverVerifyDetailBean().getLifePhotoPath())) {
            mRootView.showMessage("请上传手持身份证照");
            return;
        }
        if (ArmsUtils.isEmpty(getDriverVerifyDetailBean().getDriverCardPath())) {
            mRootView.showMessage("请上传驾驶证主页照");
            return;
        }
        if (ArmsUtils.isEmpty(getDriverVerifyDetailBean().getDriverCardBackPath())) {
            mRootView.showMessage("请上传驾驶证副页照");
            return;
        }
        getDriverVerifyDetailBean().setDriverBy(userName);
        getDriverVerifyDetailBean().setIdno(userCard);
        getDriverVerifyDetailBean().setDriverno(driverCard);
        mModel.postSaveDriverVerifyInfo(getDriverVerifyDetailBean())
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
                .subscribe(new MyHttpResultObserver<HttpResult>(mErrorHandler) {
                    @Override
                    public void onResult(HttpResult result) {
                        ArmsUtils.makeText(mApplication, mApplication.getResources().getString(R.string.module_me_user_submit_succeed));
                        EventBusManager.getInstance().post(new MessageEvent(EventBusHub.Message_UpdateUserInfo));
                        AppManagerUtil.getCurrentActivity().finish();
                    }

                });

    }

    /**
     * 选择图片
     */
    public void getPictureSelector() {
        PictureSelectorUtils.postPictureSelector(true, true,
                fileTypes == UploadFileUserCardType.HeadPhoto ? 1 : 3, fileTypes == UploadFileUserCardType.HeadPhoto ? 1 : 2);
    }

    public void setImageViewHeadPicture(String url, ImageView view) {
        LogUtils.debugInfo("hxb--->", url);
        if (!ArmsUtils.isEmpty(url))
            mImageLoader.loadImage(mApplication, CommonImageConfigImpl
                    .builder()
                    .url(url)
                    .errorPic(R.mipmap.ic_head_default)
                    .placeholder(R.mipmap.ic_head_default)
                    .transformation(new CircleCrop())
                    .imageView(view)
                    .build());
        else
            view.setImageResource(R.mipmap.ic_head_default);
    }

    public void setImageViewPicture(String url, ImageView view, int errorPic) {
        LogUtils.debugInfo("hxb--->", url);
        if (!ArmsUtils.isEmpty(url))
            mImageLoader.loadImage(mApplication, CommonImageConfigImpl
                    .builder()
                    .url(url)
                    .errorPic(errorPic)
                    .placeholder(errorPic)
                    .imageView(view)
                    .build());

        else
            view.setImageResource(errorPic);
    }

    /**
     * 预览图片
     *
     * @param url
     */
    public void openExternalPreview(String url) {
        if (ArmsUtils.isEmpty(url)) {
            ArmsUtils.makeText(mApplication, "没有可预览的图片");
        } else
        ImageViewLookImgsUtils.init().lookImgs(AppManagerUtil.getCurrentActivity(), url);
    }


}
