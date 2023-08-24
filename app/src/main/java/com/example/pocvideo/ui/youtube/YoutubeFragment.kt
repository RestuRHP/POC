package com.example.pocvideo.ui.youtube

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import com.example.pocvideo.R
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.FullscreenListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

class YoutubeFragment : Fragment() {

    private lateinit var youTubePlayer: YouTubePlayer
    private var isYouTubePlayerReady = false

    private var isFullscreen = true

    private var isPlay = false

    companion object {
        private const val ARG_VIDEO_ID = "video_id"

        fun newInstance(youtubeId: String): YoutubeFragment {
            val args = Bundle()
            args.putString(ARG_VIDEO_ID, youtubeId)
            val fragment = YoutubeFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_youtube, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val youtubePlayerView = view.findViewById<YouTubePlayerView>(R.id.youtube_player_view)
        val fullscreenViewContainer =
            view.findViewById<FrameLayout>(R.id.full_screen_view_container)
        val youtubeId = arguments?.getString(ARG_VIDEO_ID) ?: ""
        Log.e("YoutubeID", youtubeId)

        val iFramePlayerOptions = IFramePlayerOptions.Builder()
            .controls(0)
            .fullscreen(0)
            .build()

        youtubePlayerView.enableAutomaticInitialization = false

        youtubePlayerView.addFullscreenListener(object : FullscreenListener {
            override fun onEnterFullscreen(fullscreenView: View, exitFullscreen: () -> Unit) {
                isFullscreen = true

                youtubePlayerView.visibility = View.GONE
                fullscreenViewContainer.visibility = View.VISIBLE
                fullscreenViewContainer.addView(fullscreenView)
            }

            override fun onExitFullscreen() {
                isFullscreen = false

                youtubePlayerView.visibility = View.VISIBLE
                fullscreenViewContainer.visibility = View.GONE
                fullscreenViewContainer.removeAllViews()
            }
        })

        youtubePlayerView.initialize(object : AbstractYouTubePlayerListener() {
            override fun onReady(player: YouTubePlayer) {
                youTubePlayer = player
                isYouTubePlayerReady = true

                youTubePlayer.addListener(object : AbstractYouTubePlayerListener() {
                    override fun onStateChange(
                        youTubePlayer: YouTubePlayer,
                        state: PlayerConstants.PlayerState
                    ) {
                        if (state == PlayerConstants.PlayerState.ENDED) {
                            youTubePlayer.loadVideo(youtubeId, 0f)
                            youTubePlayer.play()
                        }

                        if (!isPlay && state == PlayerConstants.PlayerState.PAUSED) {
                            youTubePlayer.play()
                        }

                        if (state == PlayerConstants.PlayerState.PLAYING) {
                            isPlay = true
                        }
                    }
                })
                youTubePlayer.loadVideo(youtubeId, 0f)
                youTubePlayer.play()
                youTubePlayer.toggleFullscreen()
            }
        }, iFramePlayerOptions)


        lifecycle.addObserver(youtubePlayerView)
    }

    override fun onPause() {
        super.onPause()
        if (isYouTubePlayerReady) {
            youTubePlayer.pause()
        }

    }

    override fun onResume() {
        super.onResume()
        if (isYouTubePlayerReady) {
            youTubePlayer.play()
        }
    }
}
