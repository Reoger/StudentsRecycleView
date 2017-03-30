package com.hut.reoger.studentsrecycleview.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hut.reoger.studentsrecycleview.R;
import com.hut.reoger.studentsrecycleview.bean.InfoBean;

import java.util.List;

/**
 * Created by 24540 on 2017/3/28.
 * 添加header和footer
 */

public class MyAdapterWith extends RecyclerView.Adapter<MyAdapterWith.ItemHolder> {
    public static final int TYPE_HEADER = 0;  //说明是带有Header的
    public static final int TYPE_FOOTER = 1;  //说明是带有Footer的
    public static final int TYPE_NORMAL = 2;  //说明是不带有header和footer的


    private LayoutInflater mInflater;
    private List<InfoBean> datas;


    private View mHeaderView;
    private View mFooterView;


    public MyAdapterWith(Context mContext, List<InfoBean> datas) {
        this.datas = datas;
        mInflater = LayoutInflater.from(mContext);
    }

    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
        notifyItemInserted(0);
    }

    public void setFooterView(View footerView) {
        mFooterView = footerView;
        notifyItemInserted(getItemCount() - 1);
    }


    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        //header和foot相关
        if (mHeaderView != null && viewType == TYPE_HEADER) {
            return new ItemHolder(mHeaderView);
        }
        if (mFooterView != null && viewType == TYPE_FOOTER) {
            return new ItemHolder(mFooterView);
        }

        View v = mInflater.inflate(R.layout.item_list, parent, false);
        ItemHolder itemHolder = new ItemHolder(v);


        return itemHolder;
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {
        if (getItemViewType(position) == TYPE_HEADER) return;
        else if (getItemViewType(position) == TYPE_FOOTER) return;
        else {
            if (holder instanceof MyAdapterWith.ItemHolder) {
                position = holder.getLayoutPosition();
                position = mHeaderView == null ? position : position - 1;
                //计算当前的位置，如果添加了header的话，header也需要占用一个位置
                if (position < datas.size()) {
                    holder.imageView.setImageResource(datas.get(position).getId());
                    holder.teTitle.setText(datas.get(position).getTitle());
                    holder.teContent.setText(datas.get(position).getContent());
                }
            }
        }
    }


    @Override
    public int getItemCount() {
        if (mHeaderView == null && mFooterView == null) {
            return datas.size();
        } else if (mHeaderView == null && mFooterView != null) {
            return datas.size() + 1;
        } else if (mHeaderView != null && mFooterView == null) {
            return datas.size() + 1;
        } else {
            return datas.size() + 2;
        }
    }

    /**
     * 通过重写这个方法，绑定不同的view
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        if (mHeaderView == null && mFooterView == null) {
            return TYPE_NORMAL;
        }
        if (position == 0) {
            return TYPE_HEADER;
        }
        if (position == getItemCount() - 1) {
            return TYPE_FOOTER;
        }
        return TYPE_NORMAL;
    }


    public class ItemHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView teTitle;
        public TextView teContent;


        public ItemHolder(View itemView) {
            super(itemView);
            if (itemView == mHeaderView)
                return;
            if (itemView == mFooterView)
                return;

            imageView = (ImageView) itemView.findViewById(R.id.item_image);
            teTitle = (TextView) itemView.findViewById(R.id.item_title);
            teContent = (TextView) itemView.findViewById(R.id.item_content);
        }
    }

    /**
     * 解决gridLayout时，header和footer被当作一个item的问题
     *
     * @param recyclerView
     */
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager == null) Log.d("TAG", "manager=null");

        if (manager instanceof GridLayoutManager) {

            final GridLayoutManager gridLayoutManager = (GridLayoutManager) manager;
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (getItemViewType(position) == TYPE_HEADER || getItemViewType(position) == TYPE_FOOTER)
                        return gridLayoutManager.getSpanCount();
                    else
                        return 1;
                }
            });
        }
    }

    @Override
    public void onViewAttachedToWindow(ItemHolder holder) {
        super.onViewAttachedToWindow(holder);
        ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
        if (lp != null
                && lp instanceof StaggeredGridLayoutManager.LayoutParams) {
            StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
            if (mHeaderView != null)
                p.setFullSpan(holder.getLayoutPosition() == 0);
//            if(mFooterView!=null)
//                 p.setFullSpan(holder.getLayoutPosition() ==datas.size());
        }
    }
}
