package com.example.pocvideo.model.placeList

data class MyPlaceList(
    val title: String,
    val subTitle: String,
    val nameUser: String,
    val avatar: String,
    val listPlace: List<Place>,
    val totalPlace: Int,
    val totalLike: Int,
    val totalShare: Int
)
