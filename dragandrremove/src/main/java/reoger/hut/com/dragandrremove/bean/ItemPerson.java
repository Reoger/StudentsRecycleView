package reoger.hut.com.dragandrremove.bean;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import reoger.hut.com.dragandrremove.R;

/**
 * Created by 24540 on 2017/4/1.
 *
 */

public class ItemPerson extends RecyclerView.ViewHolder {
    public ImageView mImage;
    public TextView mName;

    public ItemPerson(View itemView) {
        super(itemView);
        mImage = (ImageView) itemView.findViewById(R.id.img);
        mName = (TextView) itemView.findViewById(R.id.name);
    }
}
