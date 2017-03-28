package com.hut.reoger.studentsrecycleview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hut.reoger.studentsrecycleview.R;
import com.hut.reoger.studentsrecycleview.bean.InfoBean;
import com.hut.reoger.studentsrecycleview.bean.ItemHolder;

import java.util.List;

/**
 * Created by 24540 on 2017/3/28.
 */

public class MyAdapter extends RecyclerView.Adapter<ItemHolder> {
    private LayoutInflater mInflater;
    private List<InfoBean> datas;

    public MyAdapter(Context mContext, List<InfoBean> datas) {
        this.datas = datas;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = mInflater.inflate(R.layout.item_list,parent,false);
        ItemHolder itemHolder = new ItemHolder(v);
        return itemHolder;
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {
        holder.imageView.setImageResource(datas.get(position).getId());
        holder.teTitle.setText(datas.get(position).getTitle());
        holder.teContent.setText(datas.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }
}
