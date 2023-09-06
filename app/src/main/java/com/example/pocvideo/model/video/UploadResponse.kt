package com.example.pocvideo.model.video


import com.google.gson.annotations.SerializedName

data class UploadResponse(
    @SerializedName("message")
    var result: String
)