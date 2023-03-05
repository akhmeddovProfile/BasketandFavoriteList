package com.example.petsgoforsaleequipment.api

import com.example.petsgoforsaleequipment.model.PetsProductImageModel
import retrofit2.Call
import retrofit2.http.GET

interface ShopListApi {
  @GET("get-petshop-list/")
  fun getList():Call<List<PetsProductImageModel>>
}
