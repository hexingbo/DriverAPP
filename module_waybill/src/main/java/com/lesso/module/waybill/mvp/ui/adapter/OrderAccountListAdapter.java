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
package com.lesso.module.waybill.mvp.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.alibaba.android.arouter.launcher.ARouter;
import com.lesso.module.waybill.R;
import com.lesso.module.waybill.mvp.model.entity.OrderAccountBean;
import com.zhouyou.recyclerview.adapter.HelperRecyclerViewHolder;
import com.zhouyou.recyclerview.adapter.HelperStateRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import me.jessyan.armscomponent.commonres.enums.OrderAccountStateType;
import me.jessyan.armscomponent.commonsdk.core.RouterHub;
import me.jessyan.armscomponent.commonsdk.utils.Utils;

/**
 * =============================================
 * 作    者：贺兴波
 * 时    间：2019/11/15
 * 描    述：对账单列表适配器
 * * 1.实现具体的getEmptyView，getErrorView，getLoadingView,虽然多了几个方法，但是代码更整洁清晰了，也利于你想定制任意的页面状态<br>
 * * 2.如果你的每个页面状态都是一样的，不想每个页面都去实现getEmptyView，getErrorView，getLoadingView，可以自己再封装一个baseAdapter,处理公共的状态页面<br>
 * =============================================
 */
public class OrderAccountListAdapter extends HelperStateRecyclerViewAdapter<OrderAccountBean> {

    private OrderAccountStateType stateType;
    private OnAdapterViewClickListener onAdapterViewClickListener;
    private boolean selectedAll;

    private ArrayList<OrderAccountBean> selectedList;

    public ArrayList<OrderAccountBean> getSelectedList() {
        if (selectedList != null) {
            selectedList.clear();
            for (OrderAccountBean bean : getList()) {
                if (bean.isSelected()) {
                    selectedList.add(bean);
                }
            }
        }

        return selectedList;
    }

    public void setSelectedAll(boolean selectedAll) {
        for (OrderAccountBean bean : getList()) {
            bean.setSelected(selectedAll);
        }
        this.selectedAll = selectedAll;
        notifyDataSetChanged();
    }

    public boolean isSelectedAll() {
        selectedAll = true;
        for (OrderAccountBean bean : getList()) {
            if (!bean.isSelected()) {
                selectedAll = false;
                break;
            }
        }
        return selectedAll;
    }

    public void setOnAdapterViewClickListener(OnAdapterViewClickListener onAdapterViewClickListener) {
        this.onAdapterViewClickListener = onAdapterViewClickListener;
    }

    public OrderAccountListAdapter(List<OrderAccountBean> data, OrderAccountStateType stateType, Context context) {
        super(data, context, R.layout.item_layout_order_account_list);
        this.stateType = stateType;
        selectedList = new ArrayList<>();
    }

    @Override
    protected void HelperBindData(HelperRecyclerViewHolder viewHolder, int position, OrderAccountBean item) {
        viewHolder.setText(R.id.tv_name, item.getGoodsName());
        viewHolder.setText(R.id.tv_order_number, item.getTransportCosts());
        viewHolder.setText(R.id.tv_car_number, item.getCarNo());
        viewHolder.setText(R.id.tv_carrier, item.getLogisticsCompany());
        viewHolder.setText(R.id.tv_send_address, item.getShipperAddress());
        viewHolder.setText(R.id.tv_send_company, item.getShipper());
        viewHolder.setText(R.id.tv_receive_address, item.getReceivingPartyAddress());
        viewHolder.setText(R.id.tv_receive_company, item.getReceivingParty());
        viewHolder.getView(R.id.ll_line_1).setVisibility(View.INVISIBLE);
        viewHolder.getView(R.id.ll_line_2).setVisibility(View.INVISIBLE);
        CheckBox cbAll = viewHolder.getView(R.id.cb_all);
        cbAll.setChecked(item.isSelected());
        cbAll.setVisibility(stateType == OrderAccountStateType.Z ? View.VISIBLE : View.GONE);


        if (stateType == OrderAccountStateType.Z) {
            View.OnClickListener clickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    item.setSelected(!item.isSelected());
                    cbAll.setChecked(item.isSelected());
                    if (onAdapterViewClickListener != null) {
                        onAdapterViewClickListener.onCheckedItemChanged(isSelectedAll(), getSelectedList());
                    }
                }
            };
            viewHolder.setOnClickListener(R.id.rl_top, clickListener);
            cbAll.setOnClickListener(clickListener);

        }

        viewHolder.getView(R.id.ll_item).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onAdapterViewClickListener != null) {
                    onAdapterViewClickListener.onCheckedItem(position, item);
                }
            }
        });

    }

    @Override
    public View getEmptyView(ViewGroup parent) {
        return mLInflater.inflate(R.layout.view_custom_empty_data, parent, false);
    }

    @Override
    public View getErrorView(ViewGroup parent) {
        View loadingView = mLInflater.inflate(R.layout.layout_user_un_authorized, parent, false);
        loadingView.findViewById(R.id.tv_authoriz).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(RouterHub.Me_UserAuthenticationActivity)
                        .withBoolean("isComeInFromTheMainStart", true)
                        .navigation(v.getContext());
            }
        });
        return loadingView;
    }

    @Override
    public View getLoadingView(ViewGroup parent) {
        return mLInflater.inflate(R.layout.view_custom_loading_data, parent, false);
    }

    public interface OnAdapterViewClickListener {
        void onCheckedItemChanged(boolean isSelectedAll, ArrayList<OrderAccountBean> selectedList);

        void onCheckedItem(int position, OrderAccountBean item);
    }

}
