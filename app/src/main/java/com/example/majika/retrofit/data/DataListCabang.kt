package com.example.majika.retrofit.data

import com.google.gson.annotations.SerializedName

data class DataListCabang (
    @SerializedName("data") val data: List<DataCabang>,
    @SerializedName("size") val size: Int
)