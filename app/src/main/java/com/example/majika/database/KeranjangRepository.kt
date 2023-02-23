package com.example.majika.database

import androidx.lifecycle.LiveData
import com.example.majika.model.KeranjangModel

class KeranjangRepository(private val keranjangDao: KeranjangDAO) {

    suspend fun insertKeranjang(keranjang: KeranjangModel) {
        keranjangDao.insertKeranjang(keranjang)
    }

    fun getAllKeranjang(): LiveData<List<KeranjangModel>> {
        return keranjangDao.getAllKeranjang()
    }

    suspend fun getJumlahKeranjang(): Int {
        return keranjangDao.getJumlahKeranjang()
    }

    suspend fun updateJumlahKeranjang(id: Int, jumlah: Int) {
        keranjangDao.updateJumlahKeranjang(id, jumlah)
    }
}