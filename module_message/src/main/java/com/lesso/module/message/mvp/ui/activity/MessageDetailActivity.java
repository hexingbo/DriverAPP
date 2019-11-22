package com.lesso.module.message.mvp.ui.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.lesso.module.message.R;
import com.lesso.module.message.R2;
import com.lesso.module.message.di.component.DaggerMessageDetailComponent;
import com.lesso.module.message.mvp.contract.MessageDetailContract;
import com.lesso.module.message.mvp.model.entity.MessageBean;
import com.lesso.module.message.mvp.presenter.MessageDetailPresenter;

import javax.inject.Inject;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * user：贺兴波
 * 2019/11/20 12:25
 * Description:消息详情
 * ================================================
 */
public class MessageDetailActivity extends BaseActivity<MessageDetailPresenter> implements MessageDetailContract.View {

    public static final String IntentValue = "MessageBean";
    private MessageBean mMessageBean;

    @Inject
    Dialog mDialog;

    @BindView(R2.id.tv_title)
    TextView tvTitle;
    @BindView(R2.id.tv_content)
    TextView tvContent;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMessageDetailComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_message_detail; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle(R.string.module_message_name_message_detail);
        mMessageBean = (MessageBean) getIntent().getSerializableExtra(IntentValue);
        if (mMessageBean != null && mMessageBean.getHaveRead() == 0)
            mPresenter.postUpdateMessageRead(mMessageBean.getUserMsgId());
        setMessageBean(mMessageBean);
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
    public void setMessageBean(MessageBean bean) {
        if (bean != null) {
            tvTitle.setText(bean.getTitle());
            tvContent.setText(getResources().getString(R.string.module_message_name_message_detail_content).replace("AA",bean.getContent()));

        }
    }

    @Override
    public Context getActivity() {
        return this;
    }
}
