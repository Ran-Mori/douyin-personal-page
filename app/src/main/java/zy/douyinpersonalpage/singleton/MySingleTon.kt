package zy.douyinpersonalpage.singleton


import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import zy.douyinpersonalpage.constant.MyConstant


/**
 * 全局单例模式对象获取
 */
class MySingleTon {
    companion object{
        /**
         * 获取Retrofit对象
         */
        private val retrofit = Retrofit.Builder()
            .baseUrl(MyConstant.BASE_URL) //获取全局BASEURL
            .addConverterFactory(GsonConverterFactory.create())//添加GSON解析器
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//添加RXJava相关内容
            .build()
        fun getRetrofit() = retrofit
    }
}