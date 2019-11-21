package com.lesso.module.me.mvp.ui.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.AppManagerUtil;
import com.jess.arms.utils.ArmsUtils;
import com.lesso.module.me.R;
import com.lesso.module.me.R2;
import com.lesso.module.me.di.component.DaggerCompanyJoinDetailInfoComponent;
import com.lesso.module.me.mvp.contract.CompanyJoinDetailInfoContract;
import com.lesso.module.me.mvp.model.entity.CompanyJoinBean;
import com.lesso.module.me.mvp.presenter.CompanyJoinDetailInfoPresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import me.jessyan.armscomponent.commonres.base.BaseIntentBean;
import me.jessyan.armscomponent.commonres.other.CircleImageView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * user：贺兴波
 * 2019/11/18 10:32
 * Description:公司详情
 * ================================================
 */
public class CompanyJoinDetailInfoActivity extends BaseActivity<CompanyJoinDetailInfoPresenter> implements CompanyJoinDetailInfoContract.View {

    public static final String IntentValue = "companyId";

    @BindView(R2.id.img_company_head)
    CircleImageView imgCompanyHead;
    @BindView(R2.id.tv_company_name)
    TextView tvCompanyName;
    @BindView(R2.id.tv_company_user)
    TextView tvCompanyUser;
    @BindView(R2.id.tv_user)
    TextView tvUser;
    @BindView(R2.id.tv_phone)
    TextView tvPhone;
    @BindView(R2.id.tv_address)
    TextView tvAddress;
    @BindView(R2.id.btn_submit)
    TextView btnSubmit;
    @BindView(R2.id.ll_button)
    LinearLayout llButton;

    @Inject
    Dialog mDialog;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerCompanyJoinDetailInfoComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_company_join_detail_info; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        ArmsUtils.statuInScreen(this);//全屏,并且沉侵式状态栏
        setTitle(R.string.module_me_compa_detail);
        btnSubmit.setText(getString(R.string.module_me_apply_join));
        BaseIntentBean intentBean = (BaseIntentBean) getIntent().getSerializableExtra(IntentValue);
        if (!ArmsUtils.isEmpty(intentBean)) {
            llButton.setVisibility(intentBean.isValueBoolean() ? View.VISIBLE : View.GONE);
            mPresenter.getCompanyDetail(intentBean.getValueString());
        } else {
            showMessage(AppManagerUtil.appContext().getResources().getString(me.jessyan.armscomponent.commonres.R.string.public_name_data_error));
        }

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

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void setCompanyJoinBean(CompanyJoinBean bean) {
        if (!ArmsUtils.isEmpty(bean)) {
            tvCompanyName.setText(bean.getCompanyName());
            tvCompanyUser.setText(bean.getLegalPersonName());
            tvUser.setText(bean.getContactBy());
            tvPhone.setText(bean.getContactMobile());
            tvAddress.setText(bean.getAddress());
            // TODO: 2019/11/18 物流公司logo平台地址有误 后期修改
//            if (!TextUtils.isEmpty(bean.getHeadUrl())) {
//                ArmsUtils.obtainAppComponentFromContext(getActivity()).imageLoader().loadImage(getActivity(),
//                        CommonImageConfigImpl
//                                .builder().errorPic(R.mipmap.app_logo)
//                                .url(bean.getHeadUrl())
//                                .imageView(imgCompanyHead)
//                                .build());
//            } else {
//                imgCompanyHead.setImageResource(R.mipmap.app_logo);
//            }
        } else {
            llButton.setVisibility(View.GONE);
        }
    }


    @OnClick(R2.id.btn_submit)
    public void onViewClicked() {
        mPresenter.postCompanyJoin();
    }
}
