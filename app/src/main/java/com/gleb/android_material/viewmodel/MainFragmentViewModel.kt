package com.gleb.android_material.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.gleb.android_material.model.Repository
import com.gleb.android_material.model.RepositoryImpl
import com.gleb.android_material.model.ResponsePOD

class MainFragmentViewModel : ViewModel() {
    private val liveDataToObserve: MutableLiveData<ResponsePOD> = MutableLiveData()
    private val repo: Repository = RepositoryImpl()

    //transfer viewLifecycleOwner to repository
    fun getNasaPODLiveData(): LiveData<ResponsePOD> =
        Transformations.switchMap(liveDataToObserve) {
            repo.getNasaPOD()
        }

    fun setNasaPODLiveDataValueMethod() {
        liveDataToObserve.value = getNasaPODLiveData().value
    }

    fun getNasaPODInternetAccess(date:String) {
        repo.getNasaPODInternetAccess(date)
    }
}