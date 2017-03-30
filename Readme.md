# RecyclerView基础使用

1. 添加依赖
```
compile 'com.android.support:recyclerview-v7:25.3.0'
```

2. 创建bean对象

这里的bean对象可以写成两个部分，一部分表示原始数据，一部分在RecycelView进行显示。
这样说可能有点抽象，举个例子说明。我需要显示如图的样式：
![显示的效果](/img/imd.jpg)

我创建两个bean对象，一个用于数据的传递，一个在adapter中用于item的显示。InfoBean用于控制数据。ItemHolder用于显示数据
代码请参考:
[/.](http://reoger.cc/2048/html)

3. 创建adapter对象
详细参照例子，这里提出要点：
* 继承RecyclerView.Adapter<T>
* 实现继承的方法。
* 利用onCreateViewHolder方法创建ViewHolder
* 利用onBindViewHolder方法显示具体内容
* 利用getItemCount总计数据的总数


4.创建item布局和主布局
    这一点比较简单，不做解释
    
    
5. 在主界面显示recyclerView
这一点同普通的listView实现基本相同，有一点需要注意的是：在显示之前需要为recyclerVIew设置布局。
关键代码如下：
```
RecyclerView.LayoutManager mManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
mRecyclerView.setLayoutManager(mManager);
```

基本上到这里，就差不多完成了。

# 

在RecycleView中，并没有直接为Item开放OnItemClick等点击事件，这需要我们自己动手来完成。
下面介绍三中比较常用的方法来实现添加点击事件。

## 在adapter中添加点击事件
比较常见的一种方法。在使用listView时，我们有时候也会使用这种方法来实现添加点击事件。因为在
adapter中添加点击事件的可以实现最item子view点击事件的监控。
思路如下：</br>
在adapter中新建并暴露自己定义的接口类型。
```
private OnRecyclerViewItemClickListener mOnItenClickListener = null;
private OnRecyclerViewItemLongClickListener mOnItemLongClickListener = null;

public void setOnItemClickListener(OnRecyclerViewItemClickListener listener){
    this.mOnItenClickListener = listener;
}

public void setOnItemLongClickListener(OnRecyclerViewItemLongClickListener listener){
    this.mOnItemLongClickListener = listener;
}
```
在adapter中添加对指定元素的的点击事件。
```
v.setOnLongClickListener(new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            if(mOnItemLongClickListener!=null)
            mOnItemLongClickListener.OnItemLongClickListener(v, (Integer) v.getTag());
            return false;
        }
    });
```
在接口调用中，调用自己定义的接口来进行实现。
```

@Override
public void onClick(View v) {
    if(mOnItemLongClickListener !=null)
    mOnItenClickListener.OnItemClickListener(v, (Integer) v.getTag());
}
```
在activity中调用接口实现监听
```
mMyAdapter.setOnItemClickListener(new MyAdapter.OnRecyclerViewItemClickListener() {
           @Override
           public void OnItemClickListener(View view, int position) {
               Toast.makeText(MainActivity.this,"通过方法3实现的点击事件"+position,Toast.LENGTH_SHORT).show();
           }
       });
       mMyAdapter.setOnItemLongClickListener(new MyAdapter.OnRecyclerViewItemLongClickListener() {
           @Override
           public void OnItemLongClickListener(View view, int position) {
               Toast.makeText(MainActivity.this,"通过方法3实现的点击事件长安"+position,Toast.LENGTH_SHORT).show();
           }
       });
```

## 通过重写GestureDetectorCompat实现监听
虽然RecycleView没有直接实现对应的点击事件，但是在它给我们提供的api中，会发现它还是给我们预留了接口来进行实现
。通过重写GestureDetectorCompat来实现对item点击事件的监控。
```
 private GestureDetectorCompat gestureDetectorCompat;

    public interface OnItemClickListener{
        void onItemClick(View view, int position);
        void onItemLongClick(View view,int postion);
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

```

在Activity中进行调用，可以参照下面：
```
mRecyclerView.addOnItemTouchListener(new ItemClickListener(mRecyclerView, new ItemClickListener.OnItemClickListener() {
   @Override
   public void onItemClick(View view, int position) {
       Toast.makeText(MainActivity.this, "通过方法1实现的点击事件" + position, Toast.LENGTH_SHORT).show();
   }

   @Override
   public void onItemLongClick(View view, int position) {
       Toast.makeText(MainActivity.this, "通过方法1实现的点击事件" + position, Toast.LENGTH_SHORT).show();
   }
}));
```

## 利用OnChildAttachStateChangeListener来实现
参照博客<http://www.littlerobots.nl/blog/Handle-Android-RecyclerView-Clicks/>
```
package com.hut.reoger.studentsrecycleview.addClickListener;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.hut.reoger.studentsrecycleview.R;

/**
 * Created by 24540 on 2017/3/28.
 * 为Item添加点击事件：方法二：利用OnChildAttachStateChangeListener来实现
 * 同时，使用方法，可以实现对item子控件的监听，具体实现参见类：
 * 参考链接：http://www.littlerobots.nl/blog/Handle-Android-RecyclerView-Clicks/
 */

public class ItemClickSupport {
    private final RecyclerView mRecyclerView;
    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                RecyclerView.ViewHolder holder = mRecyclerView.getChildViewHolder(v);
                mOnItemClickListener.onItemClicked(mRecyclerView, holder.getAdapterPosition(), v);
            }
        }
    };

    private View.OnLongClickListener mOnLongClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            if (mOnItemLongClickListener != null) {
                RecyclerView.ViewHolder holder = mRecyclerView.getChildViewHolder(v);
                return mOnItemLongClickListener.onItemLongClicked(mRecyclerView, holder.getAdapterPosition(), v);
            }
            return false;
        }
    };

    private RecyclerView.OnChildAttachStateChangeListener mAttachListener
            = new RecyclerView.OnChildAttachStateChangeListener() {
        @Override
        public void onChildViewAttachedToWindow(View view) {
            if (mOnItemClickListener != null) {
                view.setOnClickListener(mOnClickListener);
            }
            if (mOnItemLongClickListener != null) {
                view.setOnLongClickListener(mOnLongClickListener);
            }
        }

        @Override
        public void onChildViewDetachedFromWindow(View view) {}
    };

    private ItemClickSupport(RecyclerView recyclerView) {
        mRecyclerView = recyclerView;
        mRecyclerView.setTag(R.id.item_click_support, this);
        mRecyclerView.addOnChildAttachStateChangeListener(mAttachListener);
    }

    public static ItemClickSupport addTo(RecyclerView view) {
        ItemClickSupport support = (ItemClickSupport) view.getTag(R.id.item_click_support);
        if (support == null) {
            support = new ItemClickSupport(view);
        }
        return support;
    }

    public static ItemClickSupport removeFrom(RecyclerView view) {
        ItemClickSupport support = (ItemClickSupport) view.getTag(R.id.item_click_support);
        if (support != null) {
            support.detach(view);
        }
        return support;
    }

    public ItemClickSupport setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
        return this;
    }

    public ItemClickSupport setOnItemLongClickListener(OnItemLongClickListener listener) {
        mOnItemLongClickListener = listener;
        return this;
    }

    private void detach(RecyclerView view) {
        view.removeOnChildAttachStateChangeListener(mAttachListener);
        view.setTag(R.id.item_click_support, null);
    }

    public interface OnItemClickListener {
        void onItemClicked(RecyclerView recyclerView, int position, View v);
    }

    public interface OnItemLongClickListener {
        boolean onItemLongClicked(RecyclerView recyclerView, int position, View v);
    }
}

```
在MainActivity监听如下：
```
  ItemClickSupport.addTo(mRecyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
               @Override
               public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                    Toast.makeText(MainActivity.this,"通过方法2实现的点击事件"+position,Toast.LENGTH_SHORT).show();
               }
           });
           ItemClickSupport.addTo(mRecyclerView).setOnItemLongClickListener(new ItemClickSupport.OnItemLongClickListener() {
               @Override
               public boolean onItemLongClicked(RecyclerView recyclerView, int position, View v) {
                   Toast.makeText(MainActivity.this,"通过方法2实现的点击事件长安"+position,Toast.LENGTH_SHORT).show();
                   return false;
               }
           });
```
用这种方法，也可以实现对item的子view进行监控。具体实现可以参见我的代码。

综上，三种方法都可以实现对item点击事件的监控。
方法1和方法3都可以实现对item的子view实现监听。
方法2可以很方便实现对获取点击位置信息。
方法1附加在adapter中，代码有点耦合，不推荐使用方法1。


# 为RecyclerView添加header和footer

为recyclerView添加header和footer也是我们在开发过程中经常遇见的，实现起来也是比较简单的。下面介绍怎么添加header和footer

其实我们可以将header和footer看做是特殊的item，在添加他之后，我们值需要对他进行一些特殊的处理就可以达到我们想到的效果。

为了简单起见，我们直接在adapter中进行这一部分的操作。

在adapter中新建一些标识的int值，用于区分是否需要添加header和footer，如下所示：
```
public static final int TYPE_HEADER = 0;  //说明是带有Header的
public static final int TYPE_FOOTER = 1;  //说明是带有Footer的
public static final int TYPE_NORMAL = 2;  //说明是不带有header和footer的
```

对外暴露添加header和footer方法，参考如下：

```
private View mHeaderView;
private View mFooterView;

public void setHeaderView(View headerView) {
        mHeaderView = headerView;
        notifyItemInserted(0);
    }

    public void setFooterView(View footerView) {
        mFooterView = footerView;
        notifyItemInserted(getItemCount() - 1);
    }
```

覆写getItemViewType()方法，用于区分是否需要添加head和footer
```
 @Override
    public int getItemViewType(int position) {
        if (mHeaderView == null && mFooterView == null) {
            return TYPE_NORMAL;
        }
        if (position == 0) {
            return TYPE_HEADER;
        }
        if (position == getItemCount() - 1) {
            return TYPE_FOOTER;
        }
        return TYPE_NORMAL;
    }
```

在onCreateViewHolder中添加对header和footer的支持
```
if (mHeaderView != null && viewType == TYPE_HEADER) {
    return new ItemHolder(mHeaderView);
}
if (mFooterView != null && viewType == TYPE_FOOTER) {
    return new ItemHolder(mFooterView);
}
```
在onBindViewHolder增加对header和footer的处理，如果当前对象是header或者footer直接返回即可。
如果有header的话，因为header也需要占一个位子，所以显示的时候需要显示当前位子的前一个位子。
```
if (getItemViewType(position) == TYPE_HEADER) return;
else if (getItemViewType(position) == TYPE_FOOTER) return;
else {
    if (holder instanceof MyAdapterWith.ItemHolder) {
        position = holder.getLayoutPosition();
        position = mHeaderView == null ? position : position - 1;
        //计算当前的位置，如果添加了header的话，header也需要占用一个位置
        if (position < datas.size()) {
            holder.imageView.setImageResource(datas.get(position).getId());
            holder.teTitle.setText(datas.get(position).getTitle());
            holder.teContent.setText(datas.get(position).getContent());
        }
    }
}
```
然后，需要在ViewHolder中添加对header和footer的支持
```
  public class ItemHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView teTitle;
        public TextView teContent;


        public ItemHolder(View itemView) {
            super(itemView);
            if (itemView == mHeaderView)
                return;
            if (itemView == mFooterView)
                return;

            imageView = (ImageView) itemView.findViewById(R.id.item_image);
            teTitle = (TextView) itemView.findViewById(R.id.item_title);
            teContent = (TextView) itemView.findViewById(R.id.item_content);
        }
```
最后，我们就可以直接在activity中进行添加了
```
View view  = LayoutInflater.from(this).inflate(R.layout.head,mRecyclerView,false);
mMyAdapter.setHeaderView(view);
View footer = LayoutInflater.from(this).inflate(R.layout.foot,mRecyclerView,false);
mMyAdapter.setFooterView(footer);
```

其中```R.layout.head```和```R.layout.foot```是head和foot的布局。

当然。这样写好之后，我们可以观察到这样的结果：
![添加header](/img/img2)
![添加foot](/img/img3)
基本正常，但是当我们使用GridLayoutManager时，会发现这样的问题。
![添加foot](/img/img4)
可以看到，这里我们的header和footer被当作一个item，并没有实现我们想到的置顶占行的效果。
解决方案：
利用GridLayoutManager的setSpanSizeLookup方法：
```
gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
    @Override
    public int getSpanSize(int position) {
        if (getItemViewType(position) == TYPE_HEADER || getItemViewType(position) == TYPE_FOOTER)
            return gridLayoutManager.getSpanCount();
        else
            return 1;
    }
});
```
这里的getSpanSize()方法返回的值决定了每个position上item占据的单元格个数。为了简单起见，可以将这个方法放在adapter中。代码如下：
```
 @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if(manager==null)       Log.d("TAG", "manager=null");

        if (manager instanceof GridLayoutManager) {

            final GridLayoutManager gridLayoutManager = (GridLayoutManager) manager;
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (getItemViewType(position) == TYPE_HEADER || getItemViewType(position) == TYPE_FOOTER)
                        return gridLayoutManager.getSpanCount();
                    else
                        return 1;
                }
            });
        }
    }
```
最后，再为StaggeredGridLayoutManager做一些处理。
```
 @Override
    public void onViewAttachedToWindow(ItemHolder holder) {
        super.onViewAttachedToWindow(holder);
        ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
        if (lp != null
                && lp instanceof StaggeredGridLayoutManager.LayoutParams) {
            StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
            if (mHeaderView != null)
                p.setFullSpan(holder.getLayoutPosition() == 0);
//            if(mFooterView!=null)
//                 p.setFullSpan(holder.getLayoutPosition() ==datas.size());
        }
    }
```
到此，添加header和footer的基本操作就完成了。不过在日常使用中，我们肯定会对每一次都需要编写一个这么复杂的header类感觉很麻烦，这里可以考虑封装一下。
对了，在使用的过程中，如果需要适配GridLayoutManager的话，在activity的recycler的setAdapter方法之前，需要先调用 mRecyclerView.setLayoutManager(mManager);
参见代码：
```
RecyclerView.LayoutManager mManager = new new GridLayoutManager(this,2);
mRecyclerView.setLayoutManager(mManager);

 mRecyclerView.setAdapter(mMyAdapter);
```
如果这样写：
```
RecyclerView.LayoutManager mManager = new new GridLayoutManager(this,2);
 mRecyclerView.setAdapter(mMyAdapter);
 mRecyclerView.setLayoutManager(mManager);
```
那么，我们在adapter中编写的对GridLayoutManager的适配就无法获取到recycler而无法起作用。
