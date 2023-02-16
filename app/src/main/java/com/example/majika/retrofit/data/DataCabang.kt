package com.example.majika.retrofit.data

import com.google.gson.annotations.SerializedName

data class DataCabang (
    @SerializedName("name") val name: String,
    @SerializedName("popular_food") val popular_food: String,
    @SerializedName("address") val address: String,
    @SerializedName("contact_person") val contact_person: String,
    @SerializedName("phone_number") val phone_number: String,
    @SerializedName("longitude") val longitude: Double,
    @SerializedName("latitude") val latitude: Double
)