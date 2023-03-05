package com.example.petsgoforsaleequipment

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.petsgoforsaleequipment.databinding.ActivityPetsProductListBinding
import com.example.petsgoforsaleequipment.databinding.ActivityPetsProductListDetailBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class PetsProductListDetail : AppCompatActivity() {
    private var position:Int = 0
    private lateinit var fireStore:FirebaseFirestore
    private lateinit var mIntent:Intent
    private lateinit var binding:ActivityPetsProductListDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPetsProductListDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setDetailedInformation()

    }



    @SuppressLint("CheckResult")
    private fun setDetailedInformation() {
/*        val petproductRef=fireStore.collection("products")

        petproductRef.get().addOnSuccessListener { result->
            val document = result.documents[position]
            //TODO assign the items to xml views
            val petproductTite = document.get("blogTitle").toString()
            val petproductprice = document.get("blogPrice").toString()
            val petproductImg = document.get("blogImg").toString()
        }*/
        try {
            Glide.with(applicationContext)
                .load(intent.getStringExtra("blogImg")).into(binding.productImage)
            binding.textView.text=intent.getStringExtra("blogTitle")
        }catch (e:Exception){
            print("Exception: "+e.message)
        }
        print(intent.getStringExtra("blogImg"))


    }
}