package com.example.unoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import com.google.android.gms.common.api.internal.RegisterListenerMethod
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthRegistrar
import com.google.firebase.auth.ktx.FirebaseAuthKtxRegistrar
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Register_Page : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth;


    override fun onStart() {
        super.onStart()
        auth = Firebase.auth;
        val current_user = auth.currentUser
        if (current_user!=null){
            Toast.makeText(this,"Already logged in ",Toast.LENGTH_SHORT).show();
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_page)



        val button1=findViewById<ImageButton>(R.id.imageButton3)
        val button2=findViewById<ImageButton>(R.id.imageButton14)


        val register_btn = findViewById<ImageButton>(R.id.imageButton3);
        val username_text = findViewById<EditText>(R.id.editTextTextPersonName);
        val email_text = findViewById<EditText>(R.id.editTextTextEmailAddress2);
        val password = findViewById<EditText>(R.id.editTextTextPassword2);
        val comfirm_password = findViewById<EditText>(R.id.editTextTextPassword3);

        button1.setOnClickListener{
            val Intent= Intent(this,Home_Page::class.java)
            startActivity(Intent)
        }

        button2.setOnClickListener{
            val Intent= Intent(this,MainActivity::class.java)
            startActivity(Intent)
        }

        register_btn.setOnClickListener {
            var username = username_text.text.toString();
            var email = email_text.text.toString();
            var password = password.text.toString();
            var comfirm_password = comfirm_password.text.toString();
            if(password!= comfirm_password){
                Toast.makeText(this,"Passwords do not match",Toast.LENGTH_SHORT).show()
            }else {
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this){
                    task->
                    run {
                        val current_user = auth.currentUser;
                        current_user?.let { it1 ->
                            Firebase.firestore.collection("users").document(it1.uid).set(hashMapOf("username" to username)).addOnCompleteListener(this){
                                lol->
                                run {
                                    if (lol.isSuccessful) {
                                        val Intent = Intent(this, Home_Page::class.java)
                                        startActivity(Intent)
                                    }
                                }
                            }
                        }
                    }
                }
            }

        }


    }

}