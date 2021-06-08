# DouyinPersonalPage
个人大作业，实现一个抖音个人页

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

## okhttp + retrofit + rxjava

> ### okhttp
>
> * 一个androids网络请求库，能够发送http请求并收到回复
>
> ### retrofit
>
> * 本身不能进行网络请求，实际的网络请求依赖于okhttp
> * 它的作用是使发送网络请求更加简便，提高代码可读性和编程效率
>
> ### rxjava
>
> * 目前对这个框架还不太熟
> * 只知道是一个异步、响应式框架
>
> ### 引入依赖
>
> * ```bash
>   implementation 'com.squareup.okhttp3:okhttp:4.9.1'
>   implementation 'com.squareup.retrofit2:retrofit:2.4.0'
>   implementation 'io.reactivex.rxjava2:rxandroid:2.1.1'
>   implementation 'io.reactivex.rxjava2:rxjava:2.2.12'
>   implementation 'com.squareup.retrofit2:converter-gson:2.4.0'
>   implementation 'com.squareup.retrofit2:adapter-rxjava2:2.4.0'
>   ```
>
> * 一定要注意retrofit、rxjava和adapter的版本对应关系
>
> * 目前建议的是全部都用2版本，即使现在rxjava已经有3版本了。用错了版本会跑不起来
>
> ### 创建`Retrofit`对象
>
> * ```kotlin
>   /**
>    * 全局单例模式对象获取
>    */
>   class MySingleTon {
>       companion object{
>           /**
>            * 获取Retrofit对象
>            */
>           private val retrofit = Retrofit.Builder()
>               .baseUrl(MyConstant.BASE_URL) //获取全局BASEURL
>               .addConverterFactory(GsonConverterFactory.create())//添加GSON解析器
>               .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//添加RXJava相关内容
>               .build()
>           fun getRetrofit() = retrofit
>       }
>   }
>   ```
>
> * 一般情况下只要`baseUrl`不变，这个`Retrofit`对象是可以进行复用的，因此可以使用单例模式全局只创建一个
>
> * `Retrofit`框架支持自动化json解析，因此要添加一个json解析器
>
> * `Retrofit`和`Rxjava`协作，因此要添加`Rxjava`相关内容
>
> ### 定义Model对象
>
> * ```kotlin
>   data class UserProfileResponse(
>       @SerializedName("status_code")
>       val statusCode:Long,
>       @SerializedName("user")
>       val userProfile:UserProfile
>   )
>     
>   data class UserProfile(
>       @SerializedName("nickname")
>       var nickName:String,//昵称
>       @SerializedName("uid")
>       val uid:Long,//抖音号
>       @SerializedName("signature")
>       var signature:String,//个人简介
>       @SerializedName("aweme_count")
>       var awemeCount:Long,//获赞数
>       @SerializedName("following_count")
>       var followingCount:Long,//关注数
>       @SerializedName("follower_count")
>       var followerCount:Long//粉丝数
>   )
>   ```
>
> * 由于定义了json解析器，`Retrofit`会自动做json解析。因此必须保证`Model`类和json接口一一对应
>
> * 如果名字不同使用`@SerializedName("name")`注解来确定json数据名和实际属性名的映射关系
>
> ### 创建Service
>
> * ```kotlin
>   /**
>    * 个人信息获取的Retrofit Service
>    */
>   interface IUserProfileService {
>     
>       /**
>        * 会自动做url拼接，且'UserProfileResponse'会自动解析封装
>        */
>       @GET("user/profile")
>       fun getUserProfile(): Observable<UserProfileResponse>
>   }
>   ```
>
> * 创建的是一个接口，实际使用过程中框架会自己动态生成一个实现类
>
> * 且注意URL处不能加`/`，因此基地址已经加了，如果重复加可能会无法响应
>
> * 且此处定义好了饭回的对象是`UserProfileResponse`，响应饭回时会自动解析
>
> ### Service实现类
>
> * ```kotlin
>   /**
>    * 用户信息servie实现类
>    */
>   object UserProfileService {
>       /**
>        * 此语句会在运行时动态生成一个实现类
>        */
>       private val service = MySingleTon.getRetrofit().create(IUserProfileService::class.java)
>     
>       fun getUserProfile():Observable<UserProfileResponse> = service.getUserProfile()
>   }
>   ```
>
> * 直接使用单例模式方便省事
>
> * 此处在实际运行时会生成一个实现类
>
> ### 实际使用
>
> * ```kotlin
>   UserProfileService.getUserProfile()
>               .subscribeOn(Schedulers.io())
>               .observeOn(AndroidSchedulers.mainThread())
>               .subscribe(object : Observer<UserProfileResponse> {
>                   override fun onSubscribe(d: Disposable) {
>                   }
>     
>                   override fun onNext(t: UserProfileResponse) {
>                       /**
>                        * 对'_name'进行一次赋值操作，不然观察者感知不到
>                        */
>                       val userProfile = t.userProfile
>                       _userProfile.value = userProfile
>                   }
>     
>                   override fun onError(e: Throwable) {
>                   }
>     
>                   override fun onComplete() {
>                   }
>               })
>   ```
>
> * 固定模式，都这样用
>
> * 实际success回调函数是`onNext()`，在这个函数中处理运行成功的逻辑
>
> ### 自己处理json，不要它自动封装
>
> * 定义返回类型为`Observable<ResponseBody>`
>
> * ```kotlin
>   override fun onNext(t: ResponseBody) {
>     Log.d("MainActivity","onNext")
>     val tempSaveString = t.string()
>     val list = parseJsonStringToVideoList(tempSaveString)
>     resetVideoList(list)
>   }
>   ```
>
> * json字符串的内容是`t.string()`，而不是`t.toString()`
>
> * 这里还有一个点要注意。返回的`ResponseBody`是一个流，只能读取一次，二次读取会读不到任何内容。因此建议使用一个变量把内容暂时保存起来
>
> * 至于json的解析就是原来的老内容了，可以使用`JSONObject`来直接解析
>
> ***

