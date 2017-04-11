package reoger.hut.com.applications;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import reoger.hut.com.applications.adapter.MyAdapter;
import reoger.hut.com.applications.bean.Persion;

public class MainActivity extends AppCompatActivity implements PullRecycler.OnRecyclerRefreshListener {

    private PullRecycler pullRecycler;
    private MyAdapter myAdapter;
    private List<Persion> datas;

    private RecyclerView.LayoutManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pullRecycler = (PullRecycler) findViewById(R.id.pullRecycler);
        manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        myAdapter = new MyAdapter(initData(), this);
        pullRecycler.setOnRefreshListener(this);
        pullRecycler.setLayoutManager(manager);
        pullRecycler.setAdpater(myAdapter);
        pullRecycler.enableLoadMore(true);
        pullRecycler.enablePullToRefresh(true);


    }

    private List<Persion> initData() {
        datas = new ArrayList<>();
        Persion item = new Persion();
        for (int i = 0; i < 10; i++) {
            item.setImgId(i);
            item.setName("名字" + i);
            item.setContent("内容");
            datas.add(item);
        }
        return datas;
    }

    @Override
    public void onRefresh(int action) {
        if (action == PullRecycler.ACTION_PULL_TO_REFRESH) {//下拉刷新
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    updateData();
                    pullRecycler.onRefreshCompleted();
                }
            }, 3000);


        } else if (action == PullRecycler.ACTION_LOAD_MORE_REFRESH) {//加载更多
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Log.d("TAG", "正在加载数据");
                    addData();
                    pullRecycler.onRefreshCompleted();
                }
            }, 3000);


        }
    }

    private void addData() {
        List<Persion> tt = new ArrayList<>();
        Persion item = new Persion();
        for (int i = 0; i < 10; i++) {
            item.setImgId(i);
            item.setName("新增的明天" + i);
            item.setContent("新增的内柔");
            tt.add(item);
        }
        datas.addAll(tt);
        myAdapter.notifyDataSetChanged();
    }

    private void updateData() {
        List<Persion> tt = new ArrayList<>();
        Persion item = new Persion();
        for (int i = 0; i < 10; i++) {
            item.setImgId(i);
            item.setName("更新的明天" + i);
            item.setContent("更新的内柔");
            tt.add(item);
        }
        datas.addAll(0, tt);
        myAdapter.notifyDataSetChanged();
    }
}
