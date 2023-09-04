package com.example.testcat.data.network

import com.example.testcat.data.response.Result
import retrofit2.Response
import retrofit2.http.GET

interface EndPoint {
    @GET("gallery/search/?q=cats")
    suspend fun getImageCats(): Response<Result>

}