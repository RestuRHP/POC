package com.example.pocvideo.api

import com.example.pocvideo.model.video.UploadResponse
import com.example.pocvideo.model.video.VideosResponse
import com.example.pocvideo.model.youtube.GetYoutubeIdResponse
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface Api {

    @POST("videos")
    fun uploadVideo(
        @Body requestBody: RequestBody,
    ): Call<UploadResponse>

    @GET("videos")
    fun getVideoList(): Call<VideosResponse>

    @POST("youtube")
    fun uploadYoutubeId(
        @Body requestBody: RequestBody,
    ): Call<UploadResponse>

    @GET("youtube")
    fun getYoutubeIdList(): Call<GetYoutubeIdResponse>
}