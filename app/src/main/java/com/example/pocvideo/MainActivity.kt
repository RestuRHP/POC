package com.example.pocvideo

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.webkit.MimeTypeMap
import android.widget.Toast
import android.widget.Toolbar
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.pocvideo.api.ApiService
import com.example.pocvideo.model.upload_video.UploadResponse
import com.example.pocvideo.model.videos_response.VideosResponse
import com.example.pocvideo.ui.placeList.PlaceListActivity
import com.example.pocvideo.ui.video.VideoActivity
import com.example.pocvideo.ui.video.VideoPagerAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.progressindicator.LinearProgressIndicator
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream

class MainActivity : AppCompatActivity() {

    private lateinit var toolbar:androidx.appcompat.widget.Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById(R.id.toolbar)

        setSupportActionBar(toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.video -> {
                val placeListIntent = Intent(this, VideoActivity::class.java)
                startActivity(placeListIntent)
                true
            }
            R.id.place_list -> {
                val placeListIntent = Intent(this, PlaceListActivity::class.java)
                startActivity(placeListIntent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}



