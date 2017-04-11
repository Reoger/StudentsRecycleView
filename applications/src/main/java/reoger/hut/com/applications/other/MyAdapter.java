package reoger.hut.com.applications.other;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import reoger.hut.com.applications.R;
import reoger.hut.com.applications.adapter.BaseAdapter;
import reoger.hut.com.applications.bean.BaseViewHolder;

/**
 * Created by 24540 on 2017/4/6.
 */

public class MyAdapter extends BaseAdapter<MyAdapter.ViewHolderPer>{

    private List<Per> datas;
    private LayoutInflater mInflater;

    public MyAdapter(List<Per> datas, Context context) {
        this.datas = datas;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    protected BaseViewHolder onCreateNormalViewHolder(ViewGroup parent, int viewType) {
        View v = mInflater.inflate(R.layout.item_list,parent,false);
        ViewHolderPer per = new ViewHolderPer(v);
        return per;
    }

    @Override
    protected int getDataCount()
    {

        return datas.size();
    }

    class ViewHolderPer extends BaseViewHolder{
        public ImageView imageView;
        public TextView mName;


        public ViewHolderPer(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.image);
            mName = (TextView) itemView.findViewById(R.id.name);
        }

        @Override
        public void onBindViewHolder(int position) {
        imageView.setImageResource(datas.get(position).getIds());
            mName.setText(datas.get(position).getName());
        }

        @Override
        public void OnItemClick(View view, int position) {

        }
    }
}
