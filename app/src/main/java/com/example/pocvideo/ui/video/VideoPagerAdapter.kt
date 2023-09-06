package com.example.pocvideo.ui.video

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.pocvideo.model.video.Video

class VideoPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    private var videoList: List<Video> = emptyList()

    fun setVideoList(videoList: List<Video>) {
        this.videoList = videoList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = videoList.size

    override fun createFragment(position: Int): Fragment {
        val videoItem = videoList[position]
        return VideoFragment.newInstance(videoItem.filePath)
    }
}
