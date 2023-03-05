package com.example.petsgoforsaleequipment.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.petsgoforsaleequipment.R
import com.example.petsgoforsaleequipment.model.PetsProductImageModel
import com.example.petsgoforsaleequipment.model.favmodel.GetFavListModel
import com.example.petsgoforsaleequipment.room.PetShopCard

class FavoriteRecyclerAdapter(val context:Context,val favorites:List<GetFavListModel>,val product:List<PetsProductImageModel>/*, private val listener : Listener, private val deleteListener : DeleteListener*/)
    :RecyclerView.Adapter<FavoriteRecyclerAdapter.PostHolder>(){
/*    internal var db:CardDatabase?=null*/



    class PostHolder(view: View) : RecyclerView.ViewHolder(view){
        val producttitle: TextView =itemView.findViewById(R.id.cardproductname)
        val cardproductImage: ImageView =itemView.findViewById(R.id.cardprductimage)
        val productPrice: TextView =itemView.findViewById(R.id.cardproductprice)

    }
    interface DeleteListener {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.card_item, parent, false)
        return PostHolder(view)
    }

    override fun onBindViewHolder(holder: PostHolder, position: Int) {
        Log.d("MyTagHere", "onBindViewHolder: ${product[position].id}")
        Glide.with(context)
            .load(Uri.parse(product[position].image)).into(holder.cardproductImage)
       /* Glide.with(context)
            .load(cardlist.get(position).image)
            .into(holder.cardproductImage)*/
        holder.producttitle.text=product.get(position).name
        holder.productPrice.text=product.get(position).price
/*        holder.addToFav.setOnClickListener {
            listener.onFavItemClick(cardlist.get(position),position)
        }*/
    }

    override fun getItemCount(): Int {
        return product.size
    }

}