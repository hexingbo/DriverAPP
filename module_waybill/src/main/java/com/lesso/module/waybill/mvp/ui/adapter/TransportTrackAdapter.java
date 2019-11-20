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
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.lesso.module.waybill.R;
import com.lesso.module.waybill.mvp.model.entity.WayBillDetailBean;
import com.zhouyou.recyclerview.adapter.HelperRecyclerViewAdapter;
import com.zhouyou.recyclerview.adapter.HelperRecyclerViewHolder;

import java.util.List;

/**
 * =============================================
 * 作    者：贺兴波
 * 时    间：2019/11/15
 * 描    述：运输轨迹适配器
 * =============================================
 */
public class TransportTrackAdapter extends HelperRecyclerViewAdapter<WayBillDetailBean.TransportTrackBean> {

    private String orderStatus;

    public TransportTrackAdapter(List<WayBillDetailBean.TransportTrackBean> list, String orderStatus, Context context) {
        super(list, context, R.layout.item_layout_waybill_detail_transporttrack);
        this.orderStatus = orderStatus;
    }

    @Override
    protected void HelperBindData(HelperRecyclerViewHolder viewHolder, int position, WayBillDetailBean.TransportTrackBean item) {
        TextView tvAddress = viewHolder.getView(R.id.tv_address);
        TextView tvData = viewHolder.getView(R.id.tv_date);
        tvAddress.setText(item.getTransportRemark());
        tvData.setText(item.getDate());
        viewHolder.setText(R.id.tv_time, item.getTime());
        viewHolder.setVisible(R.id.iv_line, position == getItemCount() - 1 ? false : true);

        if (position == 0) {
            viewHolder.setVisible(R.id.iv_gray, true);
            viewHolder.setVisible(R.id.iv_gray, false);
            tvAddress.setEnabled(true);
            tvData.setEnabled(true);
            viewHolder.setImageResource(R.id.iv_complete, !TextUtils.isEmpty(orderStatus) && orderStatus.equals("F") ? R.mipmap.ic_complete : R.mipmap.ic_address);
        } else {
            viewHolder.setVisible(R.id.iv_complete, false);
            viewHolder.setVisible(R.id.iv_gray, true);
            tvAddress.setEnabled(false);
            tvData.setEnabled(false);
        }

    }
}
