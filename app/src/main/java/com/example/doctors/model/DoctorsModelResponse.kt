package com.example.doctors.model

import com.google.gson.annotations.SerializedName

data class DoctorsModelResponse (
    @SerializedName("doctors")
    val data : ArrayList<DoctorsModel>
    )