package reoger.hut.com.strrecyclerview.bean;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by 24540 on 2017/4/2.
 */

public abstract class AdapterItemHolder<T> extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener{




    private OnRecyclerItemClickListener mOnRecyclerItemClickListener;
    private OnRecyclerItemLongClickListener mOnRecyclerItemLongClickListener;

    public OnRecyclerItemClickListener getmOnRecyclerItemClickListener() {
        return mOnRecyclerItemClickListener;
    }

    public OnRecyclerItemLongClickListener getmOnRecyclerItemLongClickListener() {
        return mOnRecyclerItemLongClickListener;
    }

    public interface OnRecyclerItemClickListener{
        void onItemClickListener(View v,int pos);
    }

    public interface OnRecyclerItemLongClickListener{
        void onItemLongClickListener(View v,int pos);
    }

    public  void SetOnRecyclerItemClickListener(OnRecyclerItemClickListener onRecyclerItemClickListener){
        this.mOnRecyclerItemClickListener = onRecyclerItemClickListener;
    }

    public void SetOnRecyclerItemLongClickListener(OnRecyclerItemLongClickListener mOnRecyclerItemLongClickListener){
        this.mOnRecyclerItemLongClickListener = mOnRecyclerItemLongClickListener;
    }


    public abstract void addListener();

    public  abstract void bindHolder(T data);

    public AdapterItemHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void onClick(View v) {
        if(mOnRecyclerItemClickListener!=null)
            mOnRecyclerItemClickListener.onItemClickListener(v, (Integer) v.getTag());
    }

    @Override
    public boolean onLongClick(View v) {
        if(mOnRecyclerItemLongClickListener!=null){
            mOnRecyclerItemLongClickListener.onItemLongClickListener(v, (Integer) v.getTag());
            return true;
        }
        return false;
    }
}
