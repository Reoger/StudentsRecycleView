package reoger.hut.com.strrecyclerview.houchHelper;

import android.graphics.Canvas;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;

import static reoger.hut.com.strrecyclerview.adapter.RBaseAdapter.HeaderView;
import static reoger.hut.com.strrecyclerview.utils.Utils.CanTouchCurrentItem;

/**
 * Created by 24540 on 2017/4/1.
 * 拖拽、删除实现的主体类
 */

public class MyItemTouchHelpCallback extends ItemTouchHelper.Callback {

    /**Item操作的回调*/
    private OnItemTouchCallbackListener onItemTouchCallbackListener;
    /**是否可以拖拽**/
    private boolean isCanDrag = false;
    /***是否可以滑动*/
    private boolean isCanSwipe = false;


    public MyItemTouchHelpCallback(OnItemTouchCallbackListener onItemTouchCallbackListener) {
        this.onItemTouchCallbackListener = onItemTouchCallbackListener;
    }

    public void setOnItemTouchCallbackListener(OnItemTouchCallbackListener onItemTouchCallbackListener){
        this.onItemTouchCallbackListener = onItemTouchCallbackListener;
    }

    /**
     * 设置是否可以被拖拽
     * */
    public void setDragEnable(boolean CanDrag){
        this.isCanDrag = CanDrag;
    }

    /**
     * 设置是否可以滑动
     * @param canSwipe
     */
    public void setSwipeEnable(boolean canSwipe){
        this.isCanSwipe = canSwipe;
    }

    /**
     * 当Item被长按的时候是否可以拖动
     * @return
     */
    @Override
    public boolean isLongPressDragEnabled() {
        //当recyclerView中含有header、footer或者下拉加载的控件的时候，所以将这里的返回false，转交给onLongPress来进行处理
      //  return isCanDrag;
     return false;
    }

    /**
     * Item是否可以被滑动(H：左右滑动，V：上下滑动)
     * @return
     */
    @Override
    public boolean isItemViewSwipeEnabled() {
        return isCanSwipe;
    }




    /**
     * 当用户拖拽或者滑动Item的时候需要我们告诉系统滑动或者拖拽的方向
     * @param recyclerView
     * @param viewHolder
     * @return
     */
    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if(layoutManager instanceof LinearLayoutManager) {
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
            int orientation = linearLayoutManager.getOrientation();

            int dragFlag = 0;
            int swipFlag = 0;

            if(orientation== LinearLayoutManager.HORIZONTAL){
                dragFlag = ItemTouchHelper.LEFT |ItemTouchHelper.RIGHT;
                if(CanTouchCurrentItem(viewHolder))
                swipFlag = ItemTouchHelper.UP |ItemTouchHelper.DOWN;
            }else if(orientation== LinearLayoutManager.VERTICAL){
                dragFlag = ItemTouchHelper.UP |ItemTouchHelper.DOWN;

                /**
                 * 三个判断条件
                 * 1. 在添加了header的情况下，getAdapterPosition不能为0
                 * 2. 在LoadMoreView的情况下，getAdapterPosition不能为Item_Count-1
                 * 3. 在LoadMoreView且Footer的情况下，getAdapterPosition不能为Item_Count-2
                 * 或者 在LoadMoreView为false Footer为true的情况下，getAdapterPosition不能为Item_Count-1
                 */

                if(CanTouchCurrentItem(viewHolder))
                swipFlag = ItemTouchHelper.LEFT |ItemTouchHelper.RIGHT;

            }
            return makeMovementFlags(dragFlag,swipFlag);
        }
        return 0;
    }




    /**
     * 当Item被拖拽的时候回调
     * @param recyclerView s
     * @param viewHolder 拖拽的vieholder
     * @param target 目的拖拽viewHolder
     * @return
     */
    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        if(onItemTouchCallbackListener!=null){
            if(HeaderView && viewHolder.getAdapterPosition()==0){
                Log.d("TAG","方法");
                return false;
            }else
            return onItemTouchCallbackListener.onMove(viewHolder.getAdapterPosition(),target.getAdapterPosition());
        }

        return false;
    }

    /**
     * 当Item被删除的时候回调
     * @param viewHolder 要删除的item
     * @param direction
     */
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        Log.d("TAG","这里"+viewHolder.getAdapterPosition());
        if(onItemTouchCallbackListener!=null){
            onItemTouchCallbackListener.onSwiped(viewHolder.getAdapterPosition());
        }

    }

    //长按时调用
    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        if(actionState != ItemTouchHelper.ACTION_STATE_IDLE){
            viewHolder.itemView.setBackgroundColor(Color.LTGRAY);
        }
        super.onSelectedChanged(viewHolder, actionState);
    }

    //当手指松开的时候调用
    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        viewHolder.itemView.setBackgroundResource(0);
    }




    //设置拖拽的效果
    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
       if(actionState ==ItemTouchHelper.ACTION_STATE_SWIPE){
           //根据侧滑的位移来修改item的透明度
           final float alpha = 1 - Math.abs(dX) / (float) viewHolder.itemView.getWidth();
           viewHolder.itemView.setAlpha(alpha);
           viewHolder.itemView.setTranslationX(dX);
       }
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }

    public interface OnItemTouchCallbackListener{
        /**
         * 当某个item被滑动删除的时候
         * @param position
         */
        void onSwiped(int position);

        /**
         * 当两个Item位置互相的时候
         * @param srcPosition
         * @param targerPostion
         * @return
         */
        boolean onMove(int srcPosition, int targerPostion);
    }




}
