package com.example.doctors.service

import com.example.doctors.model.DoctorsModelResponse
import retrofit2.Response
import retrofit2.http.GET

interface DoctorsAPI {
    @GET("/android/doctors.json")
    suspend fun getData() : Response<DoctorsModelResponse>
}
