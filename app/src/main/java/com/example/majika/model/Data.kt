package com.example.majika.model

data class Data (
    val data: ArrayList<Result>
) {
    data class Result (val id: Int, val title: String, val name: String)
}