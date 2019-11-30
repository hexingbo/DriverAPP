package com.lesso.module.waybill.mvp.contract;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.lesso.module.waybill.mvp.model.entity.OrderAccountBean;
import com.lesso.module.waybill.mvp.model.entity.SubmitOrderSaveAccountsBean;
import com.lesso.module.waybill.mvp.ui.adapter.OrderAccountListAdapter;

import java.util.List;

import io.reactivex.Observable;
import me.jessyan.armscomponent.commonres.enums.OrderAccountStateType;
import me.jessyan.armscomponent.commonsdk.base.bean.HttpResult;
import retrofit2.http.Body;


/**
 * ================================================
 * user：贺兴波
 * 2019/11/19 14:34
 * Description:
 * ================================================
 */
public interface OrderAccountsChildContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {

        void endLoadMore();

        OrderAccountStateType getOrderAccountStateType();

        Activity getActivity();

        OrderAccountListAdapter.OnAdapterViewClickListener getOnAdapterViewClickListener();

        void setStartTimeValue(String time);

        void setEndTimeValue(String time);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        /**
         * 获取运单列表数据
         *
         * @param current
         * @param size
         * @param driverId
         * @param stateType
         * @return
         */
        Observable<HttpResult<List<OrderAccountBean>>> getOrderAccounts(int current, int size, @NonNull OrderAccountStateType stateType,
                                                                        @NonNull String driverId, String condition,
                                                                        String driverDateStart, String driverDateEnd);

        Observable<HttpResult> postOrderSaveAccounts(@Nullable String orderId, @Nullable String driverId, @Nullable String driverDateStart, @Nullable String driverDateEnd);
    }
}
