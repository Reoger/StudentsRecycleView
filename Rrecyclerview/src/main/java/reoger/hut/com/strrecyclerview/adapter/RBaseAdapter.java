package reoger.hut.com.strrecyclerview.adapter;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import reoger.hut.com.strrecyclerview.R;
import reoger.hut.com.strrecyclerview.bean.RBaseViewHolder;
import reoger.hut.com.strrecyclerview.houchHelper.MyItemTouchHelpCallback;


/**
 * Created by 24540 on 2017/4/6.
 * 基础的adapter类，实现添加header、footer的主要功能
 */

public abstract class RBaseAdapter<T extends RBaseViewHolder> extends RecyclerView.Adapter<RBaseViewHolder>  implements MyItemTouchHelpCallback.OnItemTouchCallbackListener{

    protected static final int VIEW_TYPE_LOAD_MORE_FOOTER = 100;//上拉刷新时出现的提示、正在刷新

    protected static final int VIEW_TYPE_FOOTER_ONLY = 0x110;//添加footer
    protected static final int VIEW_TYPE_HEADER_ONLY = 0x111;//添加header

    public static int Item_Count=0;

    protected List<T> mDatas;

    protected boolean isLoadMoreFooterShown;//是否需要显示正在加载 true表示显示


    protected View mFooterView;
    protected View mHeaderView;

    //这三个布尔值用于控制header、footer和加载更多三个view不能被拖拽和删除
    //true表示加载了view
    public static boolean HeaderView = false;
    public static boolean FooterView = false;
    public static boolean LoadMoreView = false;



    @Override
    public RBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_LOAD_MORE_FOOTER) {//添加footer，用来提示当前正在加载更多
            LoadMoreView = true;
            return onCreateLoadMoreFooterViewHolder(parent);
        }
        if (mHeaderView != null && viewType == VIEW_TYPE_HEADER_ONLY) {//添加headerView
            return new LoadHeaderViewHolder(mHeaderView);
        } else if (mFooterView != null && viewType == VIEW_TYPE_FOOTER_ONLY) {//添加footerView
            return new LoadHeaderViewHolder(mFooterView);
        }

        return onCreateNormalViewHolder(parent, viewType);//没有上拉刷新、下拉加载的
    }


    @Override
    public void onBindViewHolder(RBaseViewHolder holder, int position) {
        if (isLoadMoreFooterShown && position == getItemCount() - 1) {
            if (holder.itemView.getLayoutParams() instanceof StaggeredGridLayoutManager.LayoutParams) {
                StaggeredGridLayoutManager.LayoutParams params = (StaggeredGridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
                params.setFullSpan(true);
            }
        }

        if (getItemViewType(position) == VIEW_TYPE_HEADER_ONLY) return;//添加header
        else if (getItemViewType(position) == VIEW_TYPE_FOOTER_ONLY) return;//添加footer
        position = holder.getLayoutPosition();
        position = (mHeaderView == null) ? position : position - 1;
        //如果有header的话，需要将当前位置想前移动一位。
        holder.onBindViewHolder(position);
    }

    @Override
    public int getItemCount() {
        if (mHeaderView == null && mFooterView == null) {
            Item_Count = getDataCount() + (isLoadMoreFooterShown ? 1 : 0);
            return Item_Count;
        } else if (mHeaderView == null && mFooterView != null) {
            Item_Count =  getDataCount() + (isLoadMoreFooterShown ? 1 : 0) + 1;
            return Item_Count;
        } else if (mHeaderView != null && mFooterView == null) {
            return getDataCount() + (isLoadMoreFooterShown ? 1 : 0) + 1;
        } else {
            Item_Count = getDataCount() + (isLoadMoreFooterShown ? 1 : 0) + 2;
            return Item_Count;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (isLoadMoreFooterShown && position == getItemCount() - 1) {
            return VIEW_TYPE_LOAD_MORE_FOOTER;
        }

        if (mHeaderView != null && position == 0) {
            HeaderView = true;
            return VIEW_TYPE_HEADER_ONLY;
        }
        if (mFooterView != null) {
            FooterView = true;
            if (isLoadMoreFooterShown && position == getItemCount() - 2) {
                return VIEW_TYPE_FOOTER_ONLY;
            } else if (!isLoadMoreFooterShown && position == getItemCount() - 1) {
                return VIEW_TYPE_FOOTER_ONLY;
            }
        }
        return getDataViewType(position);
    }

    public abstract void addHeaderView(View headerView);

    public abstract void addFooterView(View footerView);

    protected RBaseViewHolder onCreateLoadMoreFooterViewHolder(ViewGroup parent) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.widget_pull_to_refresh_footer, parent, false);
        return new LoadMoreFooterViewHolder(v);
    }


    protected int getDataViewType(int position) {
        return 0;
    }


    public void onLoadMoreStateChanged(boolean isShown) {
        this.isLoadMoreFooterShown = isShown;
        LoadMoreView = isShown;
        if (isShown) {
            notifyItemInserted(getItemCount());
        } else {
            notifyItemRemoved(getItemCount());
        }
    }


    public boolean isLoadMoreFooter(int position) {
        return isLoadMoreFooterShown && position == getItemCount() - 1;
    }

    public boolean isSectionHeader(int position) {
        return false;
    }


    protected abstract RBaseViewHolder onCreateNormalViewHolder(ViewGroup parent, int viewType);

    protected abstract int getDataCount();

    private class LoadMoreFooterViewHolder extends RBaseViewHolder<T> {

        public LoadMoreFooterViewHolder(View view) {
            super(view);
        }

        @Override
        public void onBindViewHolder(int position) {

        }

        @Override
        public void OnItemClick(View view, int position) {

        }

    }

    private class LoadHeaderViewHolder extends RBaseViewHolder<T> {


        public LoadHeaderViewHolder(View itemView) {
            super(itemView);
            if (itemView == mHeaderView) {
                return;
            }

            if (itemView == mFooterView) {
                return;
            }

        }

        @Override
        public void onBindViewHolder(int position) {

        }

        @Override
        public void OnItemClick(View view, int position) {

        }
    }


    @Override
    public void onViewAttachedToWindow(RBaseViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
        if (lp != null
                && lp instanceof StaggeredGridLayoutManager.LayoutParams) {
            StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
            if (mHeaderView != null)
                p.setFullSpan(holder.getLayoutPosition() == 0);
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
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) manager;
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (getItemViewType(position) == VIEW_TYPE_HEADER_ONLY || getItemViewType(position) == VIEW_TYPE_FOOTER_ONLY || getItemViewType(position) == VIEW_TYPE_LOAD_MORE_FOOTER)
                        return gridLayoutManager.getSpanCount();
                    else
                        return 1;
                }
            });
        }
    }


}
