# DouyinPersonalPage
个人大作业，实现一个抖音个人页

## 三段式页面布局示例

> ```xml
> <RelativeLayout
>     android:layout_width="match_parent"
>     android:layout_height="match_parent">
> 
>     <!--48dp高度-->
>     <RelativeLayout
>         android:id="@+id/ly_top_bar"
>         android:layout_width="match_parent"
>         android:layout_height="48dp">
> 
>         <TextView
>             android:id="@+id/txt_topbar"
>             android:layout_width="match_parent"
>             android:layout_height="match_parent" />
> 
> 
>         <!--两个像素做一条分界线，且向下parent即此relativeLayout对齐-->
>         <View
>             android:layout_width="match_parent"
>             android:layout_height="2px"
>             android:layout_alignParentBottom="true" />
>     </RelativeLayout>
> 
> 
>     <!--设置了above 和 below的对齐，此时height属性没有用
>           且刚好top和上面的对齐，bottom和下面的对齐，即刚好夹在中间-->
>     <FrameLayout
>         android:id="@+id/ly_content"
>         android:layout_width="match_parent"
>         android:layout_height="0dp"
>         android:layout_above="@id/div_tab_bar"
>         android:layout_below="@id/ly_top_bar">
>         <TextView
>             android:layout_width="match_parent"
>             android:layout_height="match_parent"
>             android:text="Hello" />
>     </FrameLayout>
> 
>     <!--设置在ly_tab_bar的above，
>         此处设置在ly_content的bottom可以达到同样的效果-->
>     <View
>         android:id="@+id/div_tab_bar"
>         android:layout_width="match_parent"
>         android:layout_height="2px"
>         android:layout_above="@id/ly_tab_bar" />
> 
> 
>     <!--对齐父组件底部，即在最下面-->
>     <LinearLayout
>         android:id="@+id/ly_tab_bar"
>         android:layout_width="match_parent"
>         android:layout_height="56dp"
>         android:layout_alignParentBottom="true">
>         
>         <!--宽度默认成0，通过weight来进行设置宽度-->
>         <TextView
>             android:id="@+id/txt_channel"
>             android:layout_width="0dp"
>             android:layout_height="match_parent"
>             android:layout_weight="1" />
> 
>         <TextView
>             android:id="@+id/txt_message"
>             android:layout_width="0dp"
>             android:layout_height="match_parent"
>             android:layout_weight="1" />
> 
>         <TextView
>             android:id="@+id/txt_better"
>             android:layout_width="0dp"
>             android:layout_height="match_parent"
>             android:layout_weight="1" />
> 
>         <TextView
>             android:id="@+id/txt_setting"
>             android:layout_width="0dp"
>             android:layout_height="match_parent"
>             android:layout_weight="1" />
> 
>     </LinearLayout>
> </RelativeLayout>
> ```
>
> * 即通过设置`layout_above和layout_bottom`等可以控制页面的布局
> * 也需要注意辨别设置了不同的属性后可能会有属性覆盖的问题，即有些属性设置了等于没有设置
>
> ***

## ui属性的优先级

>```xml
><RelativeLayout android:layout_width="match_parent"
>    android:layout_height="match_parent">
>
>    <TextView
>        android:layout_width="match_parent"
>        android:layout_height="56dp"/>
>
>    <FrameLayout
>        android:layout_width="match_parent"
>        android:layout_height="match_parent"/>
></RelativeLayout>
>```
>
>* `matchparent`并不是一定会matchparent
>* 比如`FrameLayout`就不可能match parent，他必须让出`56dp`，因此dp的优先级更高
>
>***

## ViewAdapter

