package com.example.majika.database

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.majika.adapter.MenuAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class KeranjangViewModel(application: Application): AndroidViewModel(application) {
    private val repository: KeranjangRepository
    private var readAll: LiveData<List<KeranjangModel>>

    init {
        val keranjangDB = RoomDataBase.getDatabase(application).keranjangDAO()
        repository = KeranjangRepository(keranjangDB)
        readAll = repository.getAllKeranjang()
    }
    fun addKeranjang(keranjang: KeranjangModel) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertKeranjang(keranjang)
        }
    }

    fun updateKeranjang(keranjang: KeranjangModel) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateKeranjang(keranjang)
        }
    }

    fun deleteKeranjang(keranjang: KeranjangModel) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteKeranjang(keranjang)
        }
    }

    fun getAllKeranjang(): LiveData<List<KeranjangModel>> {
        return readAll
    }

    fun getKeranjangById(id: Int): LiveData<KeranjangModel> {
        return repository.getKeranjangById(id)
    }

    fun getJumlahKeranjangById(id: Int): Int {
        return repository.getJumlahKeranjangById(id)
    }

    suspend fun getJumlahKeranjang(): Int {
        return repository.getJumlahKeranjang()
    }

    fun getAllKeranjangList(): List<KeranjangModel> {
        return repository.getAllKeranjangList()
    }

    fun updateJumlahKeranjang(id: Int, jumlah: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateJumlahKeranjang(id, jumlah)
        }
    }
}