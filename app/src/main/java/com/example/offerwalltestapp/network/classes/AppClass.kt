package com.example.offerwalltestapp.network.classes

import com.google.gson.annotations.SerializedName

data class ExampleJson2KtKotlin (

    @SerializedName("id"      ) var id      : Int?    = null,
    @SerializedName("type"    ) var type    : String? = null,
    @SerializedName("message" ) var message : String? = null,
    @SerializedName("url"  ) var url  : String? = null

)