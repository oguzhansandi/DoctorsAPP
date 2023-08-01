package com.example.doctors.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class DoctorsModel (
    @SerializedName("full_name")
    val name : String,
    @SerializedName("user_status")
    val status : String,
    @SerializedName("gender")
    val gender : String,
    @SerializedName("image")
    val image : ImageResponseModel
        ) : Serializable