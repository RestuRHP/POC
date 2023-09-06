package com.example.pocvideo

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.pocvideo.ui.placeList.PlaceListActivity
import com.example.pocvideo.ui.video.VideoActivity

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



