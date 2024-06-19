package com.example.offerwalltestapp.view_models

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.offerwalltestapp.data.appContainer
import com.example.offerwalltestapp.network.classes.AppClass
import com.example.offerwalltestapp.repositories.AppRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AppViewModel(
    private val appRepository: AppRepository
) : ViewModel() {

    val idArray = ArrayList<Int>()

    var currentData: MutableLiveData<AppClass> = MutableLiveData()

    init {
        getAllIds()
    }

    fun getAllIds() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val idList = appRepository.getAllIds()
                for (id in idList) {
                    idArray.add(id)
                    Log.d("myLogs", "$id")
                }
            }
            getObject(idArray[0])
        }
    }

    fun getObject(id: Int) {
        viewModelScope.launch {
            currentData.value = appRepository.getObject(id)
            Log.d("myLogs", currentData.value.toString())
        }
    }

    companion object{
        val Factory = viewModelFactory {
            initializer {
                val appRepository = appContainer.appRepository
                AppViewModel(appRepository)
            }
        }
    }
}