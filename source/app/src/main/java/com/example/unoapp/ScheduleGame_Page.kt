package com.example.unoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class ScheduleGame_Page : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedule_game_page)

        val button1=findViewById<ImageButton>(R.id.imageButton6)
        button1.setOnClickListener{
            val Intent= Intent(this,JoinNow_Page::class.java)
            startActivity(Intent)
        }
        val button2=findViewById<ImageButton>(R.id.imageButton16)
        button2.setOnClickListener{
            val Intent= Intent(this,Create_Room_pg::class.java)
            startActivity(Intent)
        }
    }


}