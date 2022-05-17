package com.example.unoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class Home_Page : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)

        val button1=findViewById<ImageButton>(R.id.imageButton8)
       button1.setOnClickListener{
            val Intent= Intent(this,Create_Room_pg::class.java)
            startActivity(Intent)
        }

        val button2=findViewById<ImageButton>(R.id.imageButton9)
        button2.setOnClickListener{
            val Intent= Intent(this,JoinNow_Page::class.java)
            startActivity(Intent)
        }

        val button3=findViewById<ImageButton>(R.id.imageButton10)
        button3.setOnClickListener{
            val Intent= Intent(this,PlayWithMachine_Page::class.java)
            startActivity(Intent)
        }

        val button4=findViewById<ImageButton>(R.id.imageButton9)
        button4.setOnClickListener{
            val Intent= Intent(this,JoinNow_Page::class.java)
            startActivity(Intent)
        }

        val button5=findViewById<ImageButton>(R.id.imageButton10)
        button5.setOnClickListener{
            val Intent= Intent(this,PlayWithMachine_Page::class.java)
            startActivity(Intent)
        }




    }
}