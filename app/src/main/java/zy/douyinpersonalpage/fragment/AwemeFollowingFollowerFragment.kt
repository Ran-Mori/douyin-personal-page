package zy.douyinpersonalpage.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import zy.douyinpersonalpage.R

class AwemeFollowingFollowerFragment: Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.aweme_following_follower,container,false)
        return view
    }
}