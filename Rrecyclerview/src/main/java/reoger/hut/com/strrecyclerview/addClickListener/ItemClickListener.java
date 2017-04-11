package reoger.hut.com.strrecyclerview.addClickListener;

import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by 24540 on 2017/3/28.
 * 添加item的点击事件：方法一、复写GestureDetectorCompat
 */

public class ItemClickListener extends RecyclerView.SimpleOnItemTouchListener {

    private GestureDetectorCompat gestureDetectorCompat;

    public interface OnItemClickListener{
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int postion);
    }

    public ItemClickListener(final RecyclerView recyclerView,final OnItemClickListener clickListener) {
        gestureDetectorCompat = new GestureDetectorCompat(recyclerView.getContext(),
                new GestureDetector.SimpleOnGestureListener(){
                    @Override
                    public boolean onSingleTapUp(MotionEvent e) {
                        View childView = recyclerView.findChildViewUnder(e.getX(),e.getY());
                        if(childView != null && clickListener!= null){
                            clickListener.onItemClick(childView,recyclerView.getChildAdapterPosition(childView));
                        }
                        return true;
                    }

                    @Override
                    public void onLongPress(MotionEvent e) {
                        View childView = recyclerView.findChildViewUnder(e.getX(),e.getY());
                        if(childView != null && clickListener != null){
                            clickListener.onItemLongClick(childView,recyclerView.getChildAdapterPosition(childView));
                        }
                    }
                });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        gestureDetectorCompat.onTouchEvent(e);
        return false;
    }



}
