package reoger.hut.com.dragandrremove.houchHelper;

import android.support.v7.widget.helper.YolandaItemTouchHelper;

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
