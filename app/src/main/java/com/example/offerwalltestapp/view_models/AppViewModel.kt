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

class AppViewModel(
    private val appRepository: AppRepository
) : ViewModel() {

    val idArray = ArrayList<Int>()

    var currentData: MutableLiveData<AppClass> = MutableLiveData()

    var currentId = MutableLiveData(0)
    var currentIndex = MutableLiveData(0)

    var buttonNextEnabled = MutableLiveData(false)

    init {
        initApp()
    }

    private fun initApp() {
        viewModelScope.launch {
            val job = viewModelScope.launch {
                getAllIds()
            }
            job.join()
            getObject(idArray[0])
        }
    }

    private suspend fun getAllIds() {
        withContext(Dispatchers.IO) {
            val idList = appRepository.getAllIds()
            for (id in idList) {
                idArray.add(id)
            }
        }
        currentId.value = idArray[0]
    }

    fun getObject(id: Int) {
        buttonNextEnabled.value = false
        viewModelScope.launch {
            currentData.value = appRepository.getObject(id)
            buttonNextEnabled.value = true
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