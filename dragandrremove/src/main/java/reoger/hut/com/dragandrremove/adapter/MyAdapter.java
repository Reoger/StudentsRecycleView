package reoger.hut.com.dragandrremove.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import reoger.hut.com.dragandrremove.R;
import reoger.hut.com.dragandrremove.bean.ItemPerson;
import reoger.hut.com.dragandrremove.bean.Person;

/**
 * Created by 24540 on 2017/4/1.
 *
 */

public class MyAdapter extends RecyclerView.Adapter<ItemPerson> implements View.OnTouchListener{
    private LayoutInflater mInflater;
    private List<Person> datas = new ArrayList<>();

    private onItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;



    public MyAdapter(List<Person> datas, Context context){
        this.datas = datas;
        mInflater = LayoutInflater.from(context);
    }


    @Override
    public ItemPerson onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = mInflater.inflate(R.layout.item_list,parent,false);
        ItemPerson itemPerson = new ItemPerson(v);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnItemClickListener!=null){
                    mOnItemClickListener.OnclickListener(v, (Integer) v.getTag());
                }
            }
        });
        v.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(mOnItemLongClickListener!=null)
                mOnItemLongClickListener.onLongClickListener(v, (Integer) v.getTag(),false);
                return false;
            }
        });
        return itemPerson;
    }

    @Override
    public void onBindViewHolder(ItemPerson holder, int position) {
        holder.mName.setText(datas.get(position).getName());
        holder.mImage.setImageResource(datas.get(position).getImgId());

        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        return false;
    }

    public interface onItemClickListener{
        void OnclickListener(View v,int postion);
    }

    public interface OnItemLongClickListener{
        void onLongClickListener(View v,int postion,boolean flag);
    }

    public  void setOnItemClickListener(onItemClickListener onItemClickListener){
        if(mOnItemClickListener==null)
        this.mOnItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener){
        if(mOnItemLongClickListener==null)
        this.mOnItemLongClickListener = onItemLongClickListener;
    }




}
