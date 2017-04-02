package reoger.hut.com.strrecyclerview.bean;

import android.view.View;
import android.widget.TextView;

import reoger.hut.com.strrecyclerview.R;

/**
 * Created by 24540 on 2017/4/2.
 */

public class itemView extends AdapterItemHolder<ll> {

    private TextView mText;

    public itemView(View itemView) {
        super(itemView);
        mText = (TextView) itemView.findViewById(R.id.text3);
    }

    @Override
    public void addListener() {

    }

    @Override
    public void bindHolder(ll data) {
        mText.setOnClickListener(this);
    }
}
