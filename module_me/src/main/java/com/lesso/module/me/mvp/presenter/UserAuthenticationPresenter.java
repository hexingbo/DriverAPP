package com.lesso.module.me.mvp.presenter;

import android.app.Application;
import android.widget.ImageView;

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
import com.jess.arms.utils.RxLifecycleUtils;
import com.lesso.module.me.BuildConfig;
import com.lesso.module.me.mvp.contract.UserAuthenticationContract;
import com.lesso.module.me.mvp.model.entity.SubmitDriverVerifyBean;
import com.lesso.module.me.mvp.model.entity.UploadCardFileResultBean;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.armscomponent.commonres.constant.CommonHttpUrl;
import me.jessyan.armscomponent.commonres.enums.AuthenticationStatusType;
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
 * 2019/11/20 15:31
 * Description:
 * ================================================
 */
@ActivityScope
public class UserAuthenticationPresenter extends BasePresenter<UserAuthenticationContract.Model, UserAuthenticationContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    private SubmitDriverVerifyBean mSubmitDriverVerifyBean;
    private UploadFileUserCardType fileTypes;
    private List<File> fileArr = new ArrayList<>();

    public SubmitDriverVerifyBean getSubmitDriverVerifyBean() {
        if (mSubmitDriverVerifyBean == null) mSubmitDriverVerifyBean = new SubmitDriverVerifyBean();
        return mSubmitDriverVerifyBean;
    }

    @Inject
    public UserAuthenticationPresenter(UserAuthenticationContract.Model model, UserAuthenticationContract.View rootView) {
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
        if (mRootView.getRequestPermission() != null && mRootView.getRxPermissions() != null)
            mModel.checkPermission(mRootView.getRxPermissions(), mRootView.getRequestPermission(), mErrorHandler);

    }

    /**
     * 上传文件
     */
    public void postUploadFile(File file) {
        if (file == null) {
            mRootView.showMessage("请选择你要上传的文件");
            return;
        }
        mRootView.setImageViewPicture(file.getPath(), fileTypes,mSubmitDriverVerifyBean);
        fileArr.add(file);
        postUploadDriverInfoFile(fileArr, fileTypes);
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
                        switch (fileTypes) {
                            case IdCard:
                                mSubmitDriverVerifyBean.setIdCardPath(result.getData().getFilePathInfo().getIdCard());
                                if (result.getData().getFilePathInfo().getIdCardBack().isSuccess())
                                    mRootView.setUserCardNumber(result.getData().getFilePathInfo().getIdCardBack().getData().getIdNum());
                                break;
                            case IdCardBack:
                                mSubmitDriverVerifyBean.setIdCardBackPath(result.getData().getFilePathInfo().getIdCard());
                                break;
                            case LifePhoto:
                                mSubmitDriverVerifyBean.setLifePhotoPath(result.getData().getFilePathInfo().getIdCard());
                                break;
                            case DriverCard:
                                mSubmitDriverVerifyBean.setDriverCardPath(result.getData().getFilePathInfo().getIdCard());
                                if (result.getData().getFilePathInfo().getIdCardBack().isSuccess())
                                    mRootView.setDriverCardNumber(result.getData().getFilePathInfo().getIdCardBack().getData().getIdNum());
                                break;
                            case DriverCardBack:
                                mSubmitDriverVerifyBean.setDriverCardBackPath(result.getData().getFilePathInfo().getIdCard());
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
     * 实际认证提交参数
     */
    public void postDriverVerify(String userName, String userCard, String driverCard) {
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
        if (ArmsUtils.isEmpty(mSubmitDriverVerifyBean.getIdCardPath())) {
            mRootView.showMessage("请上传身份证正面照");
            return;
        }
        if (ArmsUtils.isEmpty(mSubmitDriverVerifyBean.getIdCardBackPath())) {
            mRootView.showMessage("请上传身份证背面照");
            return;
        }
        if (ArmsUtils.isEmpty(mSubmitDriverVerifyBean.getDriverCardPath())) {
            mRootView.showMessage("请上传驾驶证正面照");
            return;
        }
        if (ArmsUtils.isEmpty(mSubmitDriverVerifyBean.getDriverCardBackPath())) {
            mRootView.showMessage("请上传驾驶证背面照");
            return;
        }

        mSubmitDriverVerifyBean.setName(userName);
        mSubmitDriverVerifyBean.setIdno(userCard);
        mSubmitDriverVerifyBean.setDriverno(driverCard);
        mSubmitDriverVerifyBean.setCurrentUserId(DataHelper.getStringSF(mApplication, Constants.SP_USER_ID));

        mModel.postDriverVerify(mSubmitDriverVerifyBean)
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
                        DataHelper.setStringSF(mApplication, Constants.SP_VERIFY_STATUS, AuthenticationStatusType.D.name());
                        EventBusManager.getInstance().post(new MessageEvent(EventBusHub.Message_UpdateUserInfo));
                        AppManagerUtil.getCurrentActivity().finish();
                    }

                });

    }


    /**
     * 选择图片
     */
    public void getPictureSelector() {
        PictureSelectorUtils.postPictureSelector(true, true, fileTypes == UploadFileUserCardType.HeadPhoto ? 1 : 3, fileTypes == UploadFileUserCardType.HeadPhoto ? 1 : 2);
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
    }


    /**
     * 预览图片
     *
     * @param url
     */
    public void openExternalPreview(String url) {
        ImageViewLookImgsUtils.init().lookImgs(AppManagerUtil.getCurrentActivity(), url);
    }


}
