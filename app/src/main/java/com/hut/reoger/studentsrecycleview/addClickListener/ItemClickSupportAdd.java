package com.hut.reoger.studentsrecycleview.addClickListener;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.hut.reoger.studentsrecycleview.R;
import com.hut.reoger.studentsrecycleview.bean.ItemHolder;

/**
 * Created by 24540 on 2017/3/28.
 * 为Item添加点击事件：方法二：利用OnChildAttachStateChangeListener来实现
 * 参考链接：http://www.littlerobots.nl/blog/Handle-Android-RecyclerView-Clicks/
 */

public class ItemClickSupportAdd {
    private final RecyclerView mRecyclerView;
    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                RecyclerView.ViewHolder holder = mRecyclerView.findContainingViewHolder(v);
                if(holder!=null){
                    if(v.getId()==R.id.item_image){
                        mOnItemClickListener.onItemImageClicked(mRecyclerView, holder.getAdapterPosition(), v);
                    }else if(v.getId()==R.id.item_title){
                        mOnItemClickListener.onItemTitleClicked(mRecyclerView, holder.getAdapterPosition(), v);
                    }else if(v.getId()==R.id.item_content){
                        mOnItemClickListener.onItemContentClicked(mRecyclerView, holder.getAdapterPosition(), v);
                    }
                }

            }
        }
    };

    private View.OnLongClickListener mOnLongClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            if (mOnItemLongClickListener != null) {
                RecyclerView.ViewHolder holder = mRecyclerView.getChildViewHolder(v);//相应的，如果想监听子view的点击事件，可以在这里增加
                return mOnItemLongClickListener.onItemLongClicked(mRecyclerView, holder.getAdapterPosition(), v);
            }
            return false;
        }
    };

    private RecyclerView.OnChildAttachStateChangeListener mAttachListener
            = new RecyclerView.OnChildAttachStateChangeListener() {
        @Override
        public void onChildViewAttachedToWindow(View view) {
            if (mOnItemClickListener != null) {
                view.setOnClickListener(mOnClickListener);
                RecyclerView.ViewHolder viewHolder = mRecyclerView.getChildViewHolder(view);
                if(viewHolder instanceof ItemHolder){
                    ItemHolder info = (ItemHolder) viewHolder;
                    info.imageView.setOnClickListener(mOnClickListener);
                    info.teTitle.setOnClickListener(mOnClickListener);
                    info.teContent.setOnClickListener(mOnClickListener);
                }

            }
            if (mOnItemLongClickListener != null) {
                view.setOnLongClickListener(mOnLongClickListener);
            }
        }

        @Override
        public void onChildViewDetachedFromWindow(View view) {}
    };

    private ItemClickSupportAdd(RecyclerView recyclerView) {
        mRecyclerView = recyclerView;
        mRecyclerView.setTag(R.id.item_click_support, this);
        mRecyclerView.addOnChildAttachStateChangeListener(mAttachListener);
    }

    public static ItemClickSupportAdd addTo(RecyclerView view) {
        ItemClickSupportAdd support = (ItemClickSupportAdd) view.getTag(R.id.item_click_support);
        if (support == null) {
            support = new ItemClickSupportAdd(view);
        }
        return support;
    }

    public static ItemClickSupportAdd removeFrom(RecyclerView view) {
        ItemClickSupportAdd support = (ItemClickSupportAdd) view.getTag(R.id.item_click_support);
        if (support != null) {
            support.detach(view);
        }
        return support;
    }

    public ItemClickSupportAdd setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
        return this;
    }

    public ItemClickSupportAdd setOnItemLongClickListener(OnItemLongClickListener listener) {
        mOnItemLongClickListener = listener;
        return this;
    }

    private void detach(RecyclerView view) {
        view.removeOnChildAttachStateChangeListener(mAttachListener);
        view.setTag(R.id.item_click_support, null);
    }

    public interface OnItemClickListener {

        void onItemImageClicked(RecyclerView recyclerView, int position, View v);
        void onItemTitleClicked(RecyclerView recyclerView, int position, View v);
        void onItemContentClicked(RecyclerView recyclerView, int position, View v);
    }

    public interface OnItemLongClickListener {
        boolean onItemLongClicked(RecyclerView recyclerView, int position, View v);
    }
}
