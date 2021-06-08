package zy.douyinpersonalpage.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.aweme_following_follower.*
import kotlinx.android.synthetic.main.personal_information.*
import zy.douyinpersonalpage.R
import zy.douyinpersonalpage.viewmodel.PersonalInformationFragmentViewModel
import zy.douyinpersonalpage.viewmodel.factory.PersonalInformationFragmentViewModelFactory


/**
 * 个人信息页面逻辑代码
 */
class PersonalInformationFragment:Fragment() {

    /**
     * 延迟初始化此类对应的ViewModel
     */
    private lateinit var viewModel:PersonalInformationFragmentViewModel

    /**
     * 尽量提前初始化ViewModel，因此把ViewModel的初始化放在onAttach方法里面
     */
    override fun onAttach(context: Context) {
        super.onAttach(context)

        /**
         * 此处使用工厂方式创建ViewModel，暂时没有考虑本地缓存。因此一直都是传null进去
         */
        viewModel = ViewModelProvider(this,PersonalInformationFragmentViewModelFactory(null)).get(PersonalInformationFragmentViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.personal_information,container,false)

        /**
         * 一定有了view后才能使用findViewById()
         * 使用观察者模式动态修改页面UI上的内容
         */
        viewModel.userProfile.observe(viewLifecycleOwner){
            nickNameTextView.text = it.nickName
            uidTextView.text = "抖音号:${it.uid}"
            signatureTextView.text = it.signature
            awemeCountTextView.text = "${it.awemeCount}获赞"
            followingCountTextView.text = "${it.followingCount}关注"
            followerCountTextView.text = "${it.followerCount}粉丝"
        }
        return view
    }
}