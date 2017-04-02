package android.support.v7.widget.helper;

import android.support.v7.widget.RecyclerView;

/**
 * Created by 24540 on 2017/4/1.
 */

public class YolandaItemTouchHelper extends ItemTouchHelper {
    /**
     * Creates an ItemTouchHelper that will work with the given Callback.
     * <p>
     * You can attach ItemTouchHelper to a RecyclerView via
     * {@link #attachToRecyclerView(RecyclerView)}. Upon attaching, it will add an item decoration,
     * an onItemTouchListener and a Child attach / detach listener to the RecyclerView.
     *
     * @param callback The Callback which controls the behavior of this touch helper.
     */
    public YolandaItemTouchHelper(Callback callback) {
        super(callback);
    }
    public Callback getCallback(){
        return mCallback;
    }
}
