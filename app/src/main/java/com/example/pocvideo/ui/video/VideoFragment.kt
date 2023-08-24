package com.example.pocvideo.ui.video

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import com.example.pocvideo.R
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.PlayerView

class VideoFragment : Fragment() {

    private lateinit var rootView: View
    private lateinit var playerView: PlayerView
    private lateinit var exoPlayer: ExoPlayer
    private lateinit var pauseIcon: ImageView
    private lateinit var playIcon: ImageView
    private lateinit var loadingProgress: ProgressBar
    private val hideIconsDelayMillis = 1000L // 500 milliseconds
    private val handler = Handler()
    private val hideIconsRunnable = Runnable {
        pauseIcon.visibility = View.GONE
        playIcon.visibility = View.GONE
    }
    private var isPlaying = false

    private var videoUrl: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        rootView = inflater.inflate(R.layout.fragment_video, container, false)

        playerView = rootView.findViewById(R.id.player_view)
        pauseIcon = rootView.findViewById(R.id.ic_pause)
        playIcon = rootView.findViewById(R.id.ic_play)
        loadingProgress = rootView.findViewById(R.id.loading_progress)
        val fadeInOutAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in_out)

        playerView.useController=false
        initializePlayer()

        videoUrl = arguments?.getString(ARG_VIDEO_URL)
        videoUrl?.let { playVideoFromURL(it) }

        playerView.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_UP -> {
                    if (exoPlayer.isPlaying) {
                        exoPlayer.pause()
                        pauseIcon.startAnimation(fadeInOutAnimation)
                        pauseIcon.visibility = View.VISIBLE
                        handler.removeCallbacks(hideIconsRunnable)
                        handler.postDelayed(hideIconsRunnable, hideIconsDelayMillis)
                    } else {
                        exoPlayer.play()
                        playIcon.startAnimation(fadeInOutAnimation)
                        playIcon.visibility = View.VISIBLE
                        handler.removeCallbacks(hideIconsRunnable)
                        handler.postDelayed(hideIconsRunnable, hideIconsDelayMillis)
                    }
                }
            }
            true
        }

        return rootView
    }


    private fun initializePlayer() {
        exoPlayer = ExoPlayer.Builder(requireContext()).build()
        playerView.player = exoPlayer
    }

    private fun playVideoFromURL(videoURL: String) {
        val mediaItem = MediaItem.fromUri(videoURL)
        exoPlayer.setMediaItem(mediaItem)
        exoPlayer.prepare()
        exoPlayer.playWhenReady = true
        exoPlayer.addListener(object : Player.Listener {
            override fun onIsPlayingChanged(isPlaying: Boolean) {
                this@VideoFragment.isPlaying = isPlaying
                if (isPlaying) {
                    loadingProgress.visibility = View.GONE
                } else if (!isPlaying && exoPlayer.playbackState == Player.STATE_BUFFERING) {
                    loadingProgress.visibility = View.VISIBLE
                } else {
                    loadingProgress.visibility = View.GONE
                }
            }

            override fun onPlaybackStateChanged(state: Int) {
                super.onPlaybackStateChanged(state)
                if(state == Player.STATE_ENDED){
                    exoPlayer.seekTo(0) // Start from the beginning
                    exoPlayer.play()
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        exoPlayer.seekTo(0)
        exoPlayer.play()
    }

    override fun onPause() {
        super.onPause()
        exoPlayer.pause()
        handler.removeCallbacks(hideIconsRunnable)
        loadingProgress.visibility = View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        exoPlayer.release()
    }

    companion object {
        private const val ARG_VIDEO_URL = "videoUrl"

        fun newInstance(videoUrl: String): VideoFragment {
            val fragment = VideoFragment()
            val args = Bundle()
            args.putString(ARG_VIDEO_URL, videoUrl)
            fragment.arguments = args
            return fragment
        }
    }
}

