package reoger.hut.com.strrecyclerview;


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

import reoger.hut.com.strrecyclerview.adapter.RBaseAdapter;
import reoger.hut.com.strrecyclerview.houchHelper.OnItemTouchListener;


/**
 * Created by 24540 on 2017/4/6.
 *
 * recyclerView 动画库：https://github.com/wasabeef/recyclerview-animators
 *
 */

public class RPullRecycler extends FrameLayout implements SwipeRefreshLayout.OnRefreshListener{

    /**提供给外界的接口*/
    public SwipeRefreshLayout getmSwipeRefreshLayout() {
        return mSwipeRefreshLayout;
    }
    public RecyclerView getmRecyclerView() {
        return mRecyclerView;
    }

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    public static final int ACTION_PULL_TO_REFRESH = 1;
    public static final int ACTION_LOAD_MORE_REFRESH = 2;
    public static final int ACTION_IDLE = 0;
    private OnRecyclerRefreshListener listener;
    private OnRecyclerLongPressClick listener2;
    private int mCurrentState = ACTION_IDLE;
    private boolean isLoadMoreEnabled = false;
    private boolean isPullToRefreshEnabled = true;
    private RBaseAdapter adapter;
    private RecyclerView.LayoutManager manager;


    public RPullRecycler(@NonNull Context context) {
        super(context);
        setUpView();
    }

    public RPullRecycler(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setUpView();
    }

    public RPullRecycler(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
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
                    listener.onRefresh(ACTION_LOAD_MORE_REFRESH);//设置下拉刷新
                }
            }
        });
        mRecyclerView.addOnItemTouchListener(new OnItemTouchListener(mRecyclerView) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
            }

            @Override
            public void onItemLongPressClick(RecyclerView.ViewHolder vh) {
              if(vh.getAdapterPosition()!=0)
                  listener2.onSwipAndDrag(vh);
            }
        });
    }

    /***
     * 设置刷新状态
     */
    public void setRefreshing() {
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
                onRefresh();
            }
        });
    }

    public void setAdapter(RBaseAdapter adapter){
        this.adapter = adapter;
        mRecyclerView.setAdapter(adapter);
    }

    public void setLayoutManager(RecyclerView.LayoutManager manager){
        if(manager!=null){
            this.manager = manager;
            mRecyclerView.setLayoutManager(manager);
        }
    }

    /**
     * 设置滑动的监听事件
     * @param listener OnRecyclerRefreshListener
     */
    public void setOnRefreshListener(OnRecyclerRefreshListener listener) {
        if(listener!=null)
        this.listener = listener;
    }

    /**
     * 设置拖拽和滑动的监听器
     * @param listener2 实现接口 this
     */
    public void setRecyclerLongPressClick(OnRecyclerLongPressClick listener2){
        if (listener2 != null)
            this.listener2 = listener2;
    }

    /**
     * 设置是否需要加载更多
     * @param enable true 表示需要加载跟多 false 表示不需要加载更多
     *               默认值：false
     */
    public void enableLoadMore(boolean enable) {
        isLoadMoreEnabled = enable;
    }

    /**
     * 设置是否需要下拉刷新
     * @param enable true 表示需要下拉刷新 false 表示不需要
     *               默认值：true
     */
    public void enablePullToRefresh(boolean enable) {
        isPullToRefreshEnabled = enable;
        mSwipeRefreshLayout.setEnabled(enable);
    }

    /**
     * 添加间隔线
     * @param itemDecoration RecyclerView.ItemDecoration
     *                       参考值：new DividerItemDecoration(this,DividerItemDecoration.VERTICAL)
     */
    public void addItemDecoration(RecyclerView.ItemDecoration itemDecoration){
        if(itemDecoration!=null)
            mRecyclerView.addItemDecoration(itemDecoration);
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

    /**
     * 指定mRecyclerView移动到制定的位置
     * @param position 移动到的位置
     */
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

    public interface OnRecyclerLongPressClick{
        void onSwipAndDrag(RecyclerView.ViewHolder vh);
    }


}
