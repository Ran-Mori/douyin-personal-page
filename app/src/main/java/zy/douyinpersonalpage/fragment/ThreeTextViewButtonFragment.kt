package zy.douyinpersonalpage.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*
import zy.douyinpersonalpage.R

/**
 * '作品 - 私密 - 喜欢'三个按钮
 */
class ThreeTextViewButtonFragment:Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.three_textview_button,container,false)

        /**
         * 同一个Activity不同的Fragment之间进行通信
         */
        view.findViewById<TextView>(R.id.postTextView).setOnClickListener {
            activity?.bottomViewPager?.setCurrentItem(0,true)
        }
        view.findViewById<TextView>(R.id.privateTextView).setOnClickListener {
            activity?.bottomViewPager?.setCurrentItem(1,true)
        }
        view.findViewById<TextView>(R.id.favoriteTextView).setOnClickListener {
            activity?.bottomViewPager?.setCurrentItem(2,true)
        }

        return view
    }
}