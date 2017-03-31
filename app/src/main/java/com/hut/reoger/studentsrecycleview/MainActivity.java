package com.hut.reoger.studentsrecycleview;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;

import com.hut.reoger.studentsrecycleview.adapter.MyAdapterWith;
import com.hut.reoger.studentsrecycleview.addClickListener.DropDownListener;
import com.hut.reoger.studentsrecycleview.bean.InfoBean;
import com.hut.reoger.studentsrecycleview.myDecoraltion.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {


    //private MyAdapter mMyAdapter;
    private MyAdapterWith mMyAdapter;


    private SwipeRefreshLayout mSwipRefreshLayout;
    private RecyclerView.LayoutManager mManager;

    private LinearLayoutManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


       // manager = new LinearLayoutManager(this);


        mSwipRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recycler);
        mManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        //mManager = new GridLayoutManager(this,2);

        //mManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mManager);

        //mMyAdapter = new MyAdapter(this, getData());
        mMyAdapter = new MyAdapterWith(this,getData());

        mRecyclerView.setAdapter(mMyAdapter);


        View view  = LayoutInflater.from(this).inflate(R.layout.head,mRecyclerView,false);
        mMyAdapter.setHeaderView(view);
        View footer = LayoutInflater.from(this).inflate(R.layout.foot,mRecyclerView,false);
        mMyAdapter.setFooterView(footer);

        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.HORIZONAL));

        //addItemClickListener(mRecyclerView,4);

        initmSwipRefreshLayout(mRecyclerView);
    }

    private void initmSwipRefreshLayout( RecyclerView mRecyclerView) {
        mSwipRefreshLayout.setProgressBackgroundColorSchemeResource(android.R.color.white);
        mSwipRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_red_light,android.R.color.holo_orange_light,
                android.R.color.holo_green_light);
        mSwipRefreshLayout.setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
                        .getDisplayMetrics()));
        mSwipRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){
            //模仿加载了五条数据
            @Override
            public void onRefresh() {
               new Handler().postDelayed(new Runnable() {
                   @Override
                   public void run() {
                     loadMoreData();
                   }
               },2000);
                mSwipRefreshLayout.setRefreshing(false);
            }
        });
        mRecyclerView.addOnScrollListener(new DropDownListener((LinearLayoutManager) mManager) {
            @Override
            public void onLoadMore(int currentPage) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadMoreData();
                    }
                },2000);
            }
        });
    }

    //添加item点击事件的监听
//    private void addItemClickListener(RecyclerView mRecyclerView,int type) {
//
//       if(type==1){//方法1
//           mRecyclerView.addOnItemTouchListener(new ItemClickListener(mRecyclerView, new ItemClickListener.OnItemClickListener() {
//               @Override
//               public void onItemClick(View view, int position) {
//                   Toast.makeText(MainActivity.this, "通过方法1实现的点击事件" + position, Toast.LENGTH_SHORT).show();
//               }
//
//               @Override
//               public void onItemLongClick(View view, int position) {
//                   Toast.makeText(MainActivity.this, "通过方法1实现的点击事件" + position, Toast.LENGTH_SHORT).show();
//               }
//           }));
//       }else if(type==2){//方法2
//           ItemClickSupport.addTo(mRecyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
//               @Override
//               public void onItemClicked(RecyclerView recyclerView, int position, View v) {
//                    Toast.makeText(MainActivity.this,"通过方法2实现的点击事件"+position,Toast.LENGTH_SHORT).show();
//               }
//           });
//           ItemClickSupport.addTo(mRecyclerView).setOnItemLongClickListener(new ItemClickSupport.OnItemLongClickListener() {
//               @Override
//               public boolean onItemLongClicked(RecyclerView recyclerView, int position, View v) {
//                   Toast.makeText(MainActivity.this,"通过方法2实现的点击事件长安"+position,Toast.LENGTH_SHORT).show();
//                   return false;
//               }
//           });
//       }else if(type==3){//方法3
//           mMyAdapter.setOnItemClickListener(new MyAdapter.OnRecyclerViewItemClickListener() {
//               @Override
//               public void OnItemClickListener(View view, int position) {
//                   Toast.makeText(MainActivity.this,"通过方法3实现的点击事件"+position,Toast.LENGTH_SHORT).show();
//               }
//           });
//           mMyAdapter.setOnItemLongClickListener(new MyAdapter.OnRecyclerViewItemLongClickListener() {
//               @Override
//               public void OnItemLongClickListener(View view, int position) {
//                   Toast.makeText(MainActivity.this,"通过方法3实现的点击事件长安"+position,Toast.LENGTH_SHORT).show();
//               }
//           });
//       }else if(type ==4){//方法2的变种，实现对item的子view的点击事件
//           ItemClickSupportAdd.addTo(mRecyclerView).setOnItemClickListener(new ItemClickSupportAdd.OnItemClickListener() {
//               @Override
//               public void onItemImageClicked(RecyclerView recyclerView, int position, View v) {
//                    Toast.makeText(MainActivity.this,"图片"+position,Toast.LENGTH_SHORT).show();
//               }
//
//               @Override
//               public void onItemTitleClicked(RecyclerView recyclerView, int position, View v) {
//                   Toast.makeText(MainActivity.this,"title"+position,Toast.LENGTH_SHORT).show();
//               }
//
//               @Override
//               public void onItemContentClicked(RecyclerView recyclerView, int position, View v) {
//                   Toast.makeText(MainActivity.this,"content"+position,Toast.LENGTH_SHORT).show();
//               }
//           });
//       }
//
//
//
//
//    }

    private List<InfoBean> getData() {
        List<InfoBean> datas = new ArrayList<>();
        int[] id = {R.mipmap.img1, R.mipmap.img2, R.mipmap.img3, R.mipmap.img4};
        for (int j = 0; j < 2; j++) {
            for (int i = 0; i < 4; i++) {
                InfoBean info = new InfoBean();
                info.setId(id[i]);
                info.setContent("这里显示的是内容"+i*j);
                info.setTitle("这里显示的是标题");
                datas.add(info);
            }
        }
        return datas;
    }

    private void loadMoreData(){
        List<InfoBean> datas= new ArrayList<>();
        for(int i=0;i<3;i++){
            InfoBean info = new InfoBean();
            info.setId(R.mipmap.ic_launcher_round);
            info.setContent("新增的内容"+i);
            info.setTitle("新增的内容");
            datas.add(info);
        }
        mMyAdapter.loadMoreData(datas);
    }

}
