package com.gleb.android_material

import android.provider.Settings.System.getString
import android.util.Log
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object InternetLoader {
    private val client = OkHttpClient.Builder()
        .callTimeout(2000, TimeUnit.MILLISECONDS)
        .connectTimeout(2000, TimeUnit.MILLISECONDS)
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()
    private val retrofitObject: RetrofitInt = Retrofit.Builder()
        .baseUrl("https://api.nasa.gov/")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        .build()
        .create(RetrofitInt::class.java)

    fun getNasaPOD(date: String, onCompleteListener: Listener<ResponsePOD>) {
        retrofitObject.getPictureOfTheDay(BuildConfig.API_KEY, date)
            .enqueue(object : Callback<ResponsePOD?> {
                override fun onResponse(
                    call: Call<ResponsePOD?>,
                    response: Response<ResponsePOD?>
                ) {
                    response.body()?.let {
                        onCompleteListener.on(it)
                    }
                }

                override fun onFailure(call: Call<ResponsePOD?>, t: Throwable) {
                    Log.d("TAG", t.stackTraceToString())
                }
            })
    }

    interface Listener<T> {
        fun on(arg: T)
    }
}