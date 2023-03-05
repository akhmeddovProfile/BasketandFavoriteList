package com.example.petsgoforsaleequipment.api.FavApi


import com.example.petsgoforsaleequipment.model.favmodel.DefaultResponse
import com.example.petsgoforsaleequipment.model.PetsProductImageModel
import com.example.petsgoforsaleequipment.model.favmodel.FavModel
import io.reactivex.Observable
import io.reactivex.rxjava3.disposables.Disposable
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT

interface FavApi {
    @PUT("add-to-favorites/GdxNtqnq3iZO4DMHuIRPLnap2C92/")
    fun sendToFav(@Body petsProductImageModel: FavModel): Call<DefaultResponse>
}
