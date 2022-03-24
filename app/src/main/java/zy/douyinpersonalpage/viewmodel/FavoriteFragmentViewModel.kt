package zy.douyinpersonalpage.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody
import org.json.JSONObject
import zy.douyinpersonalpage.model.Video
import zy.douyinpersonalpage.retrofitservice.impl.VideoListService

class FavoriteFragmentViewModel(var list: MutableList<Video>?):ViewModel() {
    private var _videoList = MutableLiveData<MutableList<Video>>()

    val videoList:LiveData<MutableList<Video>>
        get() = _videoList

    init {
        if (list == null){
            getVideoList()
        }
    }

    /**
     * 通过网络请求更新数据
     */
    fun getVideoList(){
        VideoListService.getFavoriteVideoList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<ResponseBody> {
                override fun onSubscribe(d: Disposable) {

                }

                /**
                 * ResponseBody是一个流，只能读取一次，一定要提前保存一个temp
                 */
                override fun onNext(t: ResponseBody) {
                    Log.d("MainActivity","onNext")
                    /**
                     * 进行获取json并解析更新数据源的逻辑
                     */
                    val tempSaveString = t.string()
                    val list = parseJsonStringToVideoList(tempSaveString)
                    resetVideoList(list)
                }

                override fun onError(e: Throwable) {
                    Log.d("MainActivity","onError${e.printStackTrace()}")
                }

                override fun onComplete() {

                }
            })
    }


    /**
     * 解析json
     */
    fun parseJsonStringToVideoList(inputString:String):MutableList<Video>{
        val videoJsonList = JSONObject(inputString).getJSONArray("aweme_list")

        //定义最后返回的result
        var result = ArrayList<Video>()

        var description:String
        var coverUrl:String
        var awemeCount:Long
        /**
         * 直接一步步硬解析json并返回对象
         */
        var i = 0
        while (i < videoJsonList.length()){
            description = videoJsonList.getJSONObject(i).getString("desc")
            coverUrl = videoJsonList.getJSONObject(i).getJSONObject("video").getJSONObject("origin_cover").getJSONArray("url_list").getString(0)
            awemeCount = videoJsonList.getJSONObject(i).getJSONObject("statistics").getLong("digg_count")
            result.add(Video(description = description,coverUrl = coverUrl, awemeCount = awemeCount))
            i++
        }
        return result
    }

    /**
     * 重新设置数据源，触发观察更新
     */
    fun resetVideoList(list: MutableList<Video>){
        _videoList.value = list
    }
}