package com.example.pocvideo.model.upload_video


import com.google.gson.annotations.SerializedName

data class UploadResponse(
    @SerializedName("message")
    var result: String
)