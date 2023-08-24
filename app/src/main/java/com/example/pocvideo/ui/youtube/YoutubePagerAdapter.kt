package com.example.pocvideo.ui.youtube

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class YoutubePagerAdapter(fragment: Fragment, private val youtubeIds: List<String>) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = youtubeIds.size

    override fun createFragment(position: Int): Fragment {
        val videoId = youtubeIds[position]
        return YoutubeFragment.newInstance(videoId)
    }
}