package reoger.hut.com.applications.adapter;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import reoger.hut.com.applications.R;
import reoger.hut.com.applications.bean.BaseViewHolder;

/**
 * Created by 24540 on 2017/4/6.
 */

public abstract class BaseAdapter<T extends BaseViewHolder> extends RecyclerView.Adapter<BaseViewHolder>{

    protected static final int VIEW_TYPE_LOAD_MORE_FOOTER = 100;
    protected boolean isLoadMoreFooterShown;


    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_LOAD_MORE_FOOTER) {
            return onCreateLoadMoreFooterViewHolder(parent);
        }
        return onCreateNormalViewHolder(parent, viewType);//没有上拉刷新、下拉加载的
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        if (isLoadMoreFooterShown && position == getItemCount() - 1) {
            if (holder.itemView.getLayoutParams() instanceof StaggeredGridLayoutManager.LayoutParams) {
                StaggeredGridLayoutManager.LayoutParams params = (StaggeredGridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
                params.setFullSpan(true);
            }
        }
        holder.onBindViewHolder(position);
    }

    @Override
    public int getItemCount() {
         return getDataCount() + (isLoadMoreFooterShown ? 1 : 0);
    }

    @Override
    public int getItemViewType(int position) {
        if (isLoadMoreFooterShown && position == getItemCount() - 1) {
            return VIEW_TYPE_LOAD_MORE_FOOTER;
        }
        return getDataViewType(position);
    }

    protected BaseViewHolder onCreateLoadMoreFooterViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.widget_pull_to_refresh_footer, parent, false);
        return new LoadMoreFooterViewHolder(view);
    }

    protected int getDataViewType(int position) {
        return 0;
    }


    public void onLoadMoreStateChanged(boolean isShown) {
        this.isLoadMoreFooterShown = isShown;
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


    protected abstract BaseViewHolder onCreateNormalViewHolder(ViewGroup parent, int viewType);
    protected abstract int getDataCount();

    private class LoadMoreFooterViewHolder extends BaseViewHolder <T>{

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

    //这里可以适配GridLayoutManager

}
