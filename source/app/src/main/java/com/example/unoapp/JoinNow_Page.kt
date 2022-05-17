package com.example.unoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.getField
import com.google.firebase.ktx.Firebase

class JoinNow_Page : AppCompatActivity() {

    override fun onStart() {
        super.onStart()
        val cu = Firebase.auth.currentUser;
        if (cu==null){
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join_now_page)
        val join_now_btn =findViewById<ImageButton>(R.id.join_now)
        val room_id_input = findViewById<EditText>(R.id.room_id_input)
        join_now_btn.setOnClickListener{
            var room_id = room_id_input.text.toString();
            Firebase.firestore.collection("games").document(room_id).get().addOnSuccessListener { doc->
                run {
                    if(doc!=null){
                        if(doc.exists()){
                            val player_one_uid =   doc.getField<String>("player_one");
                            val player_two_uid = Firebase.auth.currentUser?.uid;
                            Firebase.firestore.collection("games").document(room_id).
                            set(hashMapOf("player_two" to  player_two_uid, "player_one" to player_one_uid)).addOnCompleteListener(this){ task ->
                                run {
                                    if (task.isSuccessful) {
                                        //playwithfriendsInt
                                        val i = Intent(this, playwithfriendsInt::class.java)
                                        i.putExtra("roomID",room_id);
                                        startActivity(i);
                                    }
                                }
                            }

                        }
                    }
                }
            }
        }

        val button2=findViewById<ImageButton>(R.id.imageButton17)
        button2.setOnClickListener{
            val Intent= Intent(this,Home_Page::class.java)
            startActivity(Intent)
        }

    }
}