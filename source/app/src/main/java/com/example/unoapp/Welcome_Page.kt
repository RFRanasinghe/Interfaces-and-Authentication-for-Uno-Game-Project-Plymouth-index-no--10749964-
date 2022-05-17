package com.example.unoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class Welcome_Page : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth;

    override fun onStart() {
        super.onStart()
        auth = Firebase.auth;
        val current_user = auth.currentUser;
        if(current_user!=null){
            val Intent = Intent(this, Home_Page::class.java)
            startActivity(Intent)
        }

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome_page)

        val button1=findViewById<ImageButton>(R.id.imageButton5)
        button1.setOnClickListener{
            val Intent= Intent(this,MainActivity::class.java)
            startActivity(Intent)
        }


    }
}