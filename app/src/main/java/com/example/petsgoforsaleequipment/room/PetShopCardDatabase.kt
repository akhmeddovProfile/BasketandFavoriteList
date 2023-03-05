package com.example.petsgoforsaleequipment.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(PetShopCard::class), version = 1)
abstract class PetShopCardDatabase : RoomDatabase() {
    abstract fun petShopCardDao() : PetShopCardDao
}
