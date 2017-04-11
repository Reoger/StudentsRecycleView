package reoger.hut.com.strrecyclerview.bean;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

/**
 * Created by 24540 on 2017/4/6.
 * 基础的bean对象，实现对item的监听
 */

public  abstract class RBaseViewHolder<T> extends RecyclerView.ViewHolder {
    List<T> datas;

    public RBaseViewHolder(View itemView) {
        super(itemView);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            OnItemClick(v,getAdapterPosition());
            }
        });
    }



    // public abstract void onBindViewHolder(T data);
    public abstract void onBindViewHolder(int  position);

    public abstract void OnItemClick(View view,int position);
}
