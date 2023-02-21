package com.example.majika.database

import androidx.lifecycle.LiveData

class KeranjangRepository(private val keranjangDao: KeranjangDAO) {

    suspend fun insertKeranjang(keranjang: KeranjangModel) {
        keranjangDao.insertKeranjang(keranjang)
    }

    suspend fun updateKeranjang(keranjang: KeranjangModel) {
        keranjangDao.updateKeranjang(keranjang)
    }

    suspend fun deleteKeranjang(keranjang: KeranjangModel) {
        keranjangDao.deleteKeranjang(keranjang)
    }

    fun getAllKeranjang(): LiveData<List<KeranjangModel>> {
        return keranjangDao.getAllKeranjang()
    }
}