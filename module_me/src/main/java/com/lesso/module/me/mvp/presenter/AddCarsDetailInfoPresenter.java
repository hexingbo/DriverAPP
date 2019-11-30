package com.lesso.module.me.mvp.presenter;

import android.app.Activity;
import android.app.Application;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
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
import com.lesso.module.me.mvp.contract.AddCarsDetailInfoContract;
import com.lesso.module.me.mvp.model.entity.CarTeamDetailBean;
import com.lesso.module.me.mvp.model.entity.UploadCardFileResultBean;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
 * 2019/11/26 10:41
 * Description:
 * ================================================
 */
@ActivityScope
public class AddCarsDetailInfoPresenter extends BasePresenter<AddCarsDetailInfoContract.Model, AddCarsDetailInfoContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    private Date startDate = new Date();
    private Date endDate = new Date();
    private Calendar startDateCalendar = Calendar.getInstance();
    private Calendar endDateCalendar = Calendar.getInstance();

    private CarTeamDetailBean mCarTeamDetailBean;
    private UploadFileUserCardType fileTypes;
    private List<File> fileArr = new ArrayList<>();
    private TimePickerView pvTime;

    private String carId;
    private String insuranceFormatDate = getTime(startDate);

    private List<String> listCarType;

    public void setCarId(String carId) {
        this.carId = carId;
        getCarTeamDetail(carId);
    }

    @Inject
    public AddCarsDetailInfoPresenter(AddCarsDetailInfoContract.Model model, AddCarsDetailInfoContract.View rootView) {
        super(model, rootView);
        fileArr.clear();
        endDate.setYear(startDate.getYear() + 20);
        endDateCalendar.setTime(endDate);

        //车型：['保温车', '叉车', '平板车', '飞翼车', '半封闭车', '危险品车', '集装车', '敞篷车', '金杯车', '自卸货车', '高低板车', '高栏车', '冷藏车', '厢式车']
        listCarType = new ArrayList<>();
        listCarType.add("保温车");
        listCarType.add("叉车");
        listCarType.add("平板车");
        listCarType.add("飞翼车");
        listCarType.add("半封闭车");
        listCarType.add("危险品车");
        listCarType.add("集装车");
        listCarType.add("敞篷车");
        listCarType.add("金杯车");
        listCarType.add("自卸货车");
        listCarType.add("高低板车");
        listCarType.add("高栏车");
        listCarType.add("冷藏车");
        listCarType.add("厢式车");
    }

    public CarTeamDetailBean getCarTeamDetailBean() {
        if (mCarTeamDetailBean == null) {
            mCarTeamDetailBean = new CarTeamDetailBean();
        }
        return mCarTeamDetailBean;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }

    public void showCarTypePickerView() {
        //条件选择器
        OptionsPickerView pvOptions = new OptionsPickerView.Builder(mRootView.getActivity(), new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = listCarType.get(options1);
//                        + options2Items.get(options1).get(option2)
//                        + options3Items.get(options1).get(option2).get(options3).getPickerViewText();
                mRootView.setCarTypeValue(tx);
            }
        }).build();
        pvOptions.setPicker(listCarType);
        pvOptions.show();
    }

    /**
     * 时间选择器弹窗
     */
    public void showTimePickerView(Activity activity) {

        if (pvTime == null) {
            pvTime = new TimePickerView.Builder(activity, new TimePickerView.OnTimeSelectListener() {
                @Override
                public void onTimeSelect(Date date2, View v) {//选中事件回调
                    insuranceFormatDate = getTime(date2);
                    mRootView.setStartTimeValue(insuranceFormatDate);
                }
            })
                    .setType(TimePickerView.Type.YEAR_MONTH_DAY)//默认全部显示
                    .setCancelText("取消")//取消按钮文字
                    .setSubmitText("确定")//确认按钮文字
                    .setContentSize(20)//滚轮文字大小
                    .setTitleSize(20)//标题文字大小
//                        .setTitleText("请选择时间")//标题文字
                    .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                    .isCyclic(false)//是否循环滚动
                    .setTextColorCenter(Color.BLACK)//设置选中项的颜色
                    .setTitleColor(Color.BLACK)//标题文字颜色
                    .setSubmitColor(Color.BLUE)//确定按钮文字颜色
                    .setCancelColor(Color.BLUE)//取消按钮文字颜色
//                        .setTitleBgColor(0xFF666666)//标题背景颜色 Night mode
//                        .setBgColor(0xFF333333)//滚轮背景颜色 Night mode
//                        .setRange(calendar.get(Calendar.YEAR) - 20, calendar.get(Calendar.YEAR))//默认是1900-2100年
//                .setDate(calendar)// 如果不设置的话，默认是系统时间*/
                    .setRangDate(startDateCalendar, endDateCalendar)//起始终止年月日设定
//                        .setLabel("年","月","日","时","分","秒")
                    .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
//                        .isDialog(true)//是否显示为对话框样式
                    .build();
//            pvTime.setDate(startDateCalendar);//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
        }
        pvTime.show();

    }

    public String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    /**
     * 权限请求
     */
    public void checkPermission(UploadFileUserCardType fileTypes) {
        this.fileTypes = fileTypes;
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
        if (file == null) {
            mRootView.showMessage("请选择你要上传的文件");
            return;
        }
        mRootView.setImageViewPicture(file.getPath(), fileTypes, getCarTeamDetailBean());
        fileArr.clear();
        fileArr.add(file);
        postUploadWlCarTeamFile(fileArr, fileTypes);
    }

    /**
     * 上传证件照
     *
     * @param fileArr
     * @param fileTypes
     */
    private void postUploadWlCarTeamFile(List<File> fileArr, UploadFileUserCardType fileTypes) {
        mModel.postUploadWlCarTeamFile(
                DataHelper.getStringSF(AppManagerUtil.getCurrentActivity(),
                        Constants.SP_USER_ID), fileTypes, fileArr)
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
                        (Module_Me_Doman_Url + CommonHttpUrl.API_postUploadWlCarTeamFile) {

                    @Override
                    public void onProgress(ProgressInfo progressInfo) {
                        LogUtils.debugInfo("上传进度：" + progressInfo.getCurrentbytes() + "%");
                    }

                    @Override
                    public void onResult(HttpResult<UploadCardFileResultBean> result) {
                        mRootView.showMessage("上传成功");
                        if (!ArmsUtils.isEmpty(result.getData())
                                && !ArmsUtils.isEmpty(result.getData().getFilePathInfo())) {
                            switch (fileTypes) {
                                case CarInsurance://车辆保险
                                    getCarTeamDetailBean().setCarInsurancePath(result.getData().getFilePathInfo().getCarInsurance());
                                    break;
                                case DrivingCard://行驶证正面照
                                    getCarTeamDetailBean().setDrivingCardPath(result.getData().getFilePathInfo().getDrivingCard());
                                    break;
                                case DrivingCardBack://行驶证反面照
                                    getCarTeamDetailBean().setDrivingCardBackPath(result.getData().getFilePathInfo().getDrivingCardBack());
                                    break;
                                case CarAPhoto://车头照
                                    getCarTeamDetailBean().setCarAPhotoPath(result.getData().getFilePathInfo().getCarAPhoto());
                                    break;
                                case CarBPhoto://车身照
                                    getCarTeamDetailBean().setCarBPhotoPath(result.getData().getFilePathInfo().getCarBPhoto());
                                    break;
                                case CarCPhoto://车尾照
                                    getCarTeamDetailBean().setCarCPhotoPath(result.getData().getFilePathInfo().getCarCPhoto());
                                    break;
                                default:
                                    throw new IllegalStateException("Unexpected value: have not mipmap resId");
                            }

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

    /**
     * @param carNo               车牌号
     * @param carType             车类型
     * @param carLong             车长
     * @param carSize             车体积
     * @param deadWeight          车载重
     * @param insuranceBy         车辆承险公司
     * @param insuranceFormatDate 保险到期日
     */
    public boolean checkedSubmitValue(String carNo, String carType, String carLong, String carSize, String deadWeight, String insuranceBy, String insuranceFormatDate) {
        if (ArmsUtils.isEmpty(carNo)) {
            ArmsUtils.makeText(mApplication, "请输入车牌号");
            return false;
        }
        if (ArmsUtils.isEmpty(carType)) {
            ArmsUtils.makeText(mApplication, "请选择车型");
            return false;
        }
        if (ArmsUtils.isEmpty(carLong)) {
            ArmsUtils.makeText(mApplication, "请输入车长");
            return false;
        }
        if (ArmsUtils.isEmpty(carSize)) {
            ArmsUtils.makeText(mApplication, "请输入车体积");
            return false;
        }
        if (ArmsUtils.isEmpty(deadWeight)) {
            ArmsUtils.makeText(mApplication, "请输入车载重量");
            return false;
        }
        if (ArmsUtils.isEmpty(insuranceBy)) {
            ArmsUtils.makeText(mApplication, "请输入车辆承险公司");
            return false;
        }
        if (ArmsUtils.isEmpty(insuranceFormatDate)) {
            ArmsUtils.makeText(mApplication, "请输入保险到期日");
            return false;
        }
        if (ArmsUtils.isEmpty(getCarTeamDetailBean().getCarInsurancePath())) {
            ArmsUtils.makeText(mApplication, "请上传车辆保险照");
            return false;
        }
        if (ArmsUtils.isEmpty(getCarTeamDetailBean().getDrivingCardPath())) {
            ArmsUtils.makeText(mApplication, "请上传行驶证正面照");
            return false;
        }
        if (ArmsUtils.isEmpty(getCarTeamDetailBean().getDrivingCardBackPath())) {
            ArmsUtils.makeText(mApplication, "请上传行驶证背面照");
            return false;
        }
        if (ArmsUtils.isEmpty(getCarTeamDetailBean().getCarAPhotoPath())) {
            ArmsUtils.makeText(mApplication, "请上传车头照");
            return false;
        }
        if (ArmsUtils.isEmpty(getCarTeamDetailBean().getCarBPhotoPath())) {
            ArmsUtils.makeText(mApplication, "请上传车身照");
            return false;
        }
        if (ArmsUtils.isEmpty(getCarTeamDetailBean().getCarCPhotoPath())) {
            ArmsUtils.makeText(mApplication, "请上传车尾照");
            return false;
        }
        getCarTeamDetailBean().setCarNo(carNo);
        getCarTeamDetailBean().setBelongBy(DataHelper.getStringSF(mApplication, Constants.SP_USER_ID));
        getCarTeamDetailBean().setCarType(carType);
        getCarTeamDetailBean().setCarLong(carLong);
        getCarTeamDetailBean().setCarSize(carSize);
        getCarTeamDetailBean().setDeadWeight(deadWeight);
        getCarTeamDetailBean().setInsuranceBy(insuranceBy);
        getCarTeamDetailBean().setInsuranceFormatDate(insuranceFormatDate);
        return true;
    }

    /**
     * 添加车辆信息
     * 请先调用 checkedSubmitValue()检验提交数据
     */
    public void postAddWlCarTeam() {
        mModel.postAddWlCarTeam(getCarTeamDetailBean())
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
                        ArmsUtils.makeText(mApplication, "添加成功");
                        EventBusManager.getInstance().post(new MessageEvent(EventBusHub.TAG_LOGIN_SUCCESS));
                        AppManagerUtil.getCurrentActivity().finish();
                    }

                });
    }


    /**
     * 获取车详情数据
     *
     * @param carId
     */
    private void getCarTeamDetail(String carId) {
        if (ArmsUtils.isEmpty(carId)) {
            return;
        }
        mModel.getCarTeamDetail(carId)
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
                .subscribe(new MyHttpResultObserver<HttpResult<CarTeamDetailBean>>(mErrorHandler) {
                    @Override
                    public void onResult(HttpResult<CarTeamDetailBean> result) {
                        mCarTeamDetailBean = result.getData();
                        mRootView.setCarTeamDetailBean(mCarTeamDetailBean);
                    }

                });
    }

    /**
     * 删除车信息
     */
    public void postDeleteWlCarTeam() {
        if (ArmsUtils.isEmpty(mCarTeamDetailBean.getGuid())) {
            return;
        }
        mModel.postDeleteWlCarTeam(mCarTeamDetailBean.getGuid())
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
                        ArmsUtils.makeText(mApplication, "删除成功");
                        EventBusManager.getInstance().post(new MessageEvent(EventBusHub.TAG_LOGIN_SUCCESS));
                        AppManagerUtil.getCurrentActivity().finish();
                    }

                });
    }


}
