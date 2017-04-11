package reoger.hut.com.dragandrremove;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import reoger.hut.com.strrecyclerview.util.BGANormalRefreshViewHolder;
import reoger.hut.com.strrecyclerview.util.BGARefreshLayout;
import reoger.hut.com.strrecyclerview.util.BGARefreshViewHolder;

public class NextActivity extends AppCompatActivity implements BGARefreshLayout.BGARefreshLayoutDelegate{

    private BGARefreshLayout mRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wecome);

        initRefreshLayout();
    }

    private void initRefreshLayout() {
        mRefreshLayout = (BGARefreshLayout) findViewById(R.id.rl_modulename_refresh);
        // 为BGARefreshLayout 设置代理
        mRefreshLayout.setDelegate(this);
        // 设置下拉刷新和上拉加载更多的风格     参数1：应用程序上下文，参数2：是否具有上拉加载更多功能
        BGARefreshViewHolder refreshViewHolder = new BGANormalRefreshViewHolder(this, true);
        // 设置下拉刷新和上拉加载更多的风格
        mRefreshLayout.setRefreshViewHolder(refreshViewHolder);


        // 为了增加下拉刷新头部和加载更多的通用性，提供了以下可选配置选项  -------------START
        // 设置正在加载更多时不显示加载更多控件
        // mRefreshLayout.setIsShowLoadingMoreView(false);
        // 设置正在加载更多时的文本
        refreshViewHolder.setLoadingMoreText("加载中");
        // 设置整个加载更多控件的背景颜色资源 id
        refreshViewHolder.setLoadMoreBackgroundColorRes(Color.blue(001));
        // 设置整个加载更多控件的背景 drawable 资源 id
        refreshViewHolder.setLoadMoreBackgroundDrawableRes(R.mipmap.bga_refresh_loading01);
        // 设置下拉刷新控件的背景颜色资源 id
        refreshViewHolder.setRefreshViewBackgroundColorRes(Color.blue(255));
        // 设置下拉刷新控件的背景 drawable 资源 id
        refreshViewHolder.setRefreshViewBackgroundDrawableRes(R.drawable.bga_refresh_loding);
        // 设置自定义头部视图（也可以不用设置）     参数1：自定义头部视图（例如广告位）， 参数2：上拉加载更多是否可用
       // mRefreshLayout.setCustomHeaderView(mBanner, false);
        // 可选配置  -------------END
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mRefreshLayout.endLoadingMore();
        //loadData
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mRefreshLayout.endLoadingMore();
        //loadData
        return false;
    }
}
