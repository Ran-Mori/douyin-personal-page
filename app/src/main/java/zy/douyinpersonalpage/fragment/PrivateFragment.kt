package zy.douyinpersonalpage.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import zy.douyinpersonalpage.R
import zy.douyinpersonalpage.adapter.VideoAdapter
import zy.douyinpersonalpage.model.Video


class PrivateFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_private, container, false)

        /**
         * 处理RecyclerView的相关操作
         */
        val recyclerView = view.findViewById<RecyclerView>(R.id.privateRecyclerView)
        val layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.layoutManager = layoutManager

        /**
         * 首先给adapter一个空的数据源
         * ViewModel在进行初始化时一旦为null会执行一次调用网络请求，会更新数据源
         * 这样就通过观察获取最新数据源
         */
        val adapter = VideoAdapter(ArrayList<Video>())
        recyclerView.adapter = adapter

        return view
    }
}