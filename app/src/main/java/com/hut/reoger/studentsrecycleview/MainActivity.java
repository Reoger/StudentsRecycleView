package com.hut.reoger.studentsrecycleview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.hut.reoger.studentsrecycleview.adapter.MyAdapter;
import com.hut.reoger.studentsrecycleview.addClickListener.ItemClickListener;
import com.hut.reoger.studentsrecycleview.addClickListener.ItemClickSupport;
import com.hut.reoger.studentsrecycleview.addClickListener.ItemClickSupportAdd;
import com.hut.reoger.studentsrecycleview.bean.InfoBean;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private MyAdapter mMyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recycler);
        RecyclerView.LayoutManager mManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mMyAdapter = new MyAdapter(this, getData());

        mRecyclerView.setAdapter(mMyAdapter);
        mRecyclerView.setLayoutManager(mManager);


        addItemClickListener(mRecyclerView,4);
    }

    //添加item点击事件的监听
    private void addItemClickListener(RecyclerView mRecyclerView,int type) {

       if(type==1){//方法1
           mRecyclerView.addOnItemTouchListener(new ItemClickListener(mRecyclerView, new ItemClickListener.OnItemClickListener() {
               @Override
               public void onItemClick(View view, int position) {
                   Toast.makeText(MainActivity.this, "通过方法1实现的点击事件" + position, Toast.LENGTH_SHORT).show();
               }

               @Override
               public void onItemLongClick(View view, int position) {
                   Toast.makeText(MainActivity.this, "通过方法1实现的点击事件" + position, Toast.LENGTH_SHORT).show();
               }
           }));
       }else if(type==2){//方法2
           ItemClickSupport.addTo(mRecyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
               @Override
               public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                    Toast.makeText(MainActivity.this,"通过方法2实现的点击事件"+position,Toast.LENGTH_SHORT).show();
               }
           });
           ItemClickSupport.addTo(mRecyclerView).setOnItemLongClickListener(new ItemClickSupport.OnItemLongClickListener() {
               @Override
               public boolean onItemLongClicked(RecyclerView recyclerView, int position, View v) {
                   Toast.makeText(MainActivity.this,"通过方法2实现的点击事件长安"+position,Toast.LENGTH_SHORT).show();
                   return false;
               }
           });
       }else if(type==3){//方法3
           mMyAdapter.setOnItemClickListener(new MyAdapter.OnRecyclerViewItemClickListener() {
               @Override
               public void OnItemClickListener(View view, int position) {
                   Toast.makeText(MainActivity.this,"通过方法3实现的点击事件"+position,Toast.LENGTH_SHORT).show();
               }
           });
           mMyAdapter.setOnItemLongClickListener(new MyAdapter.OnRecyclerViewItemLongClickListener() {
               @Override
               public void OnItemLongClickListener(View view, int position) {
                   Toast.makeText(MainActivity.this,"通过方法3实现的点击事件长安"+position,Toast.LENGTH_SHORT).show();
               }
           });
       }else if(type ==4){//方法2的变种，实现对item的子view的点击事件
           ItemClickSupportAdd.addTo(mRecyclerView).setOnItemClickListener(new ItemClickSupportAdd.OnItemClickListener() {
               @Override
               public void onItemImageClicked(RecyclerView recyclerView, int position, View v) {
                    Toast.makeText(MainActivity.this,"图片"+position,Toast.LENGTH_SHORT).show();
               }

               @Override
               public void onItemTitleClicked(RecyclerView recyclerView, int position, View v) {
                   Toast.makeText(MainActivity.this,"title"+position,Toast.LENGTH_SHORT).show();
               }

               @Override
               public void onItemContentClicked(RecyclerView recyclerView, int position, View v) {
                   Toast.makeText(MainActivity.this,"content"+position,Toast.LENGTH_SHORT).show();
               }
           });
       }




    }

    private List<InfoBean> getData() {
        List<InfoBean> datas = new ArrayList<>();
        int[] id = {R.mipmap.img1, R.mipmap.img2, R.mipmap.img3, R.mipmap.img4};
        for (int j = 0; j < 10; j++) {
            for (int i = 0; i < 4; i++) {
                InfoBean info = new InfoBean();
                info.setId(id[i]);
                info.setContent("这里显示的是内容");
                info.setTitle("这里显示的是标题");
                datas.add(info);
            }
        }
        return datas;
    }
}
