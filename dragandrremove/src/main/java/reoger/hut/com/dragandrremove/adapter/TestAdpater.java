package reoger.hut.com.dragandrremove.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import reoger.hut.com.dragandrremove.MainActivity;
import reoger.hut.com.dragandrremove.R;
import reoger.hut.com.dragandrremove.bean.Person;
import reoger.hut.com.dragandrremove.bean.TestItem;

/**
 * Created by 24540 on 2017/4/2.
 *
 */

public class TestAdpater extends BaseRecyclerviewAdapter<TestItem> {

    private List<Person> datas;
    private LayoutInflater mInflater;

    private RecyclerViewIOnClickListener recyclerViewIOnClickListener;

    public TestAdpater(List<Person> datas, Context context){
        this.datas = datas;
        mInflater = LayoutInflater.from(context);
        try {
            recyclerViewIOnClickListener = MainActivity.getInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public TestItem onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = mInflater.inflate(R.layout.item_list,parent,false);
        TestItem  item = new TestItem(v);
        recyclerViewIOnClickListener.addListener(item);
        return item;
    }


    @Override
    public void onBindViewHolder(TestItem holder, int position) {
        holder.bindHolder(datas.get(position));
        holder.mImage.setTag(position);
        holder.mName.setTag(position);

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

}
