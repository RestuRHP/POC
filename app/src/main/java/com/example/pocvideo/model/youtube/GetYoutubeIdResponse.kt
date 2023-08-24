package com.example.pocvideo.model.youtube


import com.google.gson.annotations.SerializedName

data class GetYoutubeIdResponse(
    @SerializedName("youtube_ids")
    var youtubeIds: List<String>
)