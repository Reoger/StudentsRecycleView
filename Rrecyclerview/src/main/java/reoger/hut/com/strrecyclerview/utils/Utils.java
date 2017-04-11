package reoger.hut.com.strrecyclerview.utils;

import android.support.v7.widget.RecyclerView;

import static reoger.hut.com.strrecyclerview.adapter.RBaseAdapter.FooterView;
import static reoger.hut.com.strrecyclerview.adapter.RBaseAdapter.HeaderView;
import static reoger.hut.com.strrecyclerview.adapter.RBaseAdapter.Item_Count;
import static reoger.hut.com.strrecyclerview.adapter.RBaseAdapter.LoadMoreView;

/**
 * Created by 24540 on 2017/4/11.
 */

public class Utils {
    public static boolean  CanTouchCurrentItem(RecyclerView.ViewHolder viewHolder) {
        if(HeaderView&&viewHolder.getAdapterPosition()==0){
            //满足条件1
            return false;
        }else if(LoadMoreView&&viewHolder.getAdapterPosition()==Item_Count-1){
            //满足条件2
            return false;
        }else if(LoadMoreView&&FooterView&&viewHolder.getAdapterPosition()==Item_Count-2){
            //满足条件3
            return false;
        }else if(!LoadMoreView&&FooterView&&viewHolder.getAdapterPosition()==Item_Count-1){
            //条件3的另一种情况
            return false;
        }
        return true;
    }
}
