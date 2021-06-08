package zy.douyinpersonalpage.fragment

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.facebook.drawee.view.SimpleDraweeView
import zy.douyinpersonalpage.R
import zy.douyinpersonalpage.constant.MyConstant

/**
 * '个人头像-编辑资料-添加按钮'三个组件
 */
class AvatarEditInfoAddFriendFragment:Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.avatar_editinfo_addfriend, container, false)

        /**
         * 从URI加载个人图像并且设置
         * 此处就不单独写一个ViewModel来获取头像URL了，直接把URL写死，默认是中国日报
         */
        val avatarImageView = view.findViewById<SimpleDraweeView>(R.id.avatarSimpleDraweeView)
        val uri = Uri.parse(MyConstant.AVATAR_URL)
        avatarImageView.setImageURI(uri,this)

        return view
    }
}