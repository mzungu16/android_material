package com.gleb.android_material.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface NoteTableDAO {
    @Query("SELECT * FROM dataBaseTable")
    fun getAllNotes(): List<NoteTable>

    @Insert
    fun insertNote(note: NoteTable)

    @Delete
    fun deleteNote(note: NoteTable)
}