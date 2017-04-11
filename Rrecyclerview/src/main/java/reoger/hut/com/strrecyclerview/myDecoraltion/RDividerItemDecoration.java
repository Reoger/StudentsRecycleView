package reoger.hut.com.strrecyclerview.myDecoraltion;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by 24540 on 2017/3/30.
 * 为Recycler每个item之间添加间隔
 * 参考博客：http://blog.csdn.net/lmj623565791/article/details/45059587
 */

public class RDividerItemDecoration extends RecyclerView.ItemDecoration {
    private Context mContext;
    private Drawable mDivider;
    private int mOrientation;

    public static final int HORIZONAL = LinearLayoutManager.HORIZONTAL;
    public static final int VERTICAL = LinearLayoutManager.VERTICAL;
    public static final int[] ATRRS = new int[]{android.R.attr.listDivider};

    public RDividerItemDecoration(Context mContext, int mOrientation) {
        this.mContext = mContext;
        this.mOrientation = mOrientation;
        final TypedArray typedArray = mContext.obtainStyledAttributes(ATRRS);
        this.mDivider = typedArray.getDrawable(0);

    }

    //设置屏幕方向
    public void setOrientation(int orientation) {
        if (orientation != HORIZONAL && orientation != VERTICAL) {
            throw new IllegalArgumentException("未知的屏幕方向");
        }
        mOrientation = orientation;
    }


    //需要重写这个方法，实现绘制item之间的间隔
    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (mOrientation == HORIZONAL) {
            drawHorizonalLine(c, parent, state);
        } else {
            drawVerticalLine(c, parent, state);
        }
    }

    //绘制竖线
    private void drawVerticalLine(Canvas canvas, RecyclerView recyclerView, RecyclerView.State state) {
        int top = recyclerView.getPaddingTop();
        int bottom = recyclerView.getBottom();
        final int childCount = recyclerView.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = recyclerView.getChildAt(i);

            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int left = params.rightMargin + child.getRight();
            final int right = left+mDivider.getIntrinsicHeight();
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(canvas);
        }
    }

    /**
     * 绘制横线
     *
     * @param canvas
     * @param recyclerView
     * @param state
     */
    private void drawHorizonalLine(Canvas canvas, RecyclerView recyclerView, RecyclerView.State state) {
        int left = recyclerView.getPaddingLeft();
        int right = recyclerView.getWidth() - recyclerView.getPaddingRight();
        final int childCount = recyclerView.getChildCount();
        for (int i = 0; i < childCount-1; i++){
            final View child = recyclerView.getChildAt(i);

            //获得child的布局信息
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams)child.getLayoutParams();
            final int top = child.getBottom() + params.bottomMargin;
            final int bottom = top + mDivider.getIntrinsicHeight();
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(canvas);
        }
    }

    //如果item之间设置了间隔，那么每个item就需要向下移动一定的位置
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        if(mOrientation ==HORIZONAL){
            outRect.set(0,0,0,mDivider.getIntrinsicHeight());
        }else{
            outRect.set(0,0,0,mDivider.getIntrinsicWidth());
        }
    }
}