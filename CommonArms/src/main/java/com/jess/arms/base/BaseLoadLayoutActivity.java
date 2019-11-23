package com.jess.arms.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.InflateException;
import android.view.View;
import android.widget.FrameLayout;

import com.hxb.app.loadlayoutlibrary.LoadLayout;
import com.hxb.app.loadlayoutlibrary.OnLoadListener;
import com.hxb.app.loadlayoutlibrary.State;
import com.jess.arms.R;
import com.jess.arms.mvp.IPresenter;
import com.jess.arms.utils.ArmsUtils;

import butterknife.ButterKnife;

/**
 * @Author :hexingbo
 * @Date :2019/11/22
 * @FileName： BaseLoadLayoutNewActivity
 * @Describe :
 */
public abstract class BaseLoadLayoutActivity<P extends IPresenter> extends BaseActivity<P> implements ILoadlayoutView{

    protected FrameLayout frameLayout;
    protected LoadLayout mLoadLayout;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.savedInstanceState =savedInstanceState;
        ArmsUtils.statuInScreen(this);//全屏,并且沉侵式状态栏
        setStatusBar();//设置状态栏颜色为黑色
        setContentView(R.layout.public_base_loadlayout_activity);
        frameLayout = findViewById(R.id.fl_content);
        mLoadLayout = findViewById(R.id.base_load_layout);
        try {
            int layoutResID = initView(savedInstanceState);
            //如果initView返回0,框架则不会调用setContentView(),当然也不会 Bind ButterKnife
            if (layoutResID != 0) {
                View viewContent = View.inflate(this, layoutResID, null);
                frameLayout.addView(viewContent);
                //绑定到butterknife
                mUnbinder = ButterKnife.bind(this);
            }
        } catch (Exception e) {
            if (e instanceof InflateException) throw e;
            e.printStackTrace();
        }
        mLoadLayout.setOnLoadListener(new OnLoadListener() {
            @Override
            public void onLoad() {
                initData(savedInstanceState);
            }
        });
        mLoadLayout.setLayoutState(State.LOADING);
    }

    protected void setFailedViewId(int failedViewId) {
        if (failedViewId != 0) {
            mLoadLayout.setFailedViewId(failedViewId);
        }
    }

    protected void setNoDataViewId(int noDataViewId) {
        if (noDataViewId != 0) {
            mLoadLayout.setNoDataViewId(noDataViewId);
        }
    }

    protected void setLoadingViewId(int loadingViewId) {
        if (loadingViewId != 0) {
            mLoadLayout.setLoadingViewId(loadingViewId);
        }
    }

    @Override
    public void setLayoutState_SUCCESS() {
        mLoadLayout.setLayoutState(State.SUCCESS);
    }

    @Override
    public void setLayoutState_FAILED() {
        mLoadLayout.setLayoutState(State.FAILED);
    }

    @Override
    public void setLayoutState_NO_DATA() {
        mLoadLayout.setLayoutState(State.NO_DATA);
    }

    @Override
    public void setLayoutState_LOADING() {
        mLoadLayout.setLayoutState(State.LOADING);
    }
}
