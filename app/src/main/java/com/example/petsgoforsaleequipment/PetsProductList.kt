package com.example.petsgoforsaleequipment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.petsgoforsaleequipment.View.BasketActivity
import com.example.petsgoforsaleequipment.View.FavoriteActivity
import com.example.petsgoforsaleequipment.adapter.BasketProductAdapter
import com.example.petsgoforsaleequipment.adapter.OnClickBasketAdapter
import com.example.petsgoforsaleequipment.databinding.ActivityPetsProductListBinding
import com.example.petsgoforsaleequipment.adapter.PetsProductAdapter
import com.example.petsgoforsaleequipment.api.FavApi.FavApi
import com.example.petsgoforsaleequipment.api.FavApi.FavouriteRetrofitClient

import com.example.petsgoforsaleequipment.api.ShopListApi
import com.example.petsgoforsaleequipment.model.PetsProductImageModel
import com.example.petsgoforsaleequipment.model.favmodel.DefaultResponse
import com.example.petsgoforsaleequipment.model.favmodel.FavModel
import com.example.petsgoforsaleequipment.room.PetShopCard
import com.example.petsgoforsaleequipment.room.PetShopCardDao
import com.example.petsgoforsaleequipment.room.PetShopCardDatabase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.petsgo.android.services.RetrofitService2
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class PetsProductList : AppCompatActivity(),
    PetsProductAdapter.Listener,
    PetsProductAdapter.ListenerFav,
    OnClickBasketAdapter {
    lateinit var binding: ActivityPetsProductListBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var fireStore: FirebaseFirestore
    val BASE_URL="https://us-central1-petsgo-9d7d6.cloudfunctions.net/app/api/"
    lateinit var favModel: FavModel
    val BASE_URL_FAV="https://us-central1-petsgo-9d7d6.cloudfunctions.net/app/api/"
    private lateinit var petRecyclerAdapter: PetsProductAdapter
    private lateinit var petproductImgFromFB: ArrayList<String>
    private lateinit var petproductTitleFromFB: ArrayList<String>
    private lateinit var petproductPriceFromFB: ArrayList<String>
    private val idList = ArrayList<String>()
    private lateinit var dao: PetShopCardDao
    private lateinit var db: PetShopCardDatabase

    private lateinit var petFromFB: ArrayList<PetsProductImageModel>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPetsProductListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        fireStore = Firebase.firestore
        petproductImgFromFB = ArrayList<String>()
        petproductTitleFromFB = ArrayList<String>()
        petproductPriceFromFB = ArrayList<String>()
        petFromFB=ArrayList<PetsProductImageModel>()
        binding.equipmetlist.setHasFixedSize(true)
        binding.equipmetlist.apply {
            layoutManager=LinearLayoutManager(this@PetsProductList)
            binding.equipmetlist.layoutManager=layoutManager
        }

        db = Room.databaseBuilder(applicationContext, PetShopCardDatabase::class.java, "PetShopCardTable")
            .fallbackToDestructiveMigration()
            //.allowMainThreadQueries()
            .build()
        dao = db.petShopCardDao()

        getPetShopList()

        binding.goToFavList.setOnClickListener{
            startActivity(Intent(this@PetsProductList,FavoriteActivity::class.java).apply {
            })
        }
        binding.goToBasket.setOnClickListener {
            startActivity(Intent(this@PetsProductList,BasketActivity::class.java).apply {
            })
            //finish()
        }
    }



    private fun handleResponseFav(response:DefaultResponse){
        println(response.message)
    }


    private fun getPetShopList(){
        val retrofitBuilder=Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(ShopListApi::class.java)

        val retrofitData=retrofitBuilder.getList()

        retrofitData.enqueue(object : Callback<List<PetsProductImageModel>?> {
            override fun onResponse(
                call: Call<List<PetsProductImageModel>?>,
                response: Response<List<PetsProductImageModel>?>
            ) {
                val responsebody=response.body()!!

                petRecyclerAdapter= PetsProductAdapter(baseContext,responsebody,this@PetsProductList,this@PetsProductList,this@PetsProductList)
                petRecyclerAdapter.notifyDataSetChanged()
                binding.equipmetlist.adapter=petRecyclerAdapter
            }

            override fun onFailure(call: Call<List<PetsProductImageModel>?>, t: Throwable) {
                Log.d("PetsProductList","onFailure: ",t.cause)
            }
        } )
    }

    override fun onFavItemCLick(fav: PetsProductImageModel) {
        println("Clicked")
     val product = fav.id
        favModel = FavModel(product)

        FavouriteRetrofitClient.instance.sendToFav(favModel).enqueue(object : Callback<DefaultResponse>{
            override fun onResponse(
                call: Call<DefaultResponse>,
                response: Response<DefaultResponse>
            ) {
               /* val message=response.body()?.message*/

                println(response.message() + "Success")
            }

            override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                Toast.makeText(applicationContext,t.message, Toast.LENGTH_LONG).show()
                println("Failed")
            }

        })

/*        FavouriteRetrofitClient.instance.sendToFav(favModel)
            .enqueue(object : Callback<DefaultResponse>{
                override fun onResponse(
                    call: Call<DefaultResponse>,
                    response: Response<DefaultResponse>
                ) {
                    Toast.makeText(applicationContext,response.body()?.message,Toast.LENGTH_SHORT).show()
                    println(response.message() + "Success")
                }
                override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                    val myString : String = t.message.toString()
                    Toast.makeText(applicationContext,myString, Toast.LENGTH_LONG).show()
                }

            })*/
/*        val compositeDisposableFav = CompositeDisposable()
        val request = FavModel(fav.id)
        val retrofit = RetrofitService2(BASE_URL_FAV).retrofit.create(FavApi::class.java)
        compositeDisposableFav.add(retrofit.sendToFav(request))
            .(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::handleResponseFav)*/
    }

    override fun onPetItemClick(pet: PetsProductImageModel,position:Int) {
        /*println(pet)*/
        val intent = Intent(applicationContext, PetsProductListDetail::class.java)
        intent.putExtra("blogTitle", pet.name)
        intent.putExtra("blogPrice", pet.price)
        intent.putExtra("blogImg", pet.image)
        intent.putExtra("blogCatagory",pet.catagory)
        intent.putExtra("blogDetails",pet.description)
        intent.putExtra("position", position)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    override fun onClicAddCard(card: PetShopCard) {
        try {
            var compositeDisposableGet = CompositeDisposable()
            compositeDisposableGet.add(
                dao.addMydata(card)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        println("SUCCESS")
                    }, {
                        println(it.localizedMessage)
                    })
            )
        }catch (e:Exception){
            println("ERROR ${e.message}")
        }
    }
}
