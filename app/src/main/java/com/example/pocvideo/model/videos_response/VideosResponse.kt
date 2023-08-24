package com.example.pocvideo.model.videos_response


import com.google.gson.annotations.SerializedName

data class VideosResponse(
    @SerializedName("videos")
    var videos: List<Video>
)