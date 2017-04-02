package reoger.hut.com.dragandrremove.adapter;

import reoger.hut.com.dragandrremove.bean.AdapterItemHolder;

/**
 * Created by 24540 on 2017/4/2.
 */

public interface RecyclerViewIOnClickListener<T extends AdapterItemHolder> {
    void addListener(T t);
}
