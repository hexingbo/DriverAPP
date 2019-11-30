package com.lesso.module.me.mvp.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.jess.arms.utils.AppManagerUtil;
import com.lesso.module.me.R;
import com.lesso.module.me.mvp.model.entity.CarJoinBean;
import com.lesso.module.me.mvp.ui.activity.AddCarsDetailInfoActivity;
import com.zhouyou.recyclerview.adapter.HelperRecyclerViewHolder;
import com.zhouyou.recyclerview.adapter.HelperStateRecyclerViewAdapter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author :hexingbo
 * @Date :2019/11/17
 * @FileName： CompanyJoinedAdapter
 * @Describe :
 */
public class CarJoinAdapter extends HelperStateRecyclerViewAdapter<CarJoinBean> {

    private boolean enableSelect;

    public CarJoinAdapter(List<CarJoinBean> list, Context context) {
        this(list, context, false);
    }

    public CarJoinAdapter(List<CarJoinBean> data, Context context, int... layoutId) {
        super(data, context, layoutId);
    }

    public CarJoinAdapter(List<CarJoinBean> list, Context context, boolean enableSelect) {
        super(list, context, R.layout.item_layout_car_join_select);
        this.enableSelect = enableSelect;
    }

    @Override
    protected void HelperBindData(HelperRecyclerViewHolder vh, int position, CarJoinBean item) {
        bindBaseData(vh, item);

        if (enableSelect) {
            CheckBox cbSelected = vh.getView(R.id.cb_selected);
            cbSelected.setChecked(item.isSelected());
            vh.setVisible(R.id.rl_select, true);
            cbSelected.setClickable(false);

            vh.getItemView().setOnClickListener(v -> {
                item.setSelected(!item.isSelected());
                cbSelected.setChecked(item.isSelected());
            });
        }
    }

    protected void bindBaseData(HelperRecyclerViewHolder vh, CarJoinBean item) {
        vh.setText(R.id.tv_car_no, item.getCarNo());
        vh.setText(R.id.tv_car_type, item.getCarType());
        vh.setText(R.id.tv_car_length, item.getCarLong() + mContext.getString(R.string.module_me_meter));
        vh.setText(R.id.tv_car_volume, item.getCarSize() + mContext.getString(R.string.module_me_cublic_meter));
        vh.setText(R.id.tv_car_load, item.getDeadWeight() + mContext.getString(R.string.module_me_ton));
        vh.setText(R.id.tv_car_insurance_deadline, item.getInsuranceFormatDate());
        vh.setOnClickListener(R.id.iv_more, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Serializable> map = new HashMap<>();
                map.put(AddCarsDetailInfoActivity.IntentValue, item.getGuid());
                map.put(AddCarsDetailInfoActivity.IntentValue_Action, false);
                AppManagerUtil.jump(AddCarsDetailInfoActivity.class, map);
            }
        });
    }

    @Override
    public View getEmptyView(ViewGroup parent) {
        return mLInflater.inflate(R.layout.view_custom_empty_data, parent, false);
    }

    @Override
    public View getErrorView(ViewGroup parent) {
        return mLInflater.inflate(R.layout.view_custom_data_error, parent, false);
    }

    @Override
    public View getLoadingView(ViewGroup parent) {
        return mLInflater.inflate(R.layout.view_custom_loading_data, parent, false);
    }

}
