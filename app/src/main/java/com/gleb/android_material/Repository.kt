package com.gleb.android_material

import androidx.lifecycle.LiveData

interface Repository {
    fun getNasaPOD(): LiveData<ResponsePOD>
    fun getNasaPODInternetAccess(date: String)
}