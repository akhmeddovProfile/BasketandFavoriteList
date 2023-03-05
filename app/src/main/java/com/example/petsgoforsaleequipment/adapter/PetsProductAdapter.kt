package com.example.petsgoforsaleequipment.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.petsgoforsaleequipment.R
import com.example.petsgoforsaleequipment.model.PetsProductImageModel
import com.example.petsgoforsaleequipment.model.favmodel.FavModel
import com.example.petsgoforsaleequipment.room.PetShopCard

class PetsProductAdapter(val context:Context,
                         val product:List<PetsProductImageModel>,
                         private val favlistener:ListenerFav,
                         private val listener: Listener,
                         var onClickBasketAdapter: OnClickBasketAdapter
): RecyclerView.Adapter<PetsProductAdapter.ProductViewHolder>(){
    private var userToken="GdxnNtqnq3iZO4DMHulRPLnap2C92"
    lateinit var petsProductImageModel: PetsProductImageModel

    interface Listener{
        fun onPetItemClick(pet : PetsProductImageModel, position: Int)
    }

    interface ListenerFav{
        fun onFavItemCLick(fav:PetsProductImageModel)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view=layoutInflater.inflate(R.layout.pets_item,parent,false)
        return ProductViewHolder(view)
    }

    class ProductViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val producttitle:TextView=itemView.findViewById(R.id.productname)
        val productImage:ImageView=itemView.findViewById(R.id.prductimage)
        val productPrice:TextView=itemView.findViewById(R.id.productprice)
        val addToCard: Button =itemView.findViewById(R.id.addToCard)
        val fvButton: Button =itemView.findViewById(R.id.fvBtn)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        /*val pets=product[position]*/
        var intent=Intent()

        holder.producttitle.text="Title: "+ product.get(position).name

        Glide.with(context)
            .load(product.get(position).image)
            .into(holder.productImage)

        holder.productPrice.text="Price: "+ product.get(position).price

        holder.itemView.setOnClickListener {
            val intent= Intent()
            listener.onPetItemClick(product.get(position),position)
        }

        holder.fvButton.setOnClickListener {
            try {
                favlistener.onFavItemCLick(product.get(position))
            }
            catch (e:java.lang.Exception){
                println("Error"+e.message)
                Toast.makeText(context,"Error: ${e.localizedMessage}",Toast.LENGTH_LONG).show()
            }

        }

        holder.addToCard.setOnClickListener{
            try {
                val card=PetShopCard(0,product.get(position).image,product.get(position).price,product.get(position).name,product.get(position).description)
                onClickBasketAdapter.onClicAddCard(card)
            }
            catch (e:Exception){
                println("Error: "+e.message)
                Toast.makeText(context,"Error: ${e.localizedMessage}",Toast.LENGTH_LONG).show()
            }
        }

/*        holder.fvButton.setOnClickListener {
            *//*
            val id = product.get(position).id
            petsProductImageModel = PetsProductImageModel(id,"null","null","null","null","null")
            FavouriteRetrofitClient.instance.sendToFav(petsProductImageModel)
                .enqueue(object : Callback<DefaultResponse> {
                    override fun onResponse(
                        call: Call<DefaultResponse>,
                        response: Response<DefaultResponse>
                    ) {
                        println("successfully: "+id)
                        Toast.makeText(context,response.body()?.message, Toast.LENGTH_SHORT).show()

                    }
                    override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                        Toast.makeText(context,t.message, Toast.LENGTH_SHORT).show()
                        println("failed")
                    }
                })
             *//*
        }*/
    }

    override fun getItemCount(): Int {
        return product.size
    }
}
