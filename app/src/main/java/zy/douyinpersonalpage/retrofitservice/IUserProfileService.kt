package zy.douyinpersonalpage.retrofitservice


import io.reactivex.Observable
import retrofit2.http.GET
import zy.douyinpersonalpage.model.UserProfileResponse

/**
 * 个人信息获取的Retrofit Service
 */
interface IUserProfileService {

    /**
     * 会自动做url拼接，且'UserProfileResponse'会自动解析封装
     */
    @GET("user/profile")
    fun getUserProfile(): Observable<UserProfileResponse>
}