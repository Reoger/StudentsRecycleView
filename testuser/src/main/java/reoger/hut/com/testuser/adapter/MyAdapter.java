package reoger.hut.com.testuser.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

import reoger.hut.com.strrecyclerview.adapter.RBaseAdapter;
import reoger.hut.com.strrecyclerview.bean.RBaseViewHolder;
import reoger.hut.com.testuser.R;
import reoger.hut.com.testuser.bean.Per;


/**
 * Created by 24540 on 2017/4/6.
 * 测试
 */

public class MyAdapter extends RBaseAdapter<MyAdapter.ViewHolderPer> {

    private List<Per> datas;
    private LayoutInflater mInflater;
    private Context mContext;

    public MyAdapter(List<Per> datas, Context context) {
        this.datas = datas;
        mInflater = LayoutInflater.from(context);
        mContext =  context;
    }


    public void loadMoreData(List<Per> datas){
        this.datas = datas;
      notifyDataSetChanged();
    }

    @Override
    public void addHeaderView(View headerView) {
        mHeaderView = headerView;
        notifyItemInserted(0);
    }

    @Override
    public void addFooterView(View footerView) {
        mFooterView = footerView;
        if(isLoadMoreFooterShown)
            notifyItemInserted(Item_Count-2);
        else
            notifyItemInserted(Item_Count-1);
    }

    @Override
    protected RBaseViewHolder onCreateNormalViewHolder(ViewGroup parent, int viewType) {
        View v = mInflater.inflate(R.layout.item_list,parent,false);
        ViewHolderPer per = new ViewHolderPer(v);
        return per;
    }



    @Override
    protected int getDataCount()
    {
        return datas.size();
    }


    /**
     * 如果需要实现删除效果，在这里进行实现
     * @param position 要删除对象的索引号
     */
    @Override
    public void onSwiped(int position) {
        if(datas !=null&&datas.size()>=position){
            if(HeaderView)//如果添加了header的话，需要将header的位置也算在里面
                datas.remove(position-1);
            else
                datas.remove(position);
            notifyItemRemoved(position);
        }
    }


    /**
     * 如果需要实现拖拽的效果，在这里实现数据交换
     * @param srcPosition 选中的viewHolder
     * @param targerPostion 目标对象
     * @return true 表示消耗此次的点击事件
     */
    @Override
    public boolean onMove(int srcPosition, int targerPostion) {
        if(datas != null){
                if(HeaderView){
                    Collections.swap(datas,srcPosition-1,targerPostion-1);
                }else
                    Collections.swap(datas,srcPosition,targerPostion);
                notifyItemMoved(srcPosition,targerPostion);
    }
        return true;
}

class ViewHolderPer extends RBaseViewHolder {
    public ImageView imageView;
    public TextView mName;

    public ViewHolderPer(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.image);
            mName = (TextView) itemView.findViewById(R.id.name);
        }

        @Override
        public void onBindViewHolder(int position) {
        imageView.setImageResource(datas.get(position).getIds());
            mName.setText(datas.get(position).getName());

        }

        //点击事件可以在这里进行实现
        @Override
        public void OnItemClick(View view, int position) {
            Toast.makeText(mContext,"点击了"+position,Toast.LENGTH_SHORT).show();
        }
    }
}
