package reoger.hut.com.applications.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import reoger.hut.com.applications.R;
import reoger.hut.com.applications.bean.BaseViewHolder;
import reoger.hut.com.applications.bean.Persion;


/**
 * Created by 24540 on 2017/4/6.
 */

public class MyAdapter extends BaseAdapter<MyAdapter.PersionViewHolder>{

    private List<Persion> datas;
    private LayoutInflater mInflater;

    public MyAdapter(List<Persion> datas, Context mContext){
        this.datas = datas;
        mInflater = LayoutInflater.from(mContext);
    }



    @Override
    protected BaseViewHolder onCreateNormalViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_list,parent,false);
        PersionViewHolder p = new PersionViewHolder(view);
        return p;
    }

    @Override
    protected int getDataCount() {
        return datas.size();
    }


    class PersionViewHolder extends BaseViewHolder<Persion>{
        public ImageView imageView;
        public TextView name;
        public TextView content;



        public PersionViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.image);
            name = (TextView) itemView.findViewById(R.id.name);
            content = (TextView) itemView.findViewById(R.id.content);

        }

        @Override
        public void onBindViewHolder(int postion) {
//        imageView.setImageResource(data.getImgId());
//        name.setText(data.getName());
//        content.setText(data.getContent());
            //这里还需要想办法传入数据s
            name.setText(datas.get(postion).getName());
        }


        @Override
        public void OnItemClick(View view, int position) {

        }
    }

}
