package reoger.hut.com.applications;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import reoger.hut.com.applications.other.MyAdapter;
import reoger.hut.com.applications.other.Per;


public class Main2Activity extends AppCompatActivity {

    private PullRecycler mpullRecycler;

    private MyAdapter myAdapter;

    private List<Per> datas;

    private LinearLayoutManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        manager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        initData();
    }

    private void initData() {
        mpullRecycler = (PullRecycler) findViewById(R.id.pullclerView);
        myAdapter = new MyAdapter(getData(),this);
        if(mpullRecycler!=null){

            mpullRecycler.setLayoutManager(manager);
        }else{
            Log.d("TAG","12");
        }
    }


    private List<Per> getData(){
        datas = new ArrayList<>();
        int []ids ={R.mipmap.img1,R.mipmap.img2,R.mipmap.img3,R.mipmap.img4};
        String []names ={"占山","历史","王五","斩六"};
        for(int j=0;j<4;j++)
        for(int i=0;i<4;i++){
            Per item = new Per();
            item.setIds(ids[i]);
            item.setName(names[i]);
            datas.add(item);
        }
        return datas;
    }
}
