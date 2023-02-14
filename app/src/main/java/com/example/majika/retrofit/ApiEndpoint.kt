package com.example.majika.retrofit

import com.example.majika.model.Data
import retrofit2.Call
import retrofit2.http.*

interface ApiEndpoint {
    @GET("branch")
    fun data(): Call<Data>
}