package com.gleb.android_material.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gleb.android_material.database.NoteTable
import com.gleb.android_material.database.NoteTableDAO

class RepositoryImpl : Repository {
    private val liveDataNasaImage: MutableLiveData<ResponsePOD> = MutableLiveData()
    private val liveDataNote: MutableLiveData<List<Note>> = MutableLiveData()

    override fun getNasaPOD(): LiveData<ResponsePOD> = liveDataNasaImage

    override fun getNasaPODInternetAccess(date: String) {
        InternetLoader.getNasaPOD(date, object : InternetLoader.Listener<ResponsePOD> {
            override fun on(arg: ResponsePOD) {
                liveDataNasaImage.value = arg
            }
        })
    }

    override fun getNote(): LiveData<List<Note>> = liveDataNote

    override fun getNoteAccess(dao: NoteTableDAO?) {
        liveDataNote.value = getAllFilmsFromDB(dao)
    }

    override fun getAllFilmsFromDB(dao: NoteTableDAO?): List<Note> {
        if (dao != null) {
            return dao.getAllNotes().map {
                Note(
                    id = it.id,
                    header = it.headerNote,
                    description = it.descriptionNote
                )
            }
        }
        return emptyList()
    }

    override fun insertNoteToBD(dao: NoteTableDAO?, note: Note) {
        dao?.insertNote(
            NoteTable(
                note.id,
                note.header,
                note.description
            )
        )
    }

    override fun deleteNoteFromDB(dao: NoteTableDAO?, note: Note) {
        dao?.deleteNote(
            NoteTable(
                id = note.id,
                headerNote = note.header,
                descriptionNote = note.description
            )
        )
    }
}