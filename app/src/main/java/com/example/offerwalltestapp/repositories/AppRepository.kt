package com.example.offerwalltestapp.repositories

import com.example.offerwalltestapp.network.AppService
import com.example.offerwalltestapp.network.classes.AppClass

class AppRepository(
    private val appService: AppService
) {
    suspend fun getAllIds() : List<Int> {
        return appService.getAllIds().data.map{id -> id.id ?: 0}
    }

    suspend fun getObject(id: Int) : AppClass  {
        return appService.getObject(id.toString())
    }
}