package com.hut.reoger.studentsrecycleview.bean;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hut.reoger.studentsrecycleview.R;

/**
 * Created by 24540 on 2017/3/28.
 *
 */

public class ItemHolder extends RecyclerView.ViewHolder{
    public ImageView imageView;
    public TextView teTitle;
    public TextView teContent;


    public ItemHolder(View itemView) {
        super(itemView);
        imageView = (ImageView) itemView.findViewById(R.id.item_image);
        teTitle = (TextView) itemView.findViewById(R.id.item_title);
        teContent = (TextView) itemView.findViewById(R.id.item_content);
    }
}