> ### 定义声明
>
> * `class VideoAdapter(var list:MutableList<Video>) : RecyclerView.Adapter<VideoAdapter.ViewHolder>()`
> * 即传入一个数据源头，继承基类
>
> ### 作用理解
>
> * `RecycleListView`实现了滑动显示不用的内容
> * 然而实际上实现可能是页面上的View根本没有变化，它还是那些View，只是给它绑定了不同的数据让用户觉得内容得到了更新
> * 实际上的核心目的就是减少`View`的创建，因为创建`View`还是一个比较费资源的操作。因此就有了`ViewHodler`，顾名思义就是存放`View`
>
> ### 三个重写的方法
>
> * `onCreateViewHolder()`：当没有`View`来为用户显示内容的时候会创建`ViewHolder`。注意逻辑是创建`ViewHolder`一定会创建新的`View`，因此实际上可以理解成创建`View`
> * `onBindViewHolder()`：这是执行频率比较高的一个方法了。即当用户滑动时，`View`保持不变，动态切换`View`的内容给用户滑动切换内容的感觉
> * `getItemCount()`：就是获得数据源一共包含多少个元素，个人猜测这个数字可以用来辅助创建`ViewHolder`
>
> ### 通过`Log.d`查看三个方法的执行频率
>
> * 多次滑动完所有内容后，会发现下面结论
> * `oncreateviewHolder()`执行的次数可以说是固定的，最后无论怎么滑动都不会执行这个方法了。其实也就是用户的手机屏幕最多可以容纳多少个`View`(个人理解)
> * `onBindViewHodler()`方法滑动的时候会一直执行，但后面会执行的越来越少
> * `getItemCount()`方法居然无论滑动完多少次都会一直执行很多次，我目前想不通是为什么。个人猜测是侦测到数量增加时立刻创建`ViewHolder`，达到类似于缓存的目的
>
> ***

## Fresco使用

> ### 添加依赖
>
> * `implementation 'com.facebook.fresco:fresco:2.4.0'`
>
> ### 初始化
>
> * ```kotlin
>   class MyApplication:Application() {
>       override fun onCreate() {
>           super.onCreate()
>           /**
>            * 初始化fresco
>            */
>           Fresco.initialize(this);
>       }
>   }
>   
>   class MyApplication:Application() {
>       override fun onCreate() {
>           super.onCreate()
>           /**
>            * 初始化fresco
>            */
>           Fresco.initialize(this, ImagePipelineConfig.newBuilder(applicationContext)
>               .setMemoryChunkType(MemoryChunkType.BUFFER_MEMORY)
>               .setImageTranscoderType(ImageTranscoderType.JAVA_TRANSCODER)
>               .experiment().setNativeCodeDisabled(true)
>               .build());
>       }
>   }
>   ```
>
> * 以上两种初始化方法都可以
>
> * `Fresco`要求在全局必须初始化一次，因此一般都继承`Application`进行一次初始化
>
> * 但是要在`AndroidManifest.xml`修改`Application`为我们自定义的application
>
> * ```xml
>   <?xml version="1.0" encoding="utf-8"?>
>   <manifest >
>       <uses-permission android:name="android.permission.INTERNET" />
>       <application
>           android:name=".application.MyApplication">
>       </application>
>   </manifest>
>   ```
>
> * 且不要忘记要开启网络权限
>
> ### 创建一个SimpleDraweeView
>
> * ```xml
>   <com.facebook.drawee.view.SimpleDraweeView
>           android:id="@+id/videoImageView"
>           android:layout_width="124dp"
>           android:layout_height="168dp"
>           android:background="@color/background"
>           android:scaleType="fitXY"/>
>   ```
>
> ### 直接无脑设置URL
>
> * ```kotlin
>   val uri = URI.parse("https://www.xxx.com")
>   videoImageView.setImageURI(uri)
>   ```
>
> ### 总结
>
> * androids中图像的加载操作是一个十分复杂的过程
> * 首先主线程中尽量不能有(好像就是不允许)有网络的IO操作，如果实在要进行都必须放在其他线程中
> * 然而自己控制线程有可能会创建很多的线程导致线程爆炸，导致程序崩溃
> * 比如在`onBindViewHolder()`中通过创建线程来在非主线程中请求图片内容，然而`onBindViewHolder()`方法实际执行频率爆炸高，最后因为创建的线程过多导致程序崩溃
> * 因此图片加载涉及问题太多了
>   * 主线程不能进行网络请求IO操作
>   * 只有主线程能够进行UI更新操作，因而还要自己处理回调逻辑
>   * 网络IO费时费力肯定要用缓存，还得自己处理缓存
>   * 因此建议不要轻易自己尝试处理图片
> * 直接用`Freso`库设置设置一个URI后就什么都不用管了
>
> ***



## RXjava

> ### 定义
>
> * rxjava is a java VM implementation of reactive extensions
> * 特点
>   * 异步
>   * 基于事件
> * 核心思想
>   * 异步数据流编程
>   * 函数式编程
>
> ### 数据流举例
>
> * 有一堆按照时间顺序排列好的**点击事件流**，现在想识别出连续点击事件
> * 首先对250ms内的点击事件进行一个合并
> * 接着获得每个合并后的事件是有多少个小事件
> * 然后认为个数大于2的合并事件就是连续点击事件
> * 这样就完成了一个流的过滤操作
>
> ### 响应式编程
>
> * 数据流
> * 变化链 - 观察者模式
>
> ***

