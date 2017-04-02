package reoger.hut.com.strrecyclerview.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

import reoger.hut.com.strrecyclerview.bean.AdapterItemHolder;
import reoger.hut.com.strrecyclerview.bean.itemView;
import reoger.hut.com.strrecyclerview.bean.ll;

/**
 * Created by 24540 on 2017/4/2.
 */

public class MyAdapter2 extends RecyclerView.Adapter<itemView> {

    private List<ll> datas;

    @Override
    public itemView onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(itemView holder, int position) {
        ((AdapterItemHolder)holder).bindHolder(datas.get(position));
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
