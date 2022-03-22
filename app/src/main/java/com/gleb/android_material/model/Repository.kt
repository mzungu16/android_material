package com.gleb.android_material.model

import androidx.lifecycle.LiveData
import com.gleb.android_material.database.NoteTableDAO

interface Repository {
    fun getNasaPOD(): LiveData<ResponsePOD>
    fun getNasaPODInternetAccess(date: String)
    fun getNote(): LiveData<List<Note>>
    fun getNoteAccess(dao: NoteTableDAO?)
    fun getAllFilmsFromDB(dao: NoteTableDAO?): List<Note>
    fun insertNoteToBD(dao: NoteTableDAO?, note: Note)
    fun deleteNoteFromDB(dao: NoteTableDAO?, note: Note)

//    fun deleteNoteFromBD(dao: NoteTableDAO, note: NoteTable)

}