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
