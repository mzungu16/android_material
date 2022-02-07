package com.gleb.android_material

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitInt {
    @GET("/planetary/apod")
    fun getPictureOfTheDay(
        @Query("api_key") apiKey: String
    ): Call<ResponsePOD>
}