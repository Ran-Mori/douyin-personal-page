package zy.douyinpersonalpage.adapter

import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.facebook.drawee.view.SimpleDraweeView
import zy.douyinpersonalpage.R
import zy.douyinpersonalpage.model.Video
import zy.douyinpersonalpage.singleton.MySingleTon

/**
 * RecycleListView的数据源
 */
class VideoAdapter(var list:MutableList<Video>) : RecyclerView.Adapter<VideoAdapter.ViewHolder>() {
    inner class ViewHolder(view:View) : RecyclerView.ViewHolder(view){
        /**
         * 设置这个ViewHolder究竟holde什么view
         */
        val videoImageView = view.findViewById<SimpleDraweeView>(R.id.videoSimpleDraweeView)
        val videoAwemeCount = view.findViewById<TextView>(R.id.videoAwemeCountTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //Log.d("MainActivity","onCreateViewHolder")
        val view = LayoutInflater.from(parent.context).inflate(R.layout.video_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //Log.d("MainActivity","onBindViewHolder")
        val currentVideo = list[position]

        /**
         * 设置点赞数
         */
        holder.videoAwemeCount.text = MySingleTon.bigNumberConverter(currentVideo.awemeCount)

        /**
         * 加载图片，使用Fresco框架，直接把URL仍进入，啥多线程处理缓存啥的全部不用管了。就很离谱和方便
         */
        val uri = Uri.parse(currentVideo.coverUrl)
        holder.videoImageView.setImageURI(uri,this)
    }
    override fun getItemCount():Int {
        //Log.d("MainActivity","getItemCount")
        return list.size
    }
}