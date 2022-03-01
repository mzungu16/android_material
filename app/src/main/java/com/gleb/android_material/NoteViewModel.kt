package com.gleb.android_material

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.gleb.android_material.database.NoteTable
import com.gleb.android_material.database.NoteTableDAO

class NoteViewModel : ViewModel() {
    private val liveDataToObserve: MutableLiveData<List<NoteTable>> = MutableLiveData()
    private val repo: Repository = RepositoryImpl()

    fun getNoteLiveData(): LiveData<List<NoteTable>> =
        Transformations.switchMap(liveDataToObserve) {
            repo.getNote()
        }

    fun setNoteLiveDataValueMethod() {
        liveDataToObserve.value = getNoteLiveData().value
    }

    fun getNoteAccess(dao: NoteTableDAO) {
        repo.getNoteAccess(dao)
    }
}