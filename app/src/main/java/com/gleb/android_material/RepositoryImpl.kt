package com.gleb.android_material

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gleb.android_material.database.NoteTable
import com.gleb.android_material.database.NoteTableDAO

class RepositoryImpl : Repository {
    private val liveDataNasaImage: MutableLiveData<ResponsePOD> = MutableLiveData()
    private val liveDataNote: MutableLiveData<List<NoteTable>> = MutableLiveData()

    override fun getNasaPOD(): LiveData<ResponsePOD> = liveDataNasaImage

    override fun getNasaPODInternetAccess(date: String) {
        InternetLoader.getNasaPOD(date, object : InternetLoader.Listener<ResponsePOD> {
            override fun on(arg: ResponsePOD) {
                liveDataNasaImage.value = arg
            }
        })
    }

    override fun getNote(): LiveData<List<NoteTable>> = liveDataNote

    override fun getNoteAccess(dao: NoteTableDAO) {
        liveDataNote.value = getAllFilmsFromDB(dao)
    }


    override fun getAllFilmsFromDB(dao: NoteTableDAO): List<NoteTable> {
        return dao.getAllNotes()
    }


}