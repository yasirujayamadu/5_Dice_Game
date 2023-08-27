package com.example.a5dicegame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AlertDialog

class HomePage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)

        supportActionBar?.hide()
        val startButton = findViewById<Button>(R.id.button3)
        startButton.setOnClickListener {
            val start = Intent(this,settings::class.java)
            startActivity(start)
        }
        val about = findViewById<Button>(R.id.about)
        about.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("20210304-M.G.Yasiru Jayamadu")
            builder.setMessage("I confirm that I understand what plagiarism is and have read and" +
                    "     understood the section on Assessment Offences in the Essential" +
                    " Information for Students.  The work that I have submitted is" +
                    " entirely my own. Any work from other authors is duly referenced" +
                    " and acknowledged.")
            builder.setPositiveButton("OK", null)
            val dialog = builder.create()
            dialog.show()
        }


    }
}