package com.example.majika.database

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.majika.model.KeranjangModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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

    fun getAllKeranjang(): LiveData<List<KeranjangModel>> {
        return readAll
    }

    suspend fun getJumlahKeranjang(): Int {
        return repository.getJumlahKeranjang()
    }

    fun updateJumlahKeranjang(id: Int, jumlah: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateJumlahKeranjang(id, jumlah)
        }
    }
}