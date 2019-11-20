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
package com.lesso.module.me.mvp.ui.adapter;

import android.content.Context;

import com.lesso.module.me.R;
import com.lesso.module.me.mvp.model.entity.CompanyJoinBean;
import com.zhouyou.recyclerview.adapter.HelperRecyclerViewAdapter;
import com.zhouyou.recyclerview.adapter.HelperRecyclerViewHolder;

import java.util.List;

/**
 * =============================================
 * 作    者：贺兴波
 * 时    间：2019/11/15
 * 描    述：
 * =============================================
 */
public class ChoseCompanyJoinListAdapter extends HelperRecyclerViewAdapter<CompanyJoinBean> {

    public ChoseCompanyJoinListAdapter(List<CompanyJoinBean> list, Context context) {
        super(list, context, R.layout.item_layout_chose_company_join_list);
    }

    @Override
    protected void HelperBindData(HelperRecyclerViewHolder viewHolder, int position, CompanyJoinBean item) {
        viewHolder.setText(R.id.tv_company_name, item.getCompanyName());
    }
}
