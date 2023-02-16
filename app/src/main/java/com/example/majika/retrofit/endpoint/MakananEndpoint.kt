package com.example.majika.retrofit.endpoint

import com.example.majika.retrofit.data.DataListMenu
import retrofit2.Response
import retrofit2.http.GET

interface MakananEndpoint {
    @GET("menu/food")
    suspend fun getMenuFood() : Response<DataListMenu>
}