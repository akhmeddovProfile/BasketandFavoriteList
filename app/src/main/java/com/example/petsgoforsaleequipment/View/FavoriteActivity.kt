package com.example.petsgoforsaleequipment.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.petsgoforsaleequipment.R
import com.example.petsgoforsaleequipment.databinding.ActivityFavoriteBinding
import com.example.petsgoforsaleequipment.adapter.FavoriteRecyclerAdapter
import com.example.petsgoforsaleequipment.adapter.PetsProductAdapter
import com.example.petsgoforsaleequipment.api.FavApi.FavGetApi

import com.example.petsgoforsaleequipment.model.favmodel.GetFavListModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FavoriteActivity : AppCompatActivity(),
    FavoriteRecyclerAdapter.DeleteListener {

    lateinit var binding: ActivityFavoriteBinding
    private lateinit var getFavListModel: MutableList<GetFavListModel>
    private lateinit var getListFavModelNew: MutableList<GetFavListModel>
    private lateinit var auth: FirebaseAuth
    private lateinit var fireStore: FirebaseFirestore
    private var BASE_URL = "https://us-central1-petsgo-9d7d6.cloudfunctions.net/app/api/"
    private lateinit var adapter: FavoriteRecyclerAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setContentView(R.layout.activity_favorite)
        getFavListModel = mutableListOf<GetFavListModel>()
        getListFavModelNew = mutableListOf<GetFavListModel>()

        getFavoriteId()
    }

    private fun getFavoriteId() {
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(FavGetApi::class.java)

        val retrofitData = retrofitBuilder.getFavList()
            retrofitData.enqueue(object :Callback<List<GetFavListModel>>{
                override fun onResponse(
                    call: Call<List<GetFavListModel>>,
                    response: Response<List<GetFavListModel>>
                ) {
                    TODO("Not yet implemented")
                }

                override fun onFailure(call: Call<List<GetFavListModel>>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            })

    }
}