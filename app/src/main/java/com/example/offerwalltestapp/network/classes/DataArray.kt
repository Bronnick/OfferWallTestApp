package com.example.offerwalltestapp.network.classes

import com.google.gson.annotations.SerializedName

data class DataArray (

    @SerializedName("data" ) var data : ArrayList<Id> = arrayListOf()

)