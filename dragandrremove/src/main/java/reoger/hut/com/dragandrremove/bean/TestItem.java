package reoger.hut.com.dragandrremove.bean;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import reoger.hut.com.dragandrremove.R;

/**
 * Created by 24540 on 2017/4/2.
 *
 */

public class TestItem extends AdapterItemHolder<Person> {
    public ImageView mImage;
    public TextView mName;

    public TestItem(View itemView) {
        super(itemView);
        mImage = (ImageView) itemView.findViewById(R.id.img);
        mName = (TextView) itemView.findViewById(R.id.name);
    }

    @Override
    public void bindHolder(Person data) {
        mImage.setImageResource(data.getImgId());
        mName.setText(data.getName());



        mImage.setOnClickListener(this);
        mName.setOnClickListener(this);
        mImage.setOnLongClickListener(this);
    }
}
