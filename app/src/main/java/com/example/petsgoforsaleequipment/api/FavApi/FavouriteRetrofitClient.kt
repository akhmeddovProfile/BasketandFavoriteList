package com.example.petsgoforsaleequipment.api.FavApi

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object FavouriteRetrofitClient {


   private val AUTH = "Basic " + android.util.Base64.encodeToString("KerimM:kerim12345".toByteArray(), android.util.Base64.NO_WRAP)

    private  val BASE_URL = "https://us-central1-petsgo-9d7d6.cloudfunctions.net/app/api/"


    private val  okHttpClient = OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .addInterceptor { chain->
            val original = chain.request()
            val requestBuilder = original.newBuilder()
                .addHeader("Authorization", AUTH)
                .method(original.method,original.body)
            val request = requestBuilder.build()
            chain.proceed(request)
        }.build()

    val instance: FavApi by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
        retrofit.create(FavApi::class.java)
    }


}
