package com.example.petsgoforsaleequipment.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class PetShopCard(
    @ColumnInfo(name = "my_id")
    val myid:Int,
    @ColumnInfo(name = "image")
    var image:String,
    @ColumnInfo(name = "price")
    var price:String,
    @ColumnInfo(name = "name")
    val name:String,
    @ColumnInfo(name = "description")
    val description:String
){
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id:Int = 0
}
