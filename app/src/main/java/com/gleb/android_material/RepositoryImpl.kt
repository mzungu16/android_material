package com.gleb.android_material

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class RepositoryImpl : Repository {
    private val liveDataNasaImage: MutableLiveData<ResponsePOD> = MutableLiveData()

    override fun getNasaPOD(): LiveData<ResponsePOD> = liveDataNasaImage

    override fun getNasaPODInternetAccess(date: String) {
        InternetLoader.getNasaPOD(date, object : InternetLoader.Listener<ResponsePOD> {
            override fun on(arg: ResponsePOD) {
                liveDataNasaImage.value = arg
            }
        })
    }
}