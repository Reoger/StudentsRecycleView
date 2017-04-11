package reoger.hut.com.applications;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import reoger.hut.com.applications.adapter.BaseAdapter;

/**
 * Created by 24540 on 2017/4/6.
 *
 */

public class PullRecycler  extends FrameLayout implements SwipeRefreshLayout.OnRefreshListener{
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    public static final int ACTION_PULL_TO_REFRESH = 1;
    public static final int ACTION_LOAD_MORE_REFRESH = 2;
    public static final int ACTION_IDLE = 0;
    private OnRecyclerRefreshListener listener;
    private int mCurrentState = ACTION_IDLE;
    private boolean isLoadMoreEnabled = false;
    private boolean isPullToRefreshEnabled = true;
    private BaseAdapter adapter;
    private RecyclerView.LayoutManager manager;


    public PullRecycler(@NonNull Context context) {
        super(context);
        setUpView();
    }

    public PullRecycler(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setUpView();
    }

    public PullRecycler(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setUpView();
    }


    private boolean checkIfNeedLoadMore() {

        if(manager instanceof LinearLayoutManager){
            LinearLayoutManager l = (LinearLayoutManager) manager;
            int losPos = l.findLastVisibleItemPosition();
            if(losPos>=l.getItemCount()-1)
                return true;
            else
                return false;
        }else if(manager instanceof GridLayoutManager){
            GridLayoutManager g = (GridLayoutManager) manager;
            int lost = g.findLastCompletelyVisibleItemPosition();
            if(lost>=g.getItemCount()-1)
                return true;
            else
                return false;

        }
       return false;
    }

    private void setUpView() {
        LayoutInflater.from(getContext()).inflate(R.layout.widget_pull_to_refresh, this, true);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (mCurrentState == ACTION_IDLE && isLoadMoreEnabled && checkIfNeedLoadMore()) {
                    mCurrentState = ACTION_LOAD_MORE_REFRESH;
                    adapter.onLoadMoreStateChanged(true);
                    mSwipeRefreshLayout.setEnabled(false);
                    listener.onRefresh(ACTION_LOAD_MORE_REFRESH);
                }
            }
        });
    }

    public void setRefreshing() {
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
                onRefresh();
            }
        });
    }

    public void setAdpater(BaseAdapter adpater){
        this.adapter = adpater;
        mRecyclerView.setAdapter(adpater);

    }
    public void setLayoutManager(RecyclerView.LayoutManager manager){
        this.manager = manager;
        mRecyclerView.setLayoutManager(manager);
    }
    public void setOnRefreshListener(OnRecyclerRefreshListener listener) {
        this.listener = listener;
    }

    public void enableLoadMore(boolean enable) {
        isLoadMoreEnabled = enable;
    }

    public void enablePullToRefresh(boolean enable) {
        isPullToRefreshEnabled = enable;
        mSwipeRefreshLayout.setEnabled(enable);
    }


    public void onRefreshCompleted() {//告知数据加载完毕
        switch (mCurrentState) {
            case ACTION_PULL_TO_REFRESH:
                mSwipeRefreshLayout.setRefreshing(false);
                break;
            case ACTION_LOAD_MORE_REFRESH:
                adapter.onLoadMoreStateChanged(false);
                if (isPullToRefreshEnabled) {
                    mSwipeRefreshLayout.setEnabled(true);
                }
                break;
        }
        mCurrentState = ACTION_IDLE;
    }

    public void setSelection(int position) {
        mRecyclerView.scrollToPosition(position);
    }

    @Override
    public void onRefresh() {
        mCurrentState = ACTION_PULL_TO_REFRESH;
        if(listener!=null)
        listener.onRefresh(ACTION_PULL_TO_REFRESH);
    }

    public interface OnRecyclerRefreshListener {
        void onRefresh(int action);
    }

}
