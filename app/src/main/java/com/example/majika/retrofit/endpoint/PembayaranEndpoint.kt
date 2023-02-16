package com.example.majika.retrofit.endpoint

import com.example.majika.retrofit.data.DataPembayaran
import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Path

interface PembayaranEndpoint {
    @POST("payment/{code}")
    suspend fun postPayment(@Path("code") code:String) : Response<DataPembayaran>
}