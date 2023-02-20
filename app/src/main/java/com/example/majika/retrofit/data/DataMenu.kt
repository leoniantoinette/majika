package com.example.majika.retrofit.data

import com.google.gson.annotations.SerializedName

data class DataMenu (
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String,
    @SerializedName("currency") val currency: String,
    @SerializedName("price") val price: Double,
    @SerializedName("sold") val sold: Int,
    @SerializedName("type") val type: String,
    @SerializedName("count") var count: Int,
    )