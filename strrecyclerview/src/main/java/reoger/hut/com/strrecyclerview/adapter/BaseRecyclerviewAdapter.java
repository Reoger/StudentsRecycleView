package reoger.hut.com.strrecyclerview.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import reoger.hut.com.strrecyclerview.bean.AdapterItemHolder;

/**
 * Created by 24540 on 2017/4/2.
 */

public abstract class BaseRecyclerviewAdapter<T extends  AdapterItemHolder> extends RecyclerView.Adapter<T>{
    private OnClickListener onClickListener;
    private View.OnLongClickListener onLongClickListener;


    @Override
    public abstract T onCreateViewHolder(ViewGroup parent, int viewType);

    @Override
    public abstract void onBindViewHolder(T holder, int position) ;

    public  void onBindViewHolder(RecyclerView.ViewHolder holder, int position){
        onBindViewHolder(holder,position);
    };

    public void setOnClickListener(OnClickListener onClickListener){
        this.onClickListener = onClickListener;
    }

    public void setOnLongClickListener(View.OnLongClickListener onLongClickListener){
        this.onLongClickListener = onLongClickListener;
    }


    public interface OnClickListener{
        void onClickListener(View v, int pos);
        void onTextClickListener(View v,int pos);
    }

    public interface onLongClickListener{
        void OnLaogClickListener(View v,int pos);
    }
}
