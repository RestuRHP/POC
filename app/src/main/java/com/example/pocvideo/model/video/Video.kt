package com.example.pocvideo.model.video


import com.google.gson.annotations.SerializedName

data class Video(
    @SerializedName("created_at")
    var createdAt: String,
    @SerializedName("file_path")
    var filePath: String,
    @SerializedName("id")
    var id: Int,
    @SerializedName("updated_at")
    var updatedAt: String
)