package com.example.doctors.view

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.doctors.model.DoctorsModel
import com.example.doctors.service.DoctorsAPI
import com.example.doctors.util.Constants.BASE_URL
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeViewModel (application: Application) : AndroidViewModel(application) {

    val doctorsLiveList = MutableLiveData<ArrayList<DoctorsModel>>()
    val isListEmpty = MutableLiveData<Boolean>()


    private var job : Job? = null

    fun loadData(){
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DoctorsAPI::class.java)

        job = CoroutineScope(Dispatchers.IO).launch {
            val response = retrofit.getData()
            withContext(Dispatchers.Main){
                if (response.isSuccessful){
                    response.body()?.let {
                        doctorsLiveList.value = it.data
                    }
                }else{
                    println("Veri cekilemedi")
                }
            }
        }
    }
    fun filterListForName (text : String,doctorList: ArrayList<DoctorsModel>): ArrayList<DoctorsModel>{
        if (doctorList.size > 0 && text.isNotEmpty()){
            return doctorList.filter { text.lowercase() in it.name.lowercase() } as ArrayList<DoctorsModel>
        }else {
            return doctorList
        }
    }

    fun filterByGender(gender : String,doctorList: ArrayList<DoctorsModel>): ArrayList<DoctorsModel>{
        if (doctorList.size > 0 && gender.isNotEmpty()){
            return doctorList.filter { gender == it.gender } as ArrayList<DoctorsModel>
        }else {
            return doctorList
        }
    }

    fun filterByGenderAndListForName(text: String, gender: String, female : Boolean, male : Boolean, doctorList: ArrayList<DoctorsModel>) : ArrayList<DoctorsModel>{

        if (doctorList.size > 0){
            if (female){
                if(text.isNotEmpty()){
                    val list = doctorList.filter { gender == it.gender && text.lowercase() in it.name.lowercase() } as ArrayList<DoctorsModel>
                    isListEmpty.value = list.size <= 0
                    return list
                }else{
                    return doctorList.filter { gender == it.gender} as ArrayList<DoctorsModel>
                }
            }else if (male){
                if(text.isNotEmpty()){
                    val list = doctorList.filter { gender == it.gender && text.lowercase() in it.name.lowercase() } as ArrayList<DoctorsModel>
                    isListEmpty.value = list.size <= 0
                    return list
                }else{
                    return doctorList.filter { gender == it.gender} as ArrayList<DoctorsModel>
                }
            }else{
                if(text.isNotEmpty()){
                    val list = doctorList.filter {text.lowercase() in it.name.lowercase()} as ArrayList<DoctorsModel>
                    isListEmpty.value = list.size <= 0
                    return list
                }else{
                    return doctorList
                }
            }
        }else {
            return doctorList
        }
    }

    fun getUserStatus(): String {
        return "free"
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}