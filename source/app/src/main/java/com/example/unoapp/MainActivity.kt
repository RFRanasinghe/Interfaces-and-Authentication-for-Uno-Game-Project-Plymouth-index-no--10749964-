package com.example.unoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = Firebase.auth;

        val sign_in_btn =findViewById<ImageButton>(R.id.imageButton)
        val button2=findViewById<ImageButton>(R.id.imageButton2)
        val button3=findViewById<ImageButton>(R.id.imageButton13)
        val email_txt = findViewById<EditText>(R.id.editTextTextEmailAddress);
        val password_txt = findViewById<EditText>(R.id.editTextTextPassword);

        button2.setOnClickListener{
            val Intent= Intent(this,Register_Page::class.java)
            startActivity(Intent)
        }
        button3.setOnClickListener{
            val Intent= Intent(this,Welcome_Page::class.java)
            startActivity(Intent)
        }

        sign_in_btn.setOnClickListener{
                var email =  email_txt.text.toString();
                var password = password_txt.text.toString();
                LoginWithEmailAndPassword(email, password);
        }
    }


    private fun LoginWithEmailAndPassword(email: String , password: String){
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this){
            task ->
            run {
                if (task.isSuccessful) {
                    val Intent = Intent(this, Home_Page::class.java)
                    startActivity(Intent)
                }else{
                    Toast.makeText(this , "Error",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}


///THE PERSON WHO MAKES THE ROOM IS PLAYER ONE, THE PERSON WHO JOINS THE ROOM IS PLAYER TWO