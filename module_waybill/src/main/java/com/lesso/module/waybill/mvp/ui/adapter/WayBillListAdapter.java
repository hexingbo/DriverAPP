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
import android.widget.ImageView;

import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.utils.ArmsUtils;
import com.lesso.module.waybill.R;
import com.lesso.module.waybill.mvp.model.entity.WayBillListBean;
import com.zhouyou.recyclerview.adapter.HelperRecyclerViewAdapter;
import com.zhouyou.recyclerview.adapter.HelperRecyclerViewHolder;

import java.util.List;

import me.jessyan.armscomponent.commonres.enums.WayBillStateType;
import me.jessyan.armscomponent.commonsdk.imgaEngine.config.CommonImageConfigImpl;

/**
 * =============================================
 * 作    者：贺兴波
 * 时    间：2019/11/15
 * 描    述：订单列表适配器
 * =============================================
 */
public class WayBillListAdapter extends HelperRecyclerViewAdapter<WayBillListBean> {

    private WayBillStateType stateType;

    private AppComponent mAppComponent;
    private ImageLoader mImageLoader;//用于加载图片的管理类,默认使用 Glide,使用策略模式,可替换框架
    private ImageView imgHead;

    public WayBillListAdapter(List<WayBillListBean> list, WayBillStateType stateType, Context context) {
        super(list, context, R.layout.item_layout_waybill_list);
        this.stateType = stateType;
        //可以在任何可以拿到 Context 的地方,拿到 AppComponent,从而得到用 Dagger 管理的单例对象
        mAppComponent = ArmsUtils.obtainAppComponentFromContext(context);
        mImageLoader = mAppComponent.imageLoader();
    }

    @Override
    protected void HelperBindData(HelperRecyclerViewHolder viewHolder, int position, WayBillListBean item) {
        viewHolder.setText(R.id.tv_name, item.getGoodsName());
        viewHolder.setText(R.id.tv_order_number, item.getOrderNo());
        viewHolder.setText(R.id.tv_car_number, item.getCarNo());
        viewHolder.setText(R.id.tv_carrier, item.getLogisticsCompany());
        viewHolder.setText(R.id.tv_send_address, item.getShipperAddress());
        viewHolder.setText(R.id.tv_send_company, item.getShipper());
        viewHolder.setText(R.id.tv_receive_address, item.getReceivingPartyAddress());
        viewHolder.setText(R.id.tv_receive_company, item.getReceivingParty());
        imgHead = viewHolder.getView(R.id.img_head);
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(v, item, position);
                }
            }
        };
        viewHolder.setOnClickListener(R.id.tv_fahuo, listener);
        viewHolder.setOnClickListener(R.id.tv_shouhuo, listener);
        viewHolder.setOnClickListener(R.id.tv_daka, listener);
        if (stateType != null) {
            switch (stateType) {
                case A:
                    viewHolder.setVisible(R.id.ll_order_state_fahuo, true);
                    break;
                case B:
                    viewHolder.setVisible(R.id.ll_order_state_shouhuo, true);
                    break;
                case F:
                    break;
            }
        }
        if (!ArmsUtils.isEmpty(item.getShipperCompanyHeadUrl()))
            mImageLoader.loadImage(viewHolder.itemView.getContext(), CommonImageConfigImpl
                    .builder()
                    .url(item.getShipperCompanyHeadUrl())
                    .errorPic(R.mipmap.ic_head_default)
                    .placeholder(R.mipmap.ic_head_default)
                    .transformation(new CircleCrop())
                    .imageView(imgHead)
                    .build());

    }

    /**
     * 在 Activity 的 onDestroy 中使用 {@link this #releaseAllHolder(RecyclerView)} 方法 (super.onDestroy() 之前)
     * {@link this #onRelease()} 才会被调用, 可以在此方法中释放一些资源
     */
    public void onRelease() {
        if (imgHead != null)
            mImageLoader.clear(mAppComponent.application(), CommonImageConfigImpl.builder()
                    .imageViews(imgHead)
                    .build());
    }
}
