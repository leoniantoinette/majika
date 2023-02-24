package com.example.majika.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.majika.model.KeranjangModel

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

    @Query("SELECT COUNT(*) FROM Keranjang")
    suspend fun getJumlahKeranjang(): Int

    @Query("UPDATE Keranjang SET jumlah = :jumlah WHERE id = :id")
    suspend fun updateJumlahKeranjang(id: Int, jumlah: Int)

    @Query("UPDATE Keranjang SET jumlah = 0")
    suspend fun resetJumlahKeranjang()

    @Delete
    suspend fun deleteKeranjang(keranjang: KeranjangModel)
}