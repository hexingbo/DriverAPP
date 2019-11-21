package com.lesso.module.me.mvp.ui.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.AppManagerUtil;
import com.jess.arms.utils.ArmsUtils;
import com.lesso.module.me.R;
import com.lesso.module.me.R2;
import com.lesso.module.me.di.component.DaggerMainMyComponent;
import com.lesso.module.me.mvp.contract.MainMyContract;
import com.lesso.module.me.mvp.model.entity.UserInfoBean;
import com.lesso.module.me.mvp.presenter.MainMyPresenter;
import com.lesso.module.me.mvp.ui.activity.AboutUsActivity;
import com.lesso.module.me.mvp.ui.activity.CompanyJoinManageActivity;
import com.lesso.module.me.mvp.ui.activity.CompanyJoinedManageActivity;
import com.lesso.module.me.mvp.ui.activity.UpdatePwdActivity;
import com.lesso.module.me.mvp.ui.activity.UserInfoActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import me.jessyan.armscomponent.commonres.base.BaseLoadLayoutFragment;
import me.jessyan.armscomponent.commonres.other.CircleImageView;
import me.jessyan.armscomponent.commonsdk.core.RouterHub;
import me.jessyan.armscomponent.commonsdk.imgaEngine.config.CommonImageConfigImpl;
import me.jessyan.armscomponent.commonsdk.utils.Utils;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * =============================================
 * 作    者：贺兴波
 * 时    间：2019/09/27 11:24
 * 描    述：我的页面
 * =============================================
 */
@Route(path = RouterHub.Me_MainMyFragment)
public class MainMyFragment extends BaseLoadLayoutFragment<MainMyPresenter> implements MainMyContract.View {

    @BindView(R2.id.img_user_head)
    CircleImageView imgUserHead;
    @BindView(R2.id.tv_user_nice)
    TextView tvUserNice;
    @BindView(R2.id.tv_send_order_number)
    TextView tvSendOrderNumber;
    @BindView(R2.id.btn_submit)
    TextView btnSubmit;

    @Inject
    Dialog mDialog;

    public static MainMyFragment newInstance() {
        MainMyFragment fragment = new MainMyFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerMainMyComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main_my, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initDateDefault(savedInstanceState);
    }

    @Override
    protected void initDateDefault(@Nullable Bundle savedInstanceState) {
        btnSubmit.setText(R.string.me_login_out);
        onLoad();
    }

    @Override
    public void setData(@Nullable Object data) {

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

    }

    @OnClick(R2.id.ll_company_join)
    public void onCompanyJoinViewClicked(View view) {
        //加盟物流公司
        AppManagerUtil.jump(CompanyJoinManageActivity.class);
    }

    @OnClick(R2.id.ll_company_manager)
    public void onCompanyManagerViewClicked(View view) {
        //加盟管理
        AppManagerUtil.jump(CompanyJoinedManageActivity.class);
    }

    @OnClick(R2.id.ll_order_account)
    public void onOrerAccountViewClicked(View view) {
        //发起对账
        Utils.navigation(AppManagerUtil.getCurrentActivity(), RouterHub.Waybill_OrderAccountsManagerActivity);
    }

    @OnClick(R2.id.ll_user_info)
    public void onUserInfoViewClicked(View view) {
        //个人资料
        AppManagerUtil.jump(UserInfoActivity.class);
    }

    @OnClick(R2.id.ll_update_pwd)
    public void onUpdatePwdViewClicked(View view) {
        //修改密码
        AppManagerUtil.jump(UpdatePwdActivity.class);
    }

    @OnClick(R2.id.ll_about_us)
    public void onAboutUsViewClicked(View view) {
        //关于我们
        AppManagerUtil.jump(AboutUsActivity.class);
    }

    @OnClick(R2.id.btn_submit)
    public void onLoginOutViewClicked(View view) {
        //退出登录
        mPresenter.postLoginOut();
    }

    @Override
    public void setUserInfo(UserInfoBean bean) {
        tvUserNice.setText(bean.getDriverName());
        tvSendOrderNumber.setText(getActivity().getText(R.string.me_name_send_order_number) + bean.getDepartNum());
//        if (!TextUtils.isEmpty(bean.getHeadUrl())) {
//            ArmsUtils.obtainAppComponentFromContext(getActivity()).imageLoader().loadImage(getActivity(),
//                    CommonImageConfigImpl
//                            .builder()
//                            .url(bean.getHeadUrl())
//                            .imageView(imgUserHead)
//                            .build());
//        } else {
//            imgUserHead.setImageResource(R.mipmap.ic_head_default);
//        }
    }

    @Override
    public void onLoad() {
        mPresenter.getUserInfo();
    }
}
