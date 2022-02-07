package com.gleb.android_material

import android.icu.text.SimpleDateFormat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import java.util.*

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