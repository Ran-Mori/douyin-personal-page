package zy.douyinpersonalpage.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import zy.douyinpersonalpage.constant.MyConstant
import zy.douyinpersonalpage.fragment.FavoriteFragment
import zy.douyinpersonalpage.fragment.PostFragment
import zy.douyinpersonalpage.fragment.PrivateFragment

/**
 * ViewPager adapter
 */
class MyViewPager2Adapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {

    /**
     * 延迟初始化Fragment
     */
    private val postFragment:PostFragment by lazy { PostFragment() }
    private val privateFragment : PrivateFragment by lazy { PrivateFragment() }
    private val favoriteFragment : FavoriteFragment by lazy { FavoriteFragment() }


    override fun getItemCount() = MyConstant.VIEW_PAGER2_COUNT


    /**
     * 根据位置不同来返回不同的Fragment
     */
    override fun createFragment(position: Int): Fragment {
        when(position){
            0 -> return postFragment
            1 -> return privateFragment
            else -> return favoriteFragment
        }
    }
}