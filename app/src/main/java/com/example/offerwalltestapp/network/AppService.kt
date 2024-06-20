package com.example.offerwalltestapp.network

import com.example.offerwalltestapp.network.classes.AppClass
import com.example.offerwalltestapp.network.classes.DataArray
import retrofit2.http.GET
import retrofit2.http.Path

interface AppService {

    @GET("entities/getAllIds")
    suspend fun getAllIds() : DataArray

    @GET("object/{id}")
    suspend fun getObject(
        @Path("id") id: String
    ) : AppClass
}