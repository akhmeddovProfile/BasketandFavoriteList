package com.example.petsgoforsaleequipment.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.petsgoforsaleequipment.R
import com.example.petsgoforsaleequipment.model.PetsProductImageModel
import com.example.petsgoforsaleequipment.room.PetShopCard

class BasketProductAdapter(val product:List<PetShopCard>,
                           private val deleteListener : BasketProductAdapter.DeleteListener
):RecyclerView.Adapter<BasketProductAdapter.ProductBasketView>() {

    interface DeleteListener {
        fun onOrderItemDeleteClick(order : PetShopCard, index : Int)
    }

    class ProductBasketView(itemView: View):RecyclerView.ViewHolder(itemView){
        val producttitle: TextView =itemView.findViewById(R.id.cardproductname)
        val productImage: ImageView =itemView.findViewById(R.id.cardprductimage)
        val productPrice: TextView =itemView.findViewById(R.id.cardproductprice)
        val deleteItem: ImageView =itemView.findViewById(R.id.deleteitem)!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductBasketView {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view=layoutInflater.inflate(R.layout.card_item,parent,false)
        return BasketProductAdapter.ProductBasketView(view)
    }

    override fun onBindViewHolder(holder: ProductBasketView, position: Int) {
        holder.producttitle.text="Title: "+ product.get(position).name

        Glide.with(holder.itemView.context)
            .load(product.get(position).image)
            .into(holder.productImage)

        holder.productPrice.text="Price: "+ product.get(position).price
        holder.deleteItem.setOnClickListener {
            deleteListener.onOrderItemDeleteClick(product.get(position),position)

        }
    }

    override fun getItemCount(): Int {
        return product.size
    }
}