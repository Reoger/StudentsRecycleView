package com.hut.reoger.studentsrecycleview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.hut.reoger.studentsrecycleview.adapter.MyAdapter;
import com.hut.reoger.studentsrecycleview.bean.InfoBean;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recycler);
        RecyclerView.LayoutManager mManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        MyAdapter mMyAdapter = new MyAdapter(this, getData());

        mRecyclerView.setAdapter(mMyAdapter);
        mRecyclerView.setLayoutManager(mManager);


    }

    private List<InfoBean> getData() {
        List<InfoBean> datas = new ArrayList<>();
        int []id ={R.mipmap.img1,R.mipmap.img2,R.mipmap.img3,R.mipmap.img4};
        for (int j = 0; j < 10; j++) {
            for(int i=0;i<4;i++){
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
