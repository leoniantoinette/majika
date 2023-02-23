package com.example.majika.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Keranjang")
data class KeranjangModel (
    @PrimaryKey
    var id: Int,
    @ColumnInfo(name = "nama")
    var nama: String,
    @ColumnInfo(name = "deskripsi")
    var deskripsi: String,
    @ColumnInfo(name = "currency")
    var currency: String,
    @ColumnInfo(name = "harga")
    var harga: Double,
    @ColumnInfo(name = "terjual")
    var terjual: Int,
    @ColumnInfo(name = "tipe")
    var tipe: String,
    @ColumnInfo(name = "jumlah")
    var jumlah: Int


)