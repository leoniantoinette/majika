package com.example.majika.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Keranjang")
class KeranjangModel {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    @ColumnInfo(name = "nama")
    var nama: String = ""
    @ColumnInfo(name = "harga")
    var harga: String = ""
    @ColumnInfo(name = "jumlah")
    var jumlah: String = ""

    constructor(nama: String, harga: String, jumlah: String) {
        this.nama = nama
        this.harga = harga
        this.jumlah = jumlah
    }
}