package com.lesso.module.me.mvp.presenter;

import android.app.Application;
import android.widget.ImageView;

import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.body.ProgressInfo;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.http.observer.UploadObserver;
import com.jess.arms.http.throwable.HttpThrowable;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.AppManagerUtil;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.DataHelper;
import com.jess.arms.utils.LogUtils;
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
import me.jessyan.armscomponent.commonsdk.http.observer.MyHttpResultObserver;
import me.jessyan.armscomponent.commonsdk.imgaEngine.config.CommonImageConfigImpl;
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
        if (fileTypes == UploadFileUserCardType.HeadPhoto) {
            postUploadDriverHeadFile(file);
        } else {
            fileArr.add(file);
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
                        mDriverVerifyDetailBean.setHeadSrc(result.getData().getHeadPath());
                    }

                    @Override
                    public void onError(long progressInfoId, HttpThrowable throwable) {
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
                        switch (fileTypes) {
                            case IdCard:
                                mDriverVerifyDetailBean.setIdCardPath(result.getData().getFilePathInfo().getIdCard());
                                if (result.getData().getFilePathInfo().getIdCardBack().isSuccess())
                                    mRootView.setUserCardNumber(result.getData().getFilePathInfo().getIdCardBack().getData().getIdNum());
                                break;
                            case IdCardBack:
                                mDriverVerifyDetailBean.setIdCardBackPath(result.getData().getFilePathInfo().getIdCard());
                                break;
                            case LifePhoto:
                                mDriverVerifyDetailBean.setLifePhotoPath(result.getData().getFilePathInfo().getIdCard());
                                break;
                            case DriverCard:
                                mDriverVerifyDetailBean.setDriverCardPath(result.getData().getFilePathInfo().getIdCard());
                                if (result.getData().getFilePathInfo().getIdCardBack().isSuccess())
                                    mRootView.setDriverCardNumber(result.getData().getFilePathInfo().getIdCardBack().getData().getIdNum());
                                break;
                            case DriverCardBack:
                                mDriverVerifyDetailBean.setDriverCardBackPath(result.getData().getFilePathInfo().getIdCard());
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
        if (mDriverVerifyDetailBean == null) {
            return;
        }
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
        mDriverVerifyDetailBean.setDriverBy(userName);
        mDriverVerifyDetailBean.setIdno(userCard);
        mDriverVerifyDetailBean.setDriverno(driverCard);
        mModel.postSaveDriverVerifyInfo(mDriverVerifyDetailBean)
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
                        mRootView.showMessage("保存成功");
                    }

                });

    }

    /**
     * 选择图片
     */
    public void getPictureSelector() {
        PictureSelectorUtils.postPictureSelector(true, false, 1, 1);
    }

    public void setImageViewHeadPicture(String url, ImageView view) {
        LogUtils.debugInfo("hxb--->", url);
        if (!ArmsUtils.isEmpty(url))
            mImageLoader.loadImage(mApplication, CommonImageConfigImpl
                    .builder()
                    .url(url)
                    .errorPic(R.mipmap.ic_defaul_head_img)
                    .placeholder(R.mipmap.ic_defaul_head_img)
                    .transformation(new CircleCrop())
                    .imageView(view)
                    .build());
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


}
