package reoger.hut.com.testuser;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import reoger.hut.com.strrecyclerview.RPullRecycler;
import reoger.hut.com.strrecyclerview.houchHelper.MyItemTouchHelper;
import reoger.hut.com.strrecyclerview.myDecoraltion.RDividerItemDecoration;
import reoger.hut.com.testuser.adapter.MyAdapter;
import reoger.hut.com.testuser.bean.Per;

import static reoger.hut.com.strrecyclerview.utils.Utils.CanTouchCurrentItem;
import static reoger.hut.com.testuser.R.layout.footer_view;
import static reoger.hut.com.testuser.R.layout.header_view;

public class MainActivity extends AppCompatActivity implements RPullRecycler.OnRecyclerRefreshListener, RPullRecycler.OnRecyclerLongPressClick {

    private RPullRecycler mpullRecycler;

    private MyAdapter myAdapter;

    private List<Per> datas;

    private LinearLayoutManager manager;

    private MyItemTouchHelper itemTouchHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        manager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
       // manager = new GridLayoutManager(this,2);
        initData();
    }

    private void initData() {
        mpullRecycler = (RPullRecycler) findViewById(R.id.pullclerView);
        mpullRecycler.setLayoutManager(manager);//设置这个必须放在setAdapter之前
        myAdapter = new MyAdapter(getData(),this);
        mpullRecycler.setOnRefreshListener(this);
        mpullRecycler.setRecyclerLongPressClick(this);

        mpullRecycler.setAdapter(myAdapter);


        mpullRecycler.enableLoadMore(true);


        itemTouchHelper = new MyItemTouchHelper(myAdapter);
        itemTouchHelper.attachToRecyclerView(mpullRecycler);
        itemTouchHelper.setSwipeEnable(true);//设置可以删除


        View v = LayoutInflater.from(this).inflate(header_view,mpullRecycler,false);
        myAdapter.addHeaderView(v);//添加headView
        View v2 = LayoutInflater.from(this).inflate(footer_view,mpullRecycler,false);
        myAdapter.addFooterView(v2);//添加footerView


        //添加分割线，默认系统效果。如果需要实现自己的效果，可以在
        mpullRecycler.addItemDecoration(new RDividerItemDecoration(this, RDividerItemDecoration.VERTICAL));//添加间隔



    }


    private List<Per> getData(){
        datas = new ArrayList<>();
        int []ids ={R.mipmap.img1,R.mipmap.img2,R.mipmap.img3,R.mipmap.img4};
        String []names ={"占山","历史","王五","斩六"};
        for(int j=0;j<2;j++)
            for(int i=0;i<4;i++){
                Per item = new Per();
                item.setIds(ids[i]);
                item.setName(names[i]);
                datas.add(item);
            }
        return datas;
    }

    @Override
    public void onRefresh(int action) {
        if(action == RPullRecycler.ACTION_PULL_TO_REFRESH){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                   myAdapter.loadMoreData(loadData());
                    mpullRecycler.onRefreshCompleted();
                }
            },3000);
        }
        if(action == RPullRecycler.ACTION_LOAD_MORE_REFRESH){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    mpullRecycler.onRefreshCompleted();
                }
            },3000);
        }
    }

    private List<Per> loadData(){
        for(int i=0;i<4;i++){
            Per item = new Per();
            item.setName("新增的"+i);
            datas.add(0,item);
        }
        return datas;
    }

    @Override
    public void onSwipAndDrag(RecyclerView.ViewHolder vh) {
        if(CanTouchCurrentItem(vh))
        itemTouchHelper.startDrag(vh);
    }

}
