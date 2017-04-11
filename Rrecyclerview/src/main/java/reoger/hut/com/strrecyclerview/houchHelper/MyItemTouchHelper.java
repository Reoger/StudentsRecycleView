package reoger.hut.com.strrecyclerview.houchHelper;

import android.support.v7.widget.helper.YolandaItemTouchHelper;

import reoger.hut.com.strrecyclerview.RPullRecycler;

/**
 * Created by 24540 on 2017/4/1.
 * 实现类的帮助类
 */

public class MyItemTouchHelper extends YolandaItemTouchHelper {

    private  MyItemTouchHelpCallback myItemTouchHelpCallback;



    public MyItemTouchHelper(MyItemTouchHelpCallback.OnItemTouchCallbackListener callback) {
        super(new MyItemTouchHelpCallback(callback));
        myItemTouchHelpCallback = (MyItemTouchHelpCallback) getCallback();
    }

    public void attachToRecyclerView(RPullRecycler pullRecycler){
        if(pullRecycler!=null)
        attachToRecyclerView(pullRecycler.getmRecyclerView());
    }

    /**
     * 设置是否可以拖动
     * @param canDrag
     */
    public void setDragEnable(boolean canDrag){
        myItemTouchHelpCallback.setDragEnable(canDrag);
    }


    public void setSwipeEnable(boolean canSwipe){
        myItemTouchHelpCallback.setSwipeEnable(canSwipe);
    }
}
