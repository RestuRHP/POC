package com.example.pocvideo.ui.placeList

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.format.DateFormat
import android.view.ViewTreeObserver
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pocvideo.R
import com.example.pocvideo.model.placeList.MyPlaceList
import com.example.pocvideo.model.placeList.Place
import de.hdodenhof.circleimageview.CircleImageView
import java.io.File
import java.io.FileOutputStream
import java.util.Date

class PlaceListActivity : AppCompatActivity() {
    private lateinit var mainLayout:ConstraintLayout

    val placeList = listOf(
        Place("Bakmi Place 1", "https://picsum.photos/id/63/296", 4.5),
        Place("Bakmi Place 2", "https://picsum.photos/id/139/296", 3.8),
        Place("Bakmi Place 3", "https://picsum.photos/id/152/296", 4.2),
        Place("Bakmi Place 4", "https://picsum.photos/id/248/296", 4.0),
        Place("Bakmi Place 5", "https://picsum.photos/id/250/296", 4.7)
    )

    val myPlaceList = MyPlaceList(
        title = "Bakmi Karawang",
        subTitle = "Kumpulan bakmi terenak di karawang!",
        nameUser = "John Doe",
        avatar = "https://www.w3schools.com/howto/img_avatar.png",
        listPlace = placeList,
        totalPlace = placeList.size,
        totalLike = 120,
        totalShare = 50
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_place_list)

        mainLayout=findViewById(R.id.bg_main_layout)

        initializeData()

        mainLayout.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                mainLayout.viewTreeObserver.removeOnGlobalLayoutListener(this)
                Handler(Looper.getMainLooper()).postDelayed({
                    captureScreen()
                }, 1000)
            }
        })

    }

    private fun initializeData(){

        val title=findViewById<TextView>(R.id.tvTitle)
        val subTitle=findViewById<TextView>(R.id.tvSubtitle)
        var avatar=findViewById<CircleImageView>(R.id.imageAvatar)

        title.text =myPlaceList.title
        subTitle.text=myPlaceList.subTitle

        val recyclerView: RecyclerView = findViewById(R.id.rv)
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        val adapter = PlaceAdapter(myPlaceList.listPlace)
        recyclerView.adapter = adapter
    }

    private fun captureScreen(){
        val now = Date()
        DateFormat.format("yyyy:mm:dd",now)

        val path=getExternalFilesDir(null)?.absolutePath +"/"+DateFormat.format("yyyy:mm:dd",now)+".jpg"
        val bitmap= Bitmap.createBitmap(mainLayout.width,mainLayout.height,Bitmap.Config.ARGB_8888)
        val canvas=Canvas(bitmap)
        mainLayout.draw(canvas)
        val imageFile= File(path)
        val outputStream=FileOutputStream(imageFile)
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream)
        outputStream.flush()
        outputStream.close()

        val URI=FileProvider.getUriForFile(applicationContext,"com.example.pocvideo.provider",imageFile)

        val intent = Intent()
        intent.action=Intent.ACTION_SEND
        intent.putExtra(Intent.EXTRA_TEXT,"Looks my PlaceList")
        intent.putExtra(Intent.EXTRA_STREAM,URI)
        intent.type = "image/jpeg"
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        startActivity(intent)
    }
}