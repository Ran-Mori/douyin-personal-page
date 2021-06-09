package zy.douyinpersonalpage.fragment

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.facebook.drawee.view.SimpleDraweeView
import kotlinx.android.synthetic.main.fragment_top.*
import zy.douyinpersonalpage.R
import zy.douyinpersonalpage.activity.AvatarPreviewActivity
import zy.douyinpersonalpage.constant.MyConstant
import zy.douyinpersonalpage.singleton.MySingleTon
import zy.douyinpersonalpage.viewmodel.TopFragmentViewModel
import zy.douyinpersonalpage.viewmodel.factory.TopFragmentViewModelFactory


/**
 * 上部分UI，基本都是个人的信息
 */
class TopFragment : Fragment() {

    /**
     * 延迟初始化此类对应的ViewModel
     */
    private lateinit var viewModel: TopFragmentViewModel

    /**
     * 尽量提前初始化ViewModel，因此把ViewModel的初始化放在onAttach方法里面
     */
    override fun onAttach(context: Context) {
        super.onAttach(context)

        /**
         * 此处使用工厂方式创建ViewModel，暂时没有考虑本地缓存。因此一直都是传null进去
         */
        viewModel = ViewModelProvider(this, TopFragmentViewModelFactory(null)).get(
            TopFragmentViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_top, container, false)

        /**
         * 一定有了view后才能使用findViewById()
         * 使用观察者模式动态修改页面UI上的内容
         */
        viewModel.userProfile.observe(viewLifecycleOwner){
            nickNameTextView.text = it.nickName
            uidTextView.text = "抖音号:${it.uid}"
            signatureTextView.text = it.signature
            awemeCountTextView.text = "${MySingleTon.bigNumberConverter(it.awemeCount)}"
            followingCountTextView.text = "${MySingleTon.bigNumberConverter(it.followingCount)}"
            followerCountTextView.text = "${MySingleTon.bigNumberConverter(it.followerCount)}"

        }

        /**
         * 设置头像的URL和头像点击事件
         */
        view.findViewById<SimpleDraweeView>(R.id.avatarSimpleDraweeView).apply {
            val uri = Uri.parse(MyConstant.AVATAR_URL)
            setImageURI(uri,activity)
            setOnClickListener {
                val intent = Intent(activity,AvatarPreviewActivity::class.java)
                activity?.startActivity(intent)
            }
        }

        return view
    }
}