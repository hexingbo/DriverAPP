package com.lesso.module.waybill.mvp.contract;

import android.app.Activity;
import android.support.v4.app.FragmentManager;

import com.jess.arms.base.BaseLazyLoadFragment;
import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.lesso.module.waybill.mvp.model.entity.UpdateDetailBean;

import java.util.ArrayList;

import io.reactivex.Observable;
import me.jessyan.armscomponent.commonsdk.base.bean.HttpResult;


/**
 * ================================================
 * user：贺兴波
 * 2019/11/13 22:22
 * Description:
 * ================================================
 */
public interface WayBillManagerContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {

        Activity getActivity();

        FragmentManager getFragmentManager();

        void onCheckVersion(boolean isNewVersion, UpdateDetailBean.AndroidBean android);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {

        String[] getTitles();

        ArrayList<BaseLazyLoadFragment> getFragments();

        Observable<HttpResult<UpdateDetailBean>> checkVersionDetail(String appSource);
    }
}
