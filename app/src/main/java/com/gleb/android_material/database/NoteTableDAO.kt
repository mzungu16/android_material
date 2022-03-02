package com.gleb.android_material.database

import androidx.room.*

@Dao
interface NoteTableDAO {
    @Query("SELECT * FROM dataBaseTable")
    fun getAllNotes(): List<NoteTable>

    @Update
    fun updateAll(notes:List<NoteTable>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNote(note: NoteTable)

    @Delete
    fun deleteNote(note: NoteTable)
}