package com.example.offerwalltestapp.data

import com.example.offerwalltestapp.network.AppService
import com.example.offerwalltestapp.repositories.AppRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

val appContainer by lazy {
    AppContainer()
}

class AppContainer {

    private val baseUrl = "http://demo3005513.mockable.io/api/v1/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val retrofitService by lazy{
        retrofit.create<AppService>()
    }

    val appRepository by lazy{
        AppRepository(retrofitService)
    }
}