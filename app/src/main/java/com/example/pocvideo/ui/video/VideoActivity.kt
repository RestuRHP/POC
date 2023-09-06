package com.example.pocvideo.ui.video

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.viewpager2.widget.ViewPager2
import com.example.pocvideo.R
import com.example.pocvideo.api.ApiService
import com.example.pocvideo.model.video.UploadResponse
import com.example.pocvideo.model.video.VideosResponse
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.progressindicator.LinearProgressIndicator
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class VideoActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2
    private lateinit var fabButton: FloatingActionButton
    lateinit var adapter: VideoPagerAdapter
    private lateinit var linearProgressIndicator: LinearProgressIndicator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)

        viewPager = findViewById(R.id.view_pager)
        adapter = VideoPagerAdapter(this)
        viewPager.adapter = adapter

        fabButton= findViewById(R.id.fab_btn_upload)
        fabButton.setOnClickListener(::openVideoPicker)
        linearProgressIndicator=findViewById(R.id.linear_progress)

        getVideoListFromServer()
    }

    private fun getVideoListFromServer() {
        val apiService = ApiService.create()
        val call = apiService.getVideoList()
        call.enqueue(object : Callback<VideosResponse> {
            override fun onResponse(
                call: Call<VideosResponse>, response: Response<VideosResponse>
            ) {
                Log.d("VIDEOS Response", "${response.body()}")
                if (response.isSuccessful) {
                    Log.d("VIDEOS Response", "${response.body()}")
                    val videoList = response.body()?.videos
                    if (!videoList.isNullOrEmpty()) {
                        adapter.setVideoList(videoList)
                    }
                    Toast.makeText(this@VideoActivity,"Get Data Success", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this@VideoActivity,"Get Data Failure", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<VideosResponse>, t: Throwable) {
                Toast.makeText(this@VideoActivity,"Get Data Failure Throw", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun openVideoPicker(view: View) {
        val intent = Intent(Intent.ACTION_PICK)
        intent.setDataAndType(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,"video/*")
        resultLauncher.launch(intent)
    }

    private var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {

            val data: Intent? = result.data
            val videoUri: Uri? = data?.data
            videoUri?.let {
                val videoPath = getPathFromUri(this, it)
                uploadVideoToServer(videoPath)
            }
        }
    }

    private fun getPathFromUri(context: Context, uri: Uri): String {
        val projection = arrayOf(MediaStore.Video.Media.DATA)
        context.contentResolver.query(uri, projection, null, null, null)?.use {
            if (it.moveToFirst()) {
                val columnIndex = it.getColumnIndexOrThrow(MediaStore.Video.Media.DATA)
                return it.getString(columnIndex)
            } else {
                Log.e("UploadFragment", "Cursor is empty")
            }
        }
        return ""
    }

    private fun uploadVideoToServer(videoPath: String) {
        val videoFile = File(videoPath)
        val fileExtension = MimeTypeMap.getFileExtensionFromUrl(videoFile.absolutePath)
        val mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileExtension)

        val requestFile = videoFile.asRequestBody(mimeType?.toMediaTypeOrNull())

        val requestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("video", videoFile.name, requestFile)
            .build()

        val apiService = ApiService.create()
        val call = apiService.uploadVideo(requestBody)

        linearProgressIndicator.visibility = View.VISIBLE

        call.enqueue(object : Callback<UploadResponse> {
            override fun onResponse(call: Call<UploadResponse>, response: Response<UploadResponse>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@VideoActivity, "Upload Success", Toast.LENGTH_SHORT).show()
                    getVideoListFromServer()
                } else {
                    Toast.makeText(this@VideoActivity, "Upload Failure", Toast.LENGTH_SHORT).show()
                    Log.e("Upload Error", response.message())
                }
                linearProgressIndicator.visibility = View.GONE
            }

            override fun onFailure(call: Call<UploadResponse>, t: Throwable) {
                Toast.makeText(this@VideoActivity, "Upload Failure", Toast.LENGTH_SHORT).show()
                t.message?.let { Log.e("Upload Error", it) }
                linearProgressIndicator.visibility = View.GONE
            }
        })
    }

}