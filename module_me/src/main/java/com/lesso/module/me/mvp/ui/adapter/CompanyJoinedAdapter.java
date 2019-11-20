package com.lesso.module.me.mvp.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.lesso.module.me.R;
import com.lesso.module.me.mvp.model.entity.CompanyJoinBean;
import com.lesso.module.me.mvp.model.entity.CompanyJoinedBean;
import com.zhouyou.recyclerview.adapter.HelperRecyclerViewAdapter;
import com.zhouyou.recyclerview.adapter.HelperRecyclerViewHolder;

import java.util.List;

/**
 * @Author :hexingbo
 * @Date :2019/11/17
 * @FileName： CompanyJoinedAdapter
 * @Describe :
 */
public class CompanyJoinedAdapter extends HelperRecyclerViewAdapter<CompanyJoinedBean> {

    public CompanyJoinedAdapter(List<CompanyJoinedBean> list, Context context) {
        super(list, context, R.layout.item_layout_company_joined_list);
    }

    @Override
    protected void HelperBindData(HelperRecyclerViewHolder viewHolder, int position, CompanyJoinedBean item) {
        viewHolder.setText(R.id.tv_company_name, item.getCompanyName());
        viewHolder.setText(R.id.tv_joined_time, item.getJoinDate());
        TextView tvStatus = viewHolder.getView(R.id.tv_audit_Status);
        tvStatus.setText(item.getAuditStatus());
        //0：物流公司邀请司机；1：司机加盟物流公司
        // B:审核中、待确认；F:审核不通过、拒绝；D：审核通过、同意
        if (item.getJoinSource() == 0) {
            switch (item.getAuditStatusCode()) {
                case "B"://审核中
                    tvStatus.setTextColor(tvStatus.getResources().getColor(R.color.color_shenhezhong));
                    viewHolder.setVisible(R.id.tv_agree, true);
                    viewHolder.setVisible(R.id.tv_refuse, true);
                    break;
                case "F"://审核不通过
                    tvStatus.setTextColor(tvStatus.getResources().getColor( R.color.color_shenhe_weitongguo));
                    break;
                case "D"://审核通过
                    tvStatus.setTextColor(tvStatus.getResources().getColor( R.color.color_shenhe_tongguo));
                    viewHolder.setVisible(R.id.tv_est, true);
                    break;
            }
        } else {
            switch (item.getAuditStatusCode()) {
                case "B"://待确认
                    tvStatus.setTextColor(tvStatus.getResources().getColor( R.color.color_daiqueren));
                    viewHolder.setVisible(R.id.tv_cancel, true);
                    break;
                case "F"://拒绝
                    tvStatus.setTextColor(tvStatus.getResources().getColor( R.color.color_jujue));
                    break;
                case "D"://同意
                    tvStatus.setTextColor(tvStatus.getResources().getColor( R.color.color_tongyi));
                    viewHolder.setVisible(R.id.tv_est, true);
                    break;
            }
        }

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener!=null){
                    mOnItemClickListener.onItemClick(v,item,position);
                }
            }
        };

        viewHolder.setOnClickListener(R.id.tv_agree, listener);
        viewHolder.setOnClickListener(R.id.tv_refuse, listener);
        viewHolder.setOnClickListener(R.id.tv_cancel, listener);
        viewHolder.setOnClickListener(R.id.tv_est, listener);

    }


}
