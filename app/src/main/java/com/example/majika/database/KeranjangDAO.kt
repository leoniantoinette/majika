package com.example.majika.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface KeranjangDAO {

    @Insert
    suspend fun insertKeranjang(keranjang: KeranjangModel)

    @Update
    suspend fun updateKeranjang(keranjang: KeranjangModel)

    @Query("SELECT * FROM Keranjang")
    fun getAllKeranjang(): LiveData<List<KeranjangModel>>

    @Query("SELECT * FROM Keranjang WHERE id = :id")
    fun getKeranjangById(id: Int): LiveData<KeranjangModel>

    @Query("UPDATE Keranjang SET jumlah = :jumlah WHERE id = :id")
    suspend fun updateJumlahKeranjang(id: Int, jumlah: Int)

    @Delete
    suspend fun deleteKeranjang(keranjang: KeranjangModel)
}