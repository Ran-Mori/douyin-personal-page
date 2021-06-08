package zy.douyinpersonalpage.retrofitservice


import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.http.GET

/**
 * Video的service
 */
interface  IVideoListService {
    /**
     * 此处定义的返回值是ResponseBody，后面会自定义json解析
     */
    @GET("post")
    fun getVideoList():Observable<ResponseBody>
}