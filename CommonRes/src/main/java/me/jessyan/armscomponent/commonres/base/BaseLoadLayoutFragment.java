/*
 * Copyright 2017 JessYan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package me.jessyan.armscomponent.commonres.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.hxb.app.loadlayoutlibrary.LoadLayout;
import com.hxb.app.loadlayoutlibrary.OnLoadListener;
import com.hxb.app.loadlayoutlibrary.State;
import com.jess.arms.base.BaseEventBusHub;
import com.jess.arms.base.MessageEvent;
import com.jess.arms.base.delegate.IFragment;
import com.jess.arms.integration.cache.Cache;
import com.jess.arms.integration.cache.CacheType;
import com.jess.arms.integration.lifecycle.FragmentLifecycleable;
import com.jess.arms.mvp.IPresenter;
import com.jess.arms.utils.ArmsUtils;
import com.trello.rxlifecycle2.android.FragmentEvent;

import org.simple.eventbus.Subscriber;

import javax.inject.Inject;

import butterknife.Unbinder;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;
import me.jessyan.armscomponent.commonres.R;

/**
 * ================================================
 * 因为 Java 只能单继承, 所以如果要用到需要继承特定 @{@link Fragment} 的三方库, 那你就需要自己自定义 @{@link Fragment}
 * 继承于这个特定的 @{@link Fragment}, 然后再按照 {@link BaseLoadLayoutFragment} 的格式, 将代码复制过去, 记住一定要实现{@link IFragment}
 */
public abstract class BaseLoadLayoutFragment<P extends IPresenter> extends Fragment implements IFragment, FragmentLifecycleable, OnLoadListener, ILoadlayoutView {
    protected final String TAG = this.getClass().getSimpleName();
    private final BehaviorSubject<FragmentEvent> mLifecycleSubject = BehaviorSubject.create();
    private Bundle savedInstanceState;
    private Unbinder unbinder;
    protected Cache<String, Object> mCache;
    protected FrameLayout frameLayout;
    protected LoadLayout mLoadLayout;
    /**
     * 上下文对象
     */
    protected Context mContext;

    @Inject
    @Nullable
    protected P mPresenter;//如果当前页面逻辑简单, Presenter 可以为 null

    @NonNull
    @Override
    public synchronized Cache<String, Object> provideCache() {
        if (mCache == null) {
            mCache = ArmsUtils.obtainAppComponentFromContext(getActivity()).cacheFactory().build(CacheType.FRAGMENT_CACHE);
        }
        return mCache;
    }

    @NonNull
    @Override
    public final Subject<FragmentEvent> provideLifecycleSubject() {
        return mLifecycleSubject;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.savedInstanceState =savedInstanceState;
        View rootView = inflater.inflate(R.layout.public_base_loadlayout_fragment, container, false);
        frameLayout = rootView.findViewById(R.id.fl_content);
        mLoadLayout = rootView.findViewById(R.id.base_load_layout);
        setLoadingViewId(R.layout.view_custom_loading_data);
        View contentView = initView(inflater, container, savedInstanceState);
        if (contentView != null) {
            frameLayout.addView(contentView);
        }
        mLoadLayout.setOnLoadListener(this);
        return rootView;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initDateDefault(savedInstanceState);
        setLayoutState_LOADING();
    }

    protected abstract void initDateDefault(@Nullable Bundle savedInstanceState);

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) mPresenter.onDestroy();//释放资源
        this.mPresenter = null;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mContext = null;
    }

    /**
     * 是否使用 EventBus
     * Arms 核心库现在并不会依赖某个 EventBus, 要想使用 EventBus, 还请在项目中自行依赖对应的 EventBus
     * 现在支持两种 EventBus, greenrobot 的 EventBus 和畅销书 《Android源码设计模式解析与实战》的作者 何红辉 所作的 AndroidEventBus
     * 确保依赖后, 将此方法返回 true, Arms 会自动检测您依赖的 EventBus, 并自动注册
     * 这种做法可以让使用者有自行选择三方库的权利, 并且还可以减轻 Arms 的体积
     *
     * @return 返回 {@code true} (默认为 {@code true}), Arms 会自动注册 EventBus
     */
    @Override
    public boolean useEventBus() {
        return true;
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
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void setLayoutState_LOADING() {
        mLoadLayout.setLayoutState(State.LOADING);
    }

    @Override
    public void setLayoutState_SUCCESS() {
        mLoadLayout.setLayoutState(State.SUCCESS);
    }

    @Override
    public void setLayoutState_FAILED() {
        if (mLoadLayout.getLayerType() != State.SUCCESS)
            mLoadLayout.setLayoutState(State.FAILED);
    }

    @Override
    public void setLayoutState_NO_DATA() {
        if (mLoadLayout.getLayerType() != State.SUCCESS)
            mLoadLayout.setLayoutState(State.NO_DATA);
    }

    // 含有my_tag,当用户post事件时,只有指定了"my_tag"的事件才会触发该函数,执行在一个独立的线程
    @Subscriber
    protected void getEventBusHub_Fragment(MessageEvent message) {
        if (message.getType().equals(BaseEventBusHub.TAG_LOGIN_SUCCESS))
            initData(savedInstanceState);
    }

}
