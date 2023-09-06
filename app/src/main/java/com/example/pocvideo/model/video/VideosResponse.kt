package com.example.pocvideo.model.video


import com.google.gson.annotations.SerializedName

data class VideosResponse(
    @SerializedName("videos")
    var videos: List<Video>
)