## ViewModel使用

> ### ViewModel
>
> * ```kotlin
>   class PostFragmentViewModel(list: MutableList<Video>?): ViewModel() {
>   		
>       private var _videoList = MutableLiveData<MutableList<Video>>()
>   
>       val videoList: LiveData<MutableList<Video>>
>           get() = _videoList
>   
>   
>       /**
>        * 初始化时如果为空就拉取最新
>        */
>       init {
>           if (list != null){
>               _videoList.value = list
>           }else{
>               getVideoList()
>           }
>       }
>      fun getVideoList(){
>        //...
>      }
>   }
>   ```
>
> ### ViewModelFactory
>
> * ```kotlin
>   /**
>    * 工厂获得ViewModel，适用于有参数的ViewModel
>    */
>   class PostFragmentViewModelFactory(private val list: MutableList<Video>?):ViewModelProvider.Factory {
>       override fun <T : ViewModel?> create(modelClass: Class<T>): T {
>           return PostFragmentViewModel(list) as T
>       }
>   }
>   ```
>
> ### 获取实例并使用
>
> * ```kotlin
>   class PostFragment : Fragment() {
>   
>       private lateinit var viewModel: PostFragmentViewModel
>   
>       /**
>        * ViewModel尽量早初始化，放在OnAttach里面
>        */
>       override fun onAttach(context: Context) {
>           super.onAttach(context)
>           viewModel = ViewModelProvider(this, PostFragmentViewModelFactory(null)).get(PostFragmentViewModel::class.java)
>       }
>   
>   
>       override fun onCreateView(
>           inflater: LayoutInflater, container: ViewGroup?,
>           savedInstanceState: Bundle?
>       ): View? {
>           /**
>            * 建立观察
>            */
>           viewModel.videoList.observe(viewLifecycleOwner) {
>               adapter.list = it
>               adapter.notifyDataSetChanged()
>           }
>   				//...
>       }
>   }
>   ```
>
> ***

## ViewModel非空处理示例

> ### Adapter
>
> * `class VideoAdapter(var list:MutableList<Video>)`，就传入构造函数参数不允许为空
>
> ### ViewModel
>
> * ```kotlin
>   /**
>     * 初始化时如果为空就拉取最新
>     */
>   init {
>     if (list != null){
>       _videoList.value = list
>     }else{
>       getVideoList()
>     }
>   }
>   ```
>
> * 如果为空，就执行网络请求更新数据源
>
> ### Fragment
>
> * ```kotlin
>   /**
>     * 首先给adapter一个空的数据源
>     * ViewModel在进行初始化时一旦为null会执行一次调用网络请求，会更新数据源
>     * 这样就通过观察获取最新数据源
>     */
>   val adapter = VideoAdapter(ArrayList<Video>())
>   recyclerView.adapter = adapter
>     
>   /**
>     * 建立观察
>     */
>   viewModel.videoList.observe(viewLifecycleOwner) {
>     adapter.list = it
>     adapter.notifyDataSetChanged()
>   }
>   ```
>
> ***

## 同一个Activity不同Fragment通信

> ### 思路
>
> * 不能直接通信，通过公共的`activity`作中介来通信
>
> ### 示例
>
> * ```kotlin
>   class ThreeTextViewButtonFragment:Fragment() {
>       override fun onCreateView(
>           inflater: LayoutInflater,
>           container: ViewGroup?,
>           savedInstanceState: Bundle?
>       ): View? {
>           val view = inflater.inflate(R.layout.three_textview_button,container,false)
>           /**
>            * 同一个Activity不同的Fragment之间进行通信
>            */
>           view.findViewById<TextView>(R.id.postTextView).setOnClickListener {
>               activity?.bottomViewPager?.setCurrentItem(0,true)
>           }
>           return view
>       }
>   }
>   ```
>
> * 上述代码的activity中有两个fragment，分别是`ThreeTextViewButtonFragment`和`BottomViewPager`
>
> * 现在通过`activity?.bottomViewPager?.setCurrentItem(0,true)`来进行两者之间的通信
