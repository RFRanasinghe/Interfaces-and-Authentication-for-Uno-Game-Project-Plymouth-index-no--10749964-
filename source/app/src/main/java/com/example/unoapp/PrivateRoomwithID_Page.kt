package com.example.unoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.getField
import com.google.firebase.ktx.Firebase


//creates a room id and shows
class PrivateRoomwithID_Page : AppCompatActivity() {
    var isPlayerConnecting: Boolean = true;
    val room_id_random = (1000 .. 2000).random().toString();
    override fun onStart() {
        super.onStart()
        val currentuser = Firebase.auth.currentUser;
        var pb = findViewById<ProgressBar>(R.id.progressBar);
        val loadingText = findViewById<TextView>(R.id.loadingText);
        if (currentuser != null) {
            Firebase.firestore.collection("games").document(room_id_random).addSnapshotListener{snapshot , e ->
                run {
                    if (snapshot != null && snapshot.exists()) {
                        val player_one_uid =  snapshot.getField<String>("player_one")
                        val player_two_uid = snapshot.getField<String>("player_two")
                        if(player_one_uid!=null){
                            Toast.makeText(this,"player one"+player_one_uid.toString(),Toast.LENGTH_LONG).show();
                        }
                        if(player_two_uid!=null){
                            //player two has connected , stop the progress bar and show his name set isPlayerConnecting to false;
                            isPlayerConnecting = false;
                            pb.visibility = View.GONE;
                            loadingText.setText("PLayer two has connected");
                            Toast.makeText(this, "Player two has connected ", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }
            Firebase.firestore.collection("games").document(room_id_random).
            set(hashMapOf("player_one" to  currentuser.uid)).addOnCompleteListener(this){task ->
                run {
                    if (task.isSuccessful) {
                        val room_id_txt = findViewById<TextView>(R.id.room_id);
                        room_id_txt.setText(room_id_random) ;
                    }
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_private_roomwith_id_page)
        val play_btn =findViewById<ImageButton>(R.id.imageButton7)
        play_btn.setOnClickListener{
            if(isPlayerConnecting){
                Toast.makeText(this,"Player still connecting",Toast.LENGTH_LONG).show();
            }else{
                val i = Intent(this,playwithfriendsInt::class.java)
                i.putExtra("roomID",room_id_random);
                startActivity(i);
            }
        }
    }
}