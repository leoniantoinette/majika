package com.example.majika.database

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ViewModel(application: Application): AndroidViewModel(application) {
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
}