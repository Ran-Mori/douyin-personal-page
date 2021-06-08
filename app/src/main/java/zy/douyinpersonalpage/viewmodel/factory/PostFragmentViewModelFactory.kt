package zy.douyinpersonalpage.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import zy.douyinpersonalpage.model.Video
import zy.douyinpersonalpage.viewmodel.PostFragmentViewModel

/**
 * 工厂获得ViewModel，适用于有参数的ViewModel
 */
class PostFragmentViewModelFactory(private val list: MutableList<Video>?):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PostFragmentViewModel(list) as T
    }
}