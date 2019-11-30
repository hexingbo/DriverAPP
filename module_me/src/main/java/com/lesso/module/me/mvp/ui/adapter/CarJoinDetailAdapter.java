package com.lesso.module.me.mvp.ui.adapter;

import android.content.Context;

import com.lesso.module.me.R;
import com.lesso.module.me.mvp.model.entity.CarJoinBean;
import com.zhouyou.recyclerview.adapter.HelperRecyclerViewHolder;

import java.util.ArrayList;

/**
 * @author EDZ
 */
public class CarJoinDetailAdapter extends CarJoinAdapter {
    private OnOperateListener onOperateListener;

    public CarJoinDetailAdapter(Context context) {
        super(new ArrayList<>(), context, R.layout.item_layout_car_join);
    }

    @Override
    protected void HelperBindData(HelperRecyclerViewHolder vh, int position, CarJoinBean item) {
        bindBaseData(vh, item);

        vh.setText(R.id.tv_state, item.getCompVerifyStatusDesc());
        vh.setVisible(R.id.tv_error, false);

        switch(item.getCompVerifyStatus()) {
            case CarJoinBean.STATE_PASSED:
                vh.setText(R.id.btn, mContext.getString(R.string.module_me_quit));
                vh.setOnClickListener(R.id.btn, v -> {
                    if(onOperateListener != null) {
                        onOperateListener.onQuit(item, position);
                    }
                });
                break;
            case CarJoinBean.STATE_REJECTED:
                vh.setText(R.id.btn, mContext.getString(R.string.module_me_resubmit));
                vh.setVisible(R.id.tv_error, true);
                vh.setText(R.id.tv_error, item.getCompVerifyRemark());
                vh.setOnClickListener(R.id.btn, v -> {
                    if(onOperateListener != null) {
                        onOperateListener.onResubmit(item, position);
                    }
                });
                break;
            case CarJoinBean.STATE_REVIEWING:
                vh.setText(R.id.btn, mContext.getString(R.string.module_me_cancel));
                vh.setOnClickListener(R.id.btn, v -> {
                    if(onOperateListener != null) {
                        onOperateListener.onCancel(item, position);
                    }
                });
                break;
            default:
                break;
        }
    }

    public void setOnOperateListener(OnOperateListener onOperateListener) {
        this.onOperateListener = onOperateListener;
    }

    public interface OnOperateListener {
        void onCancel(CarJoinBean item, int position);

        void onQuit(CarJoinBean item, int position);

        void onResubmit(CarJoinBean item, int position);
    }
}
