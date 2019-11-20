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

import com.lesso.module.waybill.R;
import com.lesso.module.waybill.mvp.model.entity.OrderAccountBean;
import com.zhouyou.recyclerview.adapter.HelperRecyclerViewAdapter;
import com.zhouyou.recyclerview.adapter.HelperRecyclerViewHolder;

import java.util.List;

import me.jessyan.armscomponent.commonres.enums.OrderAccountStateType;

/**
 * =============================================
 * 作    者：贺兴波
 * 时    间：2019/11/15
 * 描    述：对账单列表适配器
 * =============================================
 */
public class OrderAccountListAdapter extends HelperRecyclerViewAdapter<OrderAccountBean> {

    private OrderAccountStateType stateType;

    public OrderAccountListAdapter(List<OrderAccountBean> list, OrderAccountStateType stateType, Context context) {
        super(list, context, R.layout.item_layout_order_account_list);
        this.stateType = stateType;
    }

    @Override
    protected void HelperBindData(HelperRecyclerViewHolder viewHolder, int position, OrderAccountBean item) {
        viewHolder.setText(R.id.tv_name, item.getGoodsName());
        viewHolder.setText(R.id.tv_order_number, item.getCarNo());
        viewHolder.setText(R.id.tv_car_number, item.getCarNo());
        viewHolder.setText(R.id.tv_carrier, item.getLogisticsCompany());
        viewHolder.setText(R.id.tv_send_address, item.getShipperAddress());
        viewHolder.setText(R.id.tv_send_company, item.getShipper());
        viewHolder.setText(R.id.tv_receive_address, item.getReceivingPartyAddress());
        viewHolder.setText(R.id.tv_receive_company, item.getReceivingParty());
        viewHolder.getView(R.id.ll_line_1).setVisibility(View.INVISIBLE);
        viewHolder.getView(R.id.ll_line_2).setVisibility(View.INVISIBLE);

    }
}
