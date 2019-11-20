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
package com.lesso.module.message.mvp.ui.adapter;

import android.content.Context;

import com.lesso.module.message.R;
import com.lesso.module.message.mvp.model.entity.MessageBean;
import com.zhouyou.recyclerview.adapter.HelperRecyclerViewAdapter;
import com.zhouyou.recyclerview.adapter.HelperRecyclerViewHolder;

import java.util.List;

/**
 * =============================================
 * 作    者：贺兴波
 * 时    间：2019/11/15
 * 描    述：消息列表适配器
 * =============================================
 */
public class MessageListAdapter extends HelperRecyclerViewAdapter<MessageBean> {

    public MessageListAdapter(List<MessageBean> list, Context context) {
        super(list, context, R.layout.item_layout_message_list);
    }

    @Override
    protected void HelperBindData(HelperRecyclerViewHolder viewHolder, int position, MessageBean item) {
        viewHolder.setText(R.id.tv_title, item.getTitle());
        viewHolder.setText(R.id.tv_content, item.getContent());
        viewHolder.setVisible(R.id.img_haveRead, item.getHaveRead() == 0 ? true : false);
    }
}
