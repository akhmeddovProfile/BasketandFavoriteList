package com.example.petsgoforsaleequipment.api.FavApi

import android.telecom.Call
import com.example.petsgoforsaleequipment.model.PetsProductImageModel
import com.example.petsgoforsaleequipment.model.favmodel.GetFavListModel
import retrofit2.http.GET

interface FavGetApi {
    @GET("get-petshop-fav-list/GdxNtqnq3iZO4DMHuIRPLnap2C92/")
    fun getFavList(): retrofit2.Call<List<GetFavListModel>>
}