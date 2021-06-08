package zy.douyinpersonalpage.model

import com.google.gson.annotations.SerializedName


/**
 * 用户信息model类，此处为了符合接口json格式做了类的嵌套
 * 使用注解可以确定json名到model属性名的映射关系
 */
data class UserProfileResponse(
    @SerializedName("status_code")
    val statusCode:Long,
    @SerializedName("user")
    val userProfile:UserProfile
)

data class UserProfile(
    @SerializedName("nickname")
    var nickName:String,//昵称
    @SerializedName("uid")
    val uid:Long,//抖音号
    @SerializedName("signature")
    var signature:String,//个人简介
    @SerializedName("aweme_count")
    var awemeCount:Long,//获赞数
    @SerializedName("following_count")
    var followingCount:Long,//关注数
    @SerializedName("follower_count")
    var followerCount:Long//粉丝数
)


