package com.example.unoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class Create_Room_pg : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_room_pg)

        val button1=findViewById<ImageButton>(R.id.imageButton11)
        button1.setOnClickListener{
            val Intent= Intent(this,PrivateRoomwithID_Page::class.java)
            startActivity(Intent)
        }

        val button2=findViewById<ImageButton>(R.id.imageButton12)
        button2.setOnClickListener{
            val Intent= Intent(this,ScheduleGame_Page::class.java)
            startActivity(Intent)
        }

        val button3=findViewById<ImageButton>(R.id.imageButton15)
        button3.setOnClickListener{
            val Intent= Intent(this,Home_Page::class.java)
            startActivity(Intent)
        }
    }
}