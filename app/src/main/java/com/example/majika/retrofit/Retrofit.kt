package com.example.majika.retrofit

import com.example.majika.retrofit.data.DataListMenu
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Retrofit {

    private const val baseUrl = "http://localhost/v1/"
    private val gson = GsonBuilder()
        .registerTypeAdapter(DataListMenu::class.java, DataMenuDeserializer())
        .create()


    fun getInstance(): Retrofit {
        return Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }
}