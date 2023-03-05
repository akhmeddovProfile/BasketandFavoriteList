package com.example.petsgoforsaleequipment.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.room.Room

import com.example.petsgoforsaleequipment.PetsProductList
import com.example.petsgoforsaleequipment.adapter.BasketProductAdapter
import com.example.petsgoforsaleequipment.adapter.FavoriteRecyclerAdapter
import com.example.petsgoforsaleequipment.databinding.ActivityBasketBinding
import com.example.petsgoforsaleequipment.model.PetsProductImageModel
import com.example.petsgoforsaleequipment.room.PetShopCard
import com.example.petsgoforsaleequipment.room.PetShopCardDao
import com.example.petsgoforsaleequipment.room.PetShopCardDatabase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class BasketActivity : AppCompatActivity(),BasketProductAdapter.DeleteListener {
    private lateinit var binding: ActivityBasketBinding
    /*private var cardProductImage: ImageView?=findViewById(R.id.cardprductimage)*/
    private lateinit var carddatabase:PetShopCardDatabase
    private lateinit var cardDao: PetShopCardDao
    var succesful=true
    private lateinit var adapter:BasketProductAdapter
    private val compositeDisposable = CompositeDisposable()
    private  var basketArrayList = ArrayList<PetShopCard>()
    private lateinit var db : PetShopCardDatabase
    private lateinit var dao : PetShopCardDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBasketBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        db = Room.databaseBuilder(applicationContext, PetShopCardDatabase::class.java, "PetShopCardTable")
            .fallbackToDestructiveMigration()
            //.allowMainThreadQueries()
            .build()
        dao = db.petShopCardDao()
        binding.basketList.layoutManager=GridLayoutManager(this@BasketActivity,1)

        getPetShopList()

        binding.seekbar.setOnSeekBarChangeListener(object :SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                if(succesful==true){
                    p0!!.visibility = View.GONE
                    binding.seekbarBg.visibility = View.GONE
                    binding.seekbarBg2.visibility = View.GONE
                    Toast.makeText(this@BasketActivity, "Sifaris Tesdiqlendi", Toast.LENGTH_SHORT).show()
                    finish()
                    val intent = Intent(this@BasketActivity, PetsProductList::class.java)
                    startActivity(intent)
                    finish()
                    /*
                    cardDao=carddatabase.cardDao()
                    GlobalScope.launch {
                        carddatabase.cardDao().deleteAllList()
                    }
                     */
                }
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
                if (p0!!.progress > 25) {
                    p0!!.progress = 0
                } else {
                }
            }

        })
    }

    private fun getPetShopList(){
        var compositeDisposableGet = CompositeDisposable()
        compositeDisposableGet.add(
            dao.getMydata()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponseGetList)
        )
    }

    private fun handleResponseGetList(list:List<PetShopCard>){
        basketArrayList.addAll(list)
        println(list.size)
        //println(list[0].name)
        adapter = BasketProductAdapter(list, this)
        binding.basketList.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    override fun onOrderItemDeleteClick(order: PetShopCard, index:Int){
        compositeDisposable.add(
            dao.deleteitem(order)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponseDelete)
        )
        basketArrayList.removeAt(index)
        adapter.notifyItemRemoved(index)
        adapter.notifyItemRangeChanged(index,basketArrayList.size)
    }
    private fun handleResponseDelete(){
        println("Product Deleted")
    }
}
