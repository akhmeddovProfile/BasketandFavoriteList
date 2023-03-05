package com.example.petsgoforsaleequipment.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.petsgoforsaleequipment.model.PetsProductImageModel
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface PetShopCardDao {
    @Query("SELECT * FROM PetShopCard")
    fun getMydata() : Flowable<List<PetShopCard>>

    @Insert
    fun addMydata(card: PetShopCard) : Completable

    @Delete
    fun deleteitem(order : PetShopCard) : Completable

    //@Query("DELETE FROM PetShopCard")
    //fun deleteAllList()
}
