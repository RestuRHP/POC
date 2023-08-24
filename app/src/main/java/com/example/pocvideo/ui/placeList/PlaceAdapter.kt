package com.example.pocvideo.ui.placeList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pocvideo.R
import com.example.pocvideo.model.placeList.Place

class PlaceAdapter(private val placeList: List<Place>) :
    RecyclerView.Adapter<PlaceAdapter.PlaceViewHolder>() {

    private var loadedImageCount = 0
    private var expectedImageCount = 0
    private var imageLoadListener: OnImageLoadListener? = null

    fun setOnImageLoadListener(listener: OnImageLoadListener) {
        imageLoadListener = listener
    }

    interface OnImageLoadListener {
        fun onImageLoadComplete()
    }

    class PlaceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imagePlace = itemView.findViewById<ImageView>(R.id.imagePlace)
        val textNamePlace = itemView.findViewById<TextView>(R.id.textNamePlace)
        val textRating = itemView.findViewById<TextView>(R.id.textRating)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_place, parent, false)
        return PlaceViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PlaceViewHolder, position: Int) {
        val currentPlace = placeList[position]

        holder.textNamePlace.text = currentPlace.namePlace
        holder.textRating.text = currentPlace.rating.toString()

        expectedImageCount++
        Glide.with(holder.itemView.context)
            .load(currentPlace.image)
            .into(holder.imagePlace)

        holder.itemView.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                holder.itemView.viewTreeObserver.removeOnGlobalLayoutListener(this)
                loadedImageCount++
                if (loadedImageCount == expectedImageCount) {
                    imageLoadListener?.onImageLoadComplete()
                }
            }
        })
    }

    override fun getItemCount(): Int {
        return placeList.size
    }
}

