package com.example.majika.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface KeranjangDAO {

    @Insert
    suspend fun insertKeranjang(keranjang: KeranjangModel)

    @Update
    suspend fun  updateKeranjang(keranjang: KeranjangModel)

    @Query("SELECT * FROM Keranjang")
    fun getAllKeranjang(): LiveData<List<KeranjangModel>>

    @Delete
    suspend fun deleteKeranjang(keranjang: KeranjangModel)
}