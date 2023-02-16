package com.example.majika.retrofit.endpoint

import com.example.majika.retrofit.data.DataListCabang
import retrofit2.Response
import retrofit2.http.GET

interface CabangEndpoint {
    @GET("branch")
    suspend fun getBranch() : Response<DataListCabang>
}