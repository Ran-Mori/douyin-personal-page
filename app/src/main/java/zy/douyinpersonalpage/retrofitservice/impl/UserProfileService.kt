package zy.douyinpersonalpage.retrofitservice.impl

import io.reactivex.Observable
import zy.douyinpersonalpage.model.UserProfileResponse
import zy.douyinpersonalpage.retrofitservice.IUserProfileService
import zy.douyinpersonalpage.singleton.MySingleTon

/**
 * 用户信息servie实现类
 */
object UserProfileService {
    /**
     * 此语句会在运行时动态生成一个实现类
     */
    private val service = MySingleTon.getRetrofit().create(IUserProfileService::class.java)

    fun getUserProfile():Observable<UserProfileResponse> = service.getUserProfile()
}