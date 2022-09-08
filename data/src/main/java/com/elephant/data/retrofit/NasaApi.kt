package com.elephant.data.retrofit

import com.elephant.data.model.NasaServerResponse
import retrofit2.Response
import retrofit2.http.*

interface NasaApi {
    @GET("search")
    suspend fun getQuerySearch(
        @Query("q") search: String,
        @Query("media_type") type: String,
        @Query("page") page: Int
    ): Response<NasaServerResponse>

    @GET
    suspend fun getPicture(@Url link: String): Response<List<String>>
}