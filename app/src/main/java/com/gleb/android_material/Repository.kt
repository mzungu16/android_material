package com.gleb.android_material

import androidx.lifecycle.LiveData
import com.gleb.android_material.database.NoteTable
import com.gleb.android_material.database.NoteTableDAO

interface Repository {
    fun getNasaPOD(): LiveData<ResponsePOD>
    fun getNasaPODInternetAccess(date: String)
    fun getNote():LiveData<List<NoteTable>>
    fun getNoteAccess(dao: NoteTableDAO)
    fun getAllFilmsFromDB(dao: NoteTableDAO): List<NoteTable>
//    fun insertNoteToBD(dao: NoteTableDAO, note: NoteTable)
//    fun deleteNoteFromBD(dao: NoteTableDAO, note: NoteTable)

}