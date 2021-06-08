package zy.douyinpersonalpage.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import zy.douyinpersonalpage.R
import zy.douyinpersonalpage.adapter.VideoAdapter
import zy.douyinpersonalpage.model.Video
import zy.douyinpersonalpage.viewmodel.FavoriteFragmentViewModel
import zy.douyinpersonalpage.viewmodel.factory.FavoriteFragmentViewModelFactory


class FavoriteFragment : Fragment() {

    private lateinit var viewModel:FavoriteFragmentViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel = ViewModelProvider(this,FavoriteFragmentViewModelFactory(null)).get(FavoriteFragmentViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_favorite, container, false)

        /**
         * 处理RecyclerView的相关操作
         */
        val recyclerView = view.findViewById<RecyclerView>(R.id.favoriteRecyclerView)
        val layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.layoutManager = layoutManager

        /**
         * 首先给adapter一个空的数据源
         * ViewModel在进行初始化时一旦为null会执行一次调用网络请求，会更新数据源
         * 这样就通过观察获取最新数据源
         */
        val adapter = VideoAdapter(ArrayList<Video>())
        recyclerView.adapter = adapter

        /**
         * 建立观察
         */
        viewModel.videoList.observe(viewLifecycleOwner) {
            adapter.list = it
            adapter.notifyDataSetChanged()
        }

        return view
    }
}