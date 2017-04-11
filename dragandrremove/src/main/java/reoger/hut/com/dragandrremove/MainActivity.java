package reoger.hut.com.dragandrremove;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import reoger.hut.com.dragandrremove.adapter.RecyclerViewIOnClickListener;
import reoger.hut.com.dragandrremove.adapter.TestAdpater;
import reoger.hut.com.dragandrremove.bean.AdapterItemHolder;
import reoger.hut.com.dragandrremove.bean.Person;
import reoger.hut.com.dragandrremove.houchHelper.MyItemTouchHelpCallback;
import reoger.hut.com.dragandrremove.houchHelper.MyItemTouchHelper;

public class MainActivity extends AppCompatActivity implements RecyclerViewIOnClickListener{

    private static MainActivity instance;

    public static MainActivity getInstance() throws Exception {
        if (instance==null) {
            throw new Exception("instance为空");
        }
        return instance;
    }

    private RecyclerView mRecyclerView;
   // private MyAdapter mMyAdapter;
    private TestAdpater adpater;

    private RecyclerView.LayoutManager manager;

    private MyItemTouchHelper itemTouchHelper;

    private List<Person> datas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        instance=this;



        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        manager =new  LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        mRecyclerView.setLayoutManager(manager);
        itemTouchHelper = new MyItemTouchHelper(onItemTouchCallbackListener);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
        itemTouchHelper.setSwipeEnable(true);
        itemTouchHelper.setDragEnable(true);

      //  mMyAdapter   = new MyAdapter(initData(),this);
        adpater = new TestAdpater(initData(),this);
        mRecyclerView.setAdapter(adpater);

//        mMyAdapter.setOnItemClickListener(new MyAdapter.onItemClickListener() {
//            @Override
//            public void OnclickListener(View v, int postion) {
//                Toast.makeText(MainActivity.this,"ii"+postion,Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        mMyAdapter.setOnItemLongClickListener(new MyAdapter.OnItemLongClickListener() {
//            @Override
//            public void onLongClickListener(View v, int postion, boolean flag) {
//                Toast.makeText(MainActivity.this,"ii**"+postion,Toast.LENGTH_SHORT).show();
//            }
//        });




    }

    private List<Person> initData() {
        int []ids = {R.mipmap.img1,R.mipmap.img2,R.mipmap.img3,R.mipmap.img4};
        String []names = {"张三","李四","王五","找刘"};
        datas = new ArrayList<>();
        for (int j = 0; j < 3; j++)
        for(int i=0;i<4;i++){
            Person item = new Person();
            item.setImgId(ids[i]);
            item.setName(names[i]);
            datas.add(item);
        }
        return datas;
    }

    private MyItemTouchHelpCallback.OnItemTouchCallbackListener onItemTouchCallbackListener = new MyItemTouchHelpCallback.OnItemTouchCallbackListener(){

        @Override
        public void onSwiped(int position) {
            if(datas !=null){
                datas.remove(position);
                adpater.notifyItemRemoved(position);
            }
        }

        @Override
        public boolean onMove(int srcPosition, int targerPostion) {
            if(datas != null){
                Collections.swap(datas,srcPosition,targerPostion);
                adpater.notifyItemMoved(srcPosition,targerPostion);
            }

            return true;
        }
    };


    @Override
    public void addListener(AdapterItemHolder adapterItemHolder) {
            adapterItemHolder.SetOnRecyclerItemClickListener(new AdapterItemHolder.OnRecyclerItemClickListener() {
                @Override
                public void onItemClickListener(View v, int pos) {
                    Log.d("TAG","12"+pos+v.getId()+",-->"+(MainActivity.this));
                    Toast.makeText((MainActivity.this), "66666", Toast.LENGTH_SHORT).show();
//                    Log.d("TAG",MainActivity.this.a+" **");
                }
            });
            adapterItemHolder.SetOnRecyclerItemLongClickListener(new AdapterItemHolder.OnRecyclerItemLongClickListener() {
                @Override
                public void onItemLongClickListener(View v, int pos) {
                    Log.d("TAG","123"+pos+v.getId());
                }
            });

    }


    public void nextActivity(View view){
        startActivity(new Intent(MainActivity.this,NextActivity.class));
        Log.d("TAG","测试");
    }
}
