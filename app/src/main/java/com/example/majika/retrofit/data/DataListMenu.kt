package com.example.majika.retrofit.data

import com.google.gson.annotations.SerializedName

data class DataListMenu (
    @SerializedName("data") val data: List<DataMenu>,
    @SerializedName("size") val size: Int?
)