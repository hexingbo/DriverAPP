package com.lesso.module.me.mvp.contract;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;
import com.lesso.module.me.mvp.model.entity.CompanyJoinBean;

import java.util.List;

import io.reactivex.Observable;
import me.jessyan.armscomponent.commonres.enums.CompanyActionType;
import me.jessyan.armscomponent.commonsdk.base.bean.HttpResult;


/**
 * ================================================
 * user：贺兴波
 * 2019/11/22 09:41
 * Description:
 * ================================================
 */
public interface CompanyRecommendContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {

        Activity getActivity();

        void endLoadMore();
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {

        Observable<HttpResult<List<CompanyJoinBean>>> getRecommendCompanyJoinList(int pageSize);

        Observable<HttpResult> postCompanyJoin(@NonNull String companyId, @NonNull String driverId, @NonNull String joinType);
    }
}
