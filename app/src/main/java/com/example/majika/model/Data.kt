package com.example.majika.model

data class Data (
    val data: ArrayList<Result>
) {
    data class Result (val id: Int, val name: String, val popular_food: String, val address: String, val contact_person: String, val phone_number: String, val longitude: String, val latitude:String)
}