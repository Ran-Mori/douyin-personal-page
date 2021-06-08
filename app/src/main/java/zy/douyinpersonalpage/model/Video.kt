package zy.douyinpersonalpage.model

/**
 * 视频类的Model
 * 获取时使用ResponseBody自定义解析，因此不用按照json格式做类的嵌套
 */
data class  Video(
    val description: String,
    val coverUrl: String,
    val awemeCount: Long
)