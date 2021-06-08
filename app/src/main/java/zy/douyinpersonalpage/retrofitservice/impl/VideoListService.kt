package zy.douyinpersonalpage.retrofitservice.impl

import zy.douyinpersonalpage.retrofitservice.IVideoListService
import zy.douyinpersonalpage.singleton.MySingleTon

/**
 * 视频类的service实现
 */
object VideoListService {
    /**
     * 此语句会在运行时动态生成一个实现类
     */
    private val service = MySingleTon.getRetrofit().create(IVideoListService::class.java)

    fun getPostVideoList() = service.getPostVideoList()

    fun getFavoriteVideoList() = service.getFavoriteVideoList()
}