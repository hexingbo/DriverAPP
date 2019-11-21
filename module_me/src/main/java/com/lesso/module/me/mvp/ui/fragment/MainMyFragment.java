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
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.flyco.tablayout.widget.MsgView;
import com.jess.arms.base.MessageEvent;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.AppManagerUtil;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.DataHelper;
import com.lesso.module.me.R;
import com.lesso.module.me.R2;
import com.lesso.module.me.di.component.DaggerMainMyComponent;
import com.lesso.module.me.mvp.contract.MainMyContract;
import com.lesso.module.me.mvp.contract.UserAuthenticationContract;
import com.lesso.module.me.mvp.model.entity.UserInfoBean;
import com.lesso.module.me.mvp.presenter.MainMyPresenter;
import com.lesso.module.me.mvp.ui.activity.AboutUsActivity;
import com.lesso.module.me.mvp.ui.activity.CompanyJoinManageActivity;
import com.lesso.module.me.mvp.ui.activity.CompanyJoinedManageActivity;
import com.lesso.module.me.mvp.ui.activity.UpdatePwdActivity;
import com.lesso.module.me.mvp.ui.activity.UserAuthenticationActivity;
import com.lesso.module.me.mvp.ui.activity.UserInfoActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import me.jessyan.armscomponent.commonres.base.BaseLoadLayoutFragment;
import me.jessyan.armscomponent.commonres.other.CircleImageView;
import me.jessyan.armscomponent.commonsdk.core.Constants;
import me.jessyan.armscomponent.commonsdk.core.EventBusHub;
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
    ImageView imgUserHead;
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

    @OnClick({R2.id.ll_company_join, R2.id.ll_company_manager, R2.id.ll_order_account,
            R2.id.ll_order_account, R2.id.ll_user_info, R2.id.ll_update_pwd, R2.id.ll_about_us, R2.id.btn_submit})
    public void onCompanyJoinViewClicked(View view) {
        if (view.getId() == R.id.btn_submit) {
            //退出登录
            mPresenter.postLoginOut();
        } else {
            if (view.getId() == R.id.ll_company_join) {
                //加盟物流公司
                AppManagerUtil.jump(CompanyJoinManageActivity.class);
            } else if (view.getId() == R.id.ll_company_manager) {
                //加盟管理
                if (ArmsUtils.isEmpty(DataHelper.getStringSF(getContext(), Constants.SP_VERIFY_STATUS))) {
                    Utils.navigation(getContext(),RouterHub.Me_UserAuthenticationActivity);
                } else
                    AppManagerUtil.jump(CompanyJoinedManageActivity.class);
            } else if (view.getId() == R.id.ll_order_account) {
                //发起对账
                Utils.navigation(AppManagerUtil.getCurrentActivity(), RouterHub.Waybill_OrderAccountsManagerActivity);
            } else if (view.getId() == R.id.ll_user_info) {
                //个人资料
                AppManagerUtil.jump(UserInfoActivity.class);
            } else if (view.getId() == R.id.ll_update_pwd) {
                //修改密码
                AppManagerUtil.jump(UpdatePwdActivity.class);
            } else if (view.getId() == R.id.ll_about_us) {
                //关于我们
                AppManagerUtil.jump(AboutUsActivity.class);
            }
        }

    }


    @Override
    public void setUserInfo(UserInfoBean bean) {
        tvUserNice.setText(bean.getDriverName());
        tvSendOrderNumber.setText(getActivity().getText(R.string.me_name_send_order_number) + bean.getDepartNum());
        mPresenter.setUserHeadImager(imgUserHead, bean.getHeadUrl());
    }

    @Override
    public void onLoad() {
        mPresenter.getUserInfo();
    }

    @Override
    protected void getEventBusHub_Fragment(MessageEvent message) {
        super.getEventBusHub_Fragment(message);
        if (message.getType().equals(EventBusHub.Message_UpdateUserInfo)) {
            onLoad();
        }
    }
}
