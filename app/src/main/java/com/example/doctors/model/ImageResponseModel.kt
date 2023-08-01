package com.example.doctors.model

import com.google.gson.annotations.SerializedName

data class ImageResponseModel(
    @SerializedName("url")
    val url: String
)