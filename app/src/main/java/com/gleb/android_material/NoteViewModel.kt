package com.gleb.android_material

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.gleb.android_material.database.NoteTableDAO

class NoteViewModel : ViewModel() {
    private val liveDataToObserve: MutableLiveData<List<Note>> = MutableLiveData()
    private val repo: Repository = RepositoryImpl()

    fun getNoteLiveData(): LiveData<List<Note>> =
        Transformations.switchMap(liveDataToObserve) {
            repo.getNote()
        }

    fun setNoteLiveDataValueMethod() {
        liveDataToObserve.value = getNoteLiveData().value
    }

    fun getNoteAccess(dao: NoteTableDAO?) {
        repo.getNoteAccess(dao)
    }

    fun insertNote(dao: NoteTableDAO?, note: Note) {
        repo.insertNoteToBD(dao, note)
    }

    fun deleteNote(dao: NoteTableDAO?,note: Note){
        repo.deleteNoteFromDB(dao,note)
    }
}