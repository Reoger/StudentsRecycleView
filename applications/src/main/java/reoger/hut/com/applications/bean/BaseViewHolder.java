package reoger.hut.com.applications.bean;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

/**
 * Created by 24540 on 2017/4/6.
 */

public abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder{

     List<T> datas;

    public BaseViewHolder(View itemView) {
        super(itemView);
      
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }



    // public abstract void onBindViewHolder(T data);
    public abstract void onBindViewHolder(int  position);

    public abstract void OnItemClick(View view,int position);
}
