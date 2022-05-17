package com.example.unoapp

import android.content.Context
import android.graphics.drawable.Drawable
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.annotation.LongDef
import androidx.annotation.StringRes
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.getField
import com.google.firebase.ktx.Firebase
import java.util.*
import kotlin.random.Random
import kotlin.random.Random.Default.nextInt


//game play screen


 class Card(
     val imageResourceId : Int,
     val imageResoureName : String
)

class GameEngine{
    //todo
    public fun isCompatible(current_card: String, selected_card: String):Boolean{


        return true
    }

}


class ItemAdapter (private val context: Context , private val dataset : List<Card> ,private val roomId :String): RecyclerView.Adapter<ItemAdapter.ItemViewHolder>(){

    private var mutableList: MutableList<Card> = dataset as MutableList<Card>


    class ItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view){
        val imageView = view.findViewById<ImageView>(R.id.item_image);
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
            val adapterLayout = LayoutInflater.from(parent.context)
                .inflate(R.layout.card_item, parent, false);
        return ItemViewHolder(adapterLayout);
    }

    override fun onBindViewHolder(holder: ItemAdapter.ItemViewHolder, position: Int) {
         var item = mutableList[position];
         holder.imageView.setImageResource(item.imageResourceId);
         holder.imageView.setOnClickListener{
             Firebase.firestore.collection("gameplay").document(roomId).get().addOnSuccessListener { doc->
                 run {
                     val current_card = doc.getField<String>("card_name");
                     if (current_card != null) {
                         if (GameEngine().isCompatible(current_card,mutableList[position].imageResoureName)){
                             Firebase.firestore.collection("gameplay").document(roomId).set(hashMapOf(
                                 "card_name" to item.imageResoureName,
                             ))
                         }else{
                             //card cannot be put
                         }
                     }
                 }
             }
             Log.d("ITEM NAME", item.imageResoureName);
             mutableList.removeAt(position);
             notifyDataSetChanged();
        }
    }

    override fun getItemCount(): Int {
        return dataset.size;
    }
}

class playwithfriendsInt : AppCompatActivity() {
    val listRes: Map<String , Int> = mapOf(
        "red_one" to R.drawable.red_1 ,
        "red_two" to R.drawable.red_2 ,
        "red_three" to R.drawable.red_3 ,
        "red_four" to R.drawable.red_4 ,
        "red_five" to R.drawable.red_5 ,
        "red_six" to R.drawable.red_6 ,
        "red_eight" to R.drawable.red_8 ,
        "red_nine" to R.drawable.red_9 ,
        "red_draw" to R.drawable.red_draw ,
        "red_reverse" to R.drawable.red_reverse,
        "red_skip" to R.drawable.red_skip ,
        "blue_zero" to R.drawable.blue_0 ,
        "blue_one" to  R.drawable.blue_1 ,
        "blue_two" to R.drawable.blue_2 ,
        "blue_three" to  R.drawable.blue_3 ,
        "blue_four" to R.drawable.blue_4 ,
        "blue_five" to  R.drawable.blue_5 ,
        "blue_six" to  R.drawable.blue_6,
        "blue_seven" to R.drawable.blue_7,
        "blue_eight" to R.drawable.blue_8,
        "blue_nine" to   R.drawable.blue_9,
        "blue_draw" to R.drawable.blue_draw ,
        "blue_reverse" to  R.drawable.blue_reverse,
    );

    fun  LoadCardData():List<Card>{
        val ramdomCards : MutableList<Card> = mutableListOf();
        for(i in 1..6){
            val random =  listRes.entries.elementAt(Random.nextInt(listRes.size))
            val randomDrawable = random.value;
            val randomDrawableName = random.key;
            ramdomCards.add(Card(randomDrawable,randomDrawableName))
        }
        return ramdomCards;
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_playwithfriends_int)
        val roomid:String = intent.getStringExtra("roomID").toString()
        Toast.makeText(this,roomid, Toast.LENGTH_LONG).show();
        val recyclerv = findViewById<RecyclerView>(R.id.rv);
        val current_card = findViewById<ImageView>(R.id.current_card);

        //shows the username of the opponent
        val playing_against = findViewById<TextView>(R.id.playing_against);


        Firebase.firestore.collection("games").document(roomid).get().addOnSuccessListener { doc ->
            run {
                val cu = Firebase.auth.currentUser;
                if(cu!=null){
                    val user_id: String = cu.uid;
                    if (doc.exists() && doc != null) {
                        val player_one = doc.getField<String>("player_one")
                        val player_two = doc.getField<String>("player_two")
                        //im the one who made the game ,the opponent is player_two
                        if (user_id==player_one){
                            if (player_two != null) {
                                Firebase.firestore.collection("users").document(player_two).get().addOnSuccessListener { doc ->
                                    run {
                                        if (doc.exists() && doc != null) {
                                            val username = doc.getField<String>("username")
                                            if(username!=null){
                                                Log.d("PLAYER_TWO",username);
                                                playing_against.setText("You are playing against "+username);
                                            }else{
                                                Log.d("NULL","username player_two")
                                            }
                                        }
                                    }
                                }
                            }
                        //i joined the game so the opponent is player_one
                        }else{
                                if (player_one != null) {
                                    Firebase.firestore.collection("users").document(player_one).get().addOnSuccessListener { doc ->
                                        run {
                                            if (doc.exists() && doc != null) {
                                                val username = doc.getField<String>("username")
                                                if(username!=null){
                                                    Log.d("PLAYER_ONE",username);
                                                    playing_against.setText("You are playing against "+username);
                                                }else{
                                                    Log.d("NULL","username player_one")
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

        //on card click snapshot
        Firebase.firestore.collection("gameplay").document(roomid).addSnapshotListener{snapshot , e ->
            run {
                if(snapshot !=null){
                    val card_name =  snapshot.getField<String>("card_name");
                    val drawable = listRes.get(card_name);
                    if (drawable != null) {
                        current_card.setImageResource(drawable)
                    };
                }
            }
        }

        recyclerv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL ,false);
        recyclerv.adapter = ItemAdapter(this,LoadCardData(), roomid);
    }
}
//1262