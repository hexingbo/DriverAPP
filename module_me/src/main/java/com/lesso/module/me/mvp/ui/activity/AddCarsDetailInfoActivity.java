package com.lesso.module.me.mvp.ui.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.lesso.module.me.R;
import com.lesso.module.me.R2;
import com.lesso.module.me.di.component.DaggerAddCarsDetailInfoComponent;
import com.lesso.module.me.mvp.contract.AddCarsDetailInfoContract;
import com.lesso.module.me.mvp.model.entity.CarTeamDetailBean;
import com.lesso.module.me.mvp.presenter.AddCarsDetailInfoPresenter;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;
import me.jessyan.armscomponent.commonres.dialog.MaterialDialog;
import me.jessyan.armscomponent.commonres.dialog.MyHintDialog;
import me.jessyan.armscomponent.commonres.enums.UploadFileUserCardType;
import me.jessyan.armscomponent.commonres.other.ClearEditText;
import me.jessyan.armscomponent.commonsdk.utils.ViewFocusUtils;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * user：贺兴波
 * 2019/11/26 10:41
 * Description:添加车辆详情信息
 * ================================================
 */
public class AddCarsDetailInfoActivity extends BaseActivity<AddCarsDetailInfoPresenter> implements AddCarsDetailInfoContract.View {

    public static final String IntentValue = "carId";
    public static final String IntentValue_Action = "action";
    public boolean isAction;

    @Inject
    RxPermissions mRxPermissions;
    @Inject
    Dialog mDialog;
    @Inject
    MyHintDialog mMyHintDialog;
    @Inject
    MaterialDialog mPermissionDialog;

    @BindString(R2.string.permission_request_location)
    String mStrPermission;

    @BindView(R2.id.et_car_no)
    ClearEditText etCarNo;
    @BindView(R2.id.tv_car_type)
    TextView tvCarType;
    @BindView(R2.id.et_car_length)
    ClearEditText etCarLength;
    @BindView(R2.id.et_car_volume)
    ClearEditText etCarVolume;
    @BindView(R2.id.et_car_load)
    ClearEditText etCarLoad;
    @BindView(R2.id.et_insurance_company)
    ClearEditText etInsuranceCompany;
    @BindView(R2.id.et_insurance_time_end)
    TextView etInsuranceTimeEnd;
    @BindView(R2.id.btn_submit)
    TextView btnSubmit;

    @BindView(R2.id.img_insurance)
    ImageView imgInsurance;
    @BindView(R2.id.img_add_insurance)
    ImageView imgAddInsurance;
    @BindView(R2.id.img_delete_insurance)
    ImageView imgDeleteInsurance;
    @BindView(R2.id.img_driving_permit_s)
    ImageView imgDrivingPermitS;
    @BindView(R2.id.img_add_driving_permit_s)
    ImageView imgAddDrivingPermitS;
    @BindView(R2.id.img_delete_driving_permit_s)
    ImageView imgDeleteDrivingPermitS;
    @BindView(R2.id.img_driving_permit_n)
    ImageView imgDrivingPermitN;
    @BindView(R2.id.img_add_driving_permit_n)
    ImageView imgAddDrivingPermitN;
    @BindView(R2.id.img_delete_driving_permit_n)
    ImageView imgDeleteDrivingPermitN;
    @BindView(R2.id.img_car_head)
    ImageView imgCarHead;
    @BindView(R2.id.img_add_car_head)
    ImageView imgAddCarHead;
    @BindView(R2.id.img_delete_car_head)
    ImageView imgDeleteCarHead;
    @BindView(R2.id.img_car_boy)
    ImageView imgCarBoy;
    @BindView(R2.id.img_add_car_boy)
    ImageView imgAddCarBoy;
    @BindView(R2.id.img_delete_car_boy)
    ImageView imgDeleteCarBoy;
    @BindView(R2.id.img_car_end)
    ImageView imgCarEnd;
    @BindView(R2.id.img_add_car_end)
    ImageView imgAddCarEnd;
    @BindView(R2.id.img_delete_car_end)
    ImageView imgDeleteCarEnd;
    @BindView(R2.id.btn_delete)
    TextView btnDelete;
    @BindView(R2.id.ll_bottom)
    LinearLayout llBottom;


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerAddCarsDetailInfoComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_add_cars_detail_info; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        String carId = getIntent().getStringExtra(IntentValue);
        isAction = getIntent().getBooleanExtra(IntentValue_Action, false);
        setTitle(ArmsUtils.isEmpty(carId) ? R.string.module_me_name_add_car_info : R.string.module_me_name_car_detail_info);
        initViewState();
        mPresenter.getCarTeamDetailBean();
        mPresenter.setCarId(carId);
    }

    @Override
    public void showLoading() {
        mDialog.show();
    }

    @Override
    public void hideLoading() {
        mDialog.dismiss();
    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }


    @OnClick({R2.id.tv_car_type, R2.id.et_insurance_time_end, R2.id.btn_submit, R2.id.btn_delete})
    public void onViewClicked(View view) {
        if (!isAction) return;
        if (view.getId() == R.id.tv_car_type) {
            // 车型号名称选择
            mPresenter.showCarTypePickerView();
        } else if (view.getId() == R.id.et_insurance_time_end) {
            //选择时间
            mPresenter.showTimePickerView(getActivity());
        } else if (view.getId() == R.id.btn_submit) {
            // 提交数据
            boolean checkedSubmitValue = mPresenter.checkedSubmitValue(etCarNo.getText().toString().trim(),
                    tvCarType.getText().toString().trim(), etCarLength.getText().toString().trim(),
                    etCarVolume.getText().toString().trim(), etCarLoad.getText().toString().trim(),
                    etInsuranceCompany.getText().toString().trim(), etInsuranceTimeEnd.getText().toString().trim());
            if (checkedSubmitValue) {
                mMyHintDialog.setTextContent("确认保存");
                mMyHintDialog.setOnDialogListener(new MyHintDialog.OnDialogListener() {
                    @Override
                    public void onItemViewRightListener() {
                        mPresenter.postAddWlCarTeam();
                    }
                });
                mMyHintDialog.show();

            }
        } else if (view.getId() == R.id.btn_delete) {
            //删除
            mMyHintDialog.setTextContent("确认删除");
            mMyHintDialog.setOnDialogListener(new MyHintDialog.OnDialogListener() {
                @Override
                public void onItemViewRightListener() {
                    mPresenter.postDeleteWlCarTeam();
                }
            });
            mMyHintDialog.show();
        }
    }

    private void initViewState() {
        setViewInsurance(true);
        setViewDrivingPermitS(true);
        setViewDrivingPermitN(true);
        setViewCarHead(true);
        setViewCarBoy(true);
        setViewCarEnd(true);
        llBottom.setVisibility(isAction ? View.VISIBLE : View.GONE);
        etCarNo.setEnabled(isAction ? true : false);
        etCarLength.setEnabled(isAction ? true : false);
        etCarVolume.setEnabled(isAction ? true : false);
        etCarLoad.setEnabled(isAction ? true : false);
        etInsuranceCompany.setEnabled(isAction ? true : false);
    }


    /**
     * 车险照
     *
     * @param addPicture
     */
    private void setViewInsurance(boolean addPicture) {
        imgInsurance.setEnabled(!addPicture);
        if (isAction) {
            imgDeleteInsurance.setVisibility(!addPicture ? View.VISIBLE : View.GONE);
            imgAddInsurance.setVisibility(addPicture ? View.VISIBLE : View.GONE);
        } else {
            imgDeleteInsurance.setVisibility(View.GONE);
            imgAddInsurance.setVisibility(View.GONE);
        }

    }

    /**
     * 行驶证正面
     *
     * @param addPicture
     */
    private void setViewDrivingPermitS(boolean addPicture) {
        imgDrivingPermitS.setEnabled(!addPicture);
        if (isAction) {
            imgAddDrivingPermitS.setVisibility(addPicture ? View.VISIBLE : View.GONE);
            imgDeleteDrivingPermitS.setVisibility(!addPicture ? View.VISIBLE : View.GONE);
        } else {
            imgAddDrivingPermitS.setVisibility(View.GONE);
            imgDeleteDrivingPermitS.setVisibility(View.GONE);
        }
    }

    /**
     * 行驶证背面
     *
     * @param addPicture
     */
    private void setViewDrivingPermitN(boolean addPicture) {
        imgDrivingPermitN.setEnabled(!addPicture);
        if (isAction) {
            imgAddDrivingPermitN.setVisibility(addPicture ? View.VISIBLE : View.GONE);
            imgDeleteDrivingPermitN.setVisibility(!addPicture ? View.VISIBLE : View.GONE);
        } else {
            imgAddDrivingPermitN.setVisibility(View.GONE);
            imgDeleteDrivingPermitN.setVisibility(View.GONE);
        }
    }

    /**
     * 车头照
     *
     * @param addPicture
     */
    private void setViewCarHead(boolean addPicture) {
        imgCarHead.setEnabled(!addPicture);
        if (isAction) {
            imgAddCarHead.setVisibility(addPicture ? View.VISIBLE : View.GONE);
            imgDeleteCarHead.setVisibility(!addPicture ? View.VISIBLE : View.GONE);
        } else {
            imgAddCarHead.setVisibility(View.GONE);
            imgDeleteCarHead.setVisibility(View.GONE);
        }
    }

    /**
     * 车身照
     *
     * @param addPicture
     */
    private void setViewCarBoy(boolean addPicture) {
        imgCarBoy.setEnabled(!addPicture);
        if (isAction) {
            imgAddCarBoy.setVisibility(addPicture ? View.VISIBLE : View.GONE);
            imgDeleteCarBoy.setVisibility(!addPicture ? View.VISIBLE : View.GONE);
        } else {
            imgAddCarBoy.setVisibility(View.GONE);
            imgDeleteCarBoy.setVisibility(View.GONE);
        }
    }

    /**
     * 车尾照
     *
     * @param addPicture
     */
    private void setViewCarEnd(boolean addPicture) {
        imgCarEnd.setEnabled(!addPicture);
        if (isAction) {
            imgAddCarEnd.setVisibility(addPicture ? View.VISIBLE : View.GONE);
            imgDeleteCarEnd.setVisibility(!addPicture ? View.VISIBLE : View.GONE);
        } else {
            imgAddCarEnd.setVisibility(View.GONE);
            imgDeleteCarEnd.setVisibility(View.GONE);
        }
    }

    @Override
    public void setImageViewPicture(String filePath, UploadFileUserCardType fileTypes, CarTeamDetailBean bean) {
        ImageView imageView = imgInsurance;
        int red = R.mipmap.ic_card_driver_s;
        switch (fileTypes) {
            case CarInsurance://车辆保险
                setViewInsurance(ArmsUtils.isEmpty(filePath));
                imageView = imgInsurance;
                bean.setCarInsuranceUrl(filePath);
                if (ArmsUtils.isEmpty(filePath)) {
                    bean.setCarInsurancePath("");
                }
                break;
            case DrivingCard://行驶证正面照
                setViewDrivingPermitS(ArmsUtils.isEmpty(filePath));
                imageView = imgDrivingPermitS;
                bean.setDrivingCardUrl(filePath);
                if (ArmsUtils.isEmpty(filePath)) {
                    bean.setDrivingCardPath("");
                }
                break;
            case DrivingCardBack://行驶证反面照
                setViewDrivingPermitN(ArmsUtils.isEmpty(filePath));
                bean.setDrivingCardBackUrl(filePath);
                imageView = imgDrivingPermitN;
                if (ArmsUtils.isEmpty(filePath)) {
                    bean.setDrivingCardBackPath("");
                }
                break;
            case CarAPhoto://车头照
                setViewCarHead(ArmsUtils.isEmpty(filePath));
                bean.setCarAPhotoUrl(filePath);
                imageView = imgCarHead;
                if (ArmsUtils.isEmpty(filePath)) {
                    bean.setCarAPhotoPath("");
                }
                break;
            case CarBPhoto://车身照
                setViewCarBoy(ArmsUtils.isEmpty(filePath));
                bean.setCarBPhotoUrl(filePath);
                imageView = imgCarBoy;
                if (ArmsUtils.isEmpty(filePath)) {
                    bean.setCarBPhotoPath("");
                }
                break;
            case CarCPhoto://车尾照
                setViewCarEnd(ArmsUtils.isEmpty(filePath));
                bean.setCarCPhotoUrl(filePath);
                imageView = imgCarEnd;
                if (ArmsUtils.isEmpty(filePath)) {
                    bean.setCarCPhotoPath("");
                }
                break;
            default:
                throw new IllegalStateException("Unexpected value: have not mipmap resId");
        }

        mPresenter.setImageViewPicture(filePath, imageView, red);

    }

    @Override
    public void setCarTeamDetailBean(CarTeamDetailBean bean) {
        if (!ArmsUtils.isEmpty(bean)) {
            etCarNo.setText(bean.getCarNo());
            tvCarType.setText(bean.getCarType());
            etCarLength.setText(bean.getCarLong());
            etCarVolume.setText(bean.getCarSize());
            etCarLoad.setText(bean.getDeadWeight());
            etInsuranceCompany.setText(bean.getInsuranceBy());
            etInsuranceTimeEnd.setText(bean.getInsuranceFormatDate());

            etCarNo.setClearIconVisible(false);
            etCarLength.setClearIconVisible(false);
            etCarVolume.setClearIconVisible(false);
            etCarLoad.setClearIconVisible(false);
            etInsuranceCompany.setClearIconVisible(false);

            setImageViewPicture(bean.getCarInsuranceUrl(), UploadFileUserCardType.CarInsurance, bean);
            setImageViewPicture(bean.getDrivingCardUrl(), UploadFileUserCardType.DrivingCard, bean);
            setImageViewPicture(bean.getDrivingCardBackUrl(), UploadFileUserCardType.DrivingCardBack, bean);
            setImageViewPicture(bean.getCarAPhotoUrl(), UploadFileUserCardType.CarAPhoto, bean);
            setImageViewPicture(bean.getCarBPhotoUrl(), UploadFileUserCardType.CarBPhoto, bean);
            setImageViewPicture(bean.getCarCPhotoUrl(), UploadFileUserCardType.CarCPhoto, bean);

            btnDelete.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void setStartTimeValue(String insuranceFormatDate) {
        etInsuranceTimeEnd.setText(insuranceFormatDate);
    }

    @Override
    public void setCarTypeValue(String tx) {
        tvCarType.setText(tx);
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public RxPermissions getRxPermissions() {
        return mRxPermissions;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片、视频、音频选择结果回调
                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回四种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true  注意：音视频除外
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true  注意：音视频除外
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
                    // 4.media.getAndroidQToPath();为Android Q版本特有返回的字段，此字段有值就用来做上传使用
//                    adapter.setList(selectList);
//                    adapter.notifyDataSetChanged();
                    for (LocalMedia localMedia : selectList) {
                        if (localMedia.isCut()) {
                            String path = localMedia.getCutPath();
                            Log.i("hxb：", "图片保存路径==>" + path);
                            //上传头像
                            File file = new File(path);
                            mPresenter.postUploadFile(file);
                        }

                    }

                    break;
            }
        }
    }

    @OnClick({R2.id.img_insurance, R2.id.img_driving_permit_s,
            R2.id.img_driving_permit_n, R2.id.img_car_head,
            R2.id.img_car_boy, R2.id.img_car_end})
    public void onViewLookImgClicked(View view) {
//        if (!isAction) return;
        ViewFocusUtils.setRequestFocus(view);
        String path = "";
        if (view.getId() == R.id.img_insurance) {
            // : 2019/11/26 车险照片
            path = mPresenter.getCarTeamDetailBean().getCarInsuranceUrl();
        } else if (view.getId() == R.id.img_driving_permit_s) {
            // : 2019/11/26 行驶证正面
            path = mPresenter.getCarTeamDetailBean().getDrivingCardUrl();
        } else if (view.getId() == R.id.img_driving_permit_n) {
            // : 2019/11/26 行驶证背面
            path = mPresenter.getCarTeamDetailBean().getDrivingCardBackUrl();
        } else if (view.getId() == R.id.img_car_head) {
            // : 2019/11/26 车头照
            path = mPresenter.getCarTeamDetailBean().getCarAPhotoUrl();
        } else if (view.getId() == R.id.img_car_boy) {
            // : 2019/11/26 车身照
            path = mPresenter.getCarTeamDetailBean().getCarBPhotoUrl();
        } else {
            // : 2019/11/26 车尾照
            path = mPresenter.getCarTeamDetailBean().getCarCPhotoUrl();
        }
        mPresenter.openExternalPreview(path);
    }


    @OnClick({R2.id.img_add_insurance, R2.id.img_add_driving_permit_s,
            R2.id.img_add_driving_permit_n, R2.id.img_add_car_head,
            R2.id.img_add_car_boy, R2.id.img_add_car_end})
    public void onViewAddImgClicked(View view) {
        if (!isAction) return;
        ViewFocusUtils.setRequestFocus(view);
        UploadFileUserCardType fileTypes;
        if (view.getId() == R.id.img_add_insurance) {
            // : 2019/11/26 车险照片
            fileTypes = UploadFileUserCardType.CarInsurance;
        } else if (view.getId() == R.id.img_add_driving_permit_s) {
            // : 2019/11/26 行驶证正面
            fileTypes = UploadFileUserCardType.DrivingCard;
        } else if (view.getId() == R.id.img_add_driving_permit_n) {
            // : 2019/11/26 行驶证背面
            fileTypes = UploadFileUserCardType.DrivingCardBack;
        } else if (view.getId() == R.id.img_add_car_head) {
            // : 2019/11/26 车头照
            fileTypes = UploadFileUserCardType.CarAPhoto;
        } else if (view.getId() == R.id.img_add_car_boy) {
            // : 2019/11/26 车身照
            fileTypes = UploadFileUserCardType.CarBPhoto;
        } else {
            // : 2019/11/26 车尾照
            fileTypes = UploadFileUserCardType.CarCPhoto;
        }
        mPresenter.checkPermission(fileTypes);
    }

    @OnClick({R2.id.img_delete_insurance, R2.id.img_delete_driving_permit_s,
            R2.id.img_delete_driving_permit_n, R2.id.img_delete_car_head,
            R2.id.img_delete_car_boy, R2.id.img_delete_car_end})
    public void onViewDeleteImgClicked(View view) {
        if (!isAction) return;
        ViewFocusUtils.setRequestFocus(view);
        UploadFileUserCardType fileTypes = null;
        if (view.getId() == R.id.img_delete_insurance) {
            // : 2019/11/26 车险照片
            fileTypes = UploadFileUserCardType.CarInsurance;
        } else if (view.getId() == R.id.img_delete_driving_permit_s) {
            // : 2019/11/26 行驶证正面
            fileTypes = UploadFileUserCardType.DrivingCard;
        } else if (view.getId() == R.id.img_delete_driving_permit_n) {
            // : 2019/11/26 行驶证背面
            fileTypes = UploadFileUserCardType.DrivingCardBack;
        } else if (view.getId() == R.id.img_delete_car_head) {
            // : 2019/11/26 车头照
            fileTypes = UploadFileUserCardType.CarAPhoto;
        } else if (view.getId() == R.id.img_delete_car_boy) {
            // : 2019/11/26 车身照
            fileTypes = UploadFileUserCardType.CarBPhoto;
        } else {
            // : 2019/11/26 车尾照
            fileTypes = UploadFileUserCardType.CarCPhoto;
        }
        setImageViewPicture("", fileTypes, mPresenter.getCarTeamDetailBean());
    }
}
