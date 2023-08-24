package com.example.pocvideo.model.videos_response


import com.google.gson.annotations.SerializedName

data class Video(
    @SerializedName("created_at")
    var createdAt: String,
    @SerializedName("file_path")
    var filePath: String,
    @SerializedName("id")
    var id: Int,
    @SerializedName("title")
    var title: String,
    @SerializedName("updated_at")
    var updatedAt: String
)