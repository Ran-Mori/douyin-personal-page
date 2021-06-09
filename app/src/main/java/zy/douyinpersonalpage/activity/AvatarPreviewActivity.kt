package zy.douyinpersonalpage.activity

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_avatar_preview.*
import zy.douyinpersonalpage.R
import zy.douyinpersonalpage.constant.MyConstant
import java.net.URI

/**
 * 单击头像框出现了预览头像页面
 */
class AvatarPreviewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_avatar_preview)


        /**
         * 隐藏最上面的action bar
         */
        supportActionBar?.hide()

        /**
         * 设置图片URL
         */
        val uri = Uri.parse(MyConstant.AVATAR_URL)
        avatarPreviewSimpleDraweeView.setImageURI(uri,this)

        /**
         * 设置点击返回
         */
        avatarPreviewSimpleDraweeView.setOnClickListener {
            this.finish()
        }
    }
}