package com.example.a5dicegame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //https://drive.google.com/drive/folders/1zJKUP9Prcf5QvSEhStlSiNncGmXusMcS?usp=share_link
        Handler().postDelayed({
            val intent = Intent(this@MainActivity, HomePage::class.java)
            startActivity(intent)
            finish()
        }, 3000)
    }
}

