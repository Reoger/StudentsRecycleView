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
 * 添加点击事件：方法三，在adapter中添加接口
 * 如果想为item的子view添加监听事件，可以在onBindViewHolder方法中为需要监听的view添加监听事件
 */

public class MyAdapter extends RecyclerView.Adapter<ItemHolder> implements View.OnClickListener {


    private LayoutInflater mInflater;
    private List<InfoBean> datas;

    private OnRecyclerViewItemClickListener mOnItenClickListener = null;
    private OnRecyclerViewItemLongClickListener mOnItemLongClickListener = null;





    public MyAdapter(Context mContext, List<InfoBean> datas) {
        this.datas = datas;
        mInflater = LayoutInflater.from(mContext);
    }




    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {



        View v = mInflater.inflate(R.layout.item_list,parent,false);
        ItemHolder itemHolder = new ItemHolder(v);


        v.setOnClickListener(this);
        v.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(mOnItemLongClickListener!=null)
                mOnItemLongClickListener.OnItemLongClickListener(v, (Integer) v.getTag());
                return false;
            }
        });
        return itemHolder;
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {

            holder.imageView.setImageResource(datas.get(position).getId());
            holder.teTitle.setText(datas.get(position).getTitle());
            holder.teContent.setText(datas.get(position).getContent());



        //如果有必要，可以在这里为view的部件添加监听事件。
        //形如：holder.teTitle.setOnClickListener(this);



        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {

        return datas.size();
    }



    @Override
    public void onClick(View v) {
        if(mOnItemLongClickListener !=null)
        mOnItenClickListener.OnItemClickListener(v, (Integer) v.getTag());
    }


    public  interface OnRecyclerViewItemClickListener{
        void OnItemClickListener(View view,int position);
    }

    public   interface OnRecyclerViewItemLongClickListener{
        void OnItemLongClickListener(View view,int position);
    }


    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener){
        this.mOnItenClickListener = listener;
    }

    public void setOnItemLongClickListener(OnRecyclerViewItemLongClickListener listener){
        this.mOnItemLongClickListener = listener;
    }


}
