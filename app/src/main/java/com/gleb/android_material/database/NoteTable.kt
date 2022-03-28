package com.gleb.android_material.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dataBaseTable")
data class NoteTable(
    @PrimaryKey val id: Int?,
    @ColumnInfo(name = "note_header") val headerNote: String?,
    @ColumnInfo(name = "note_description") val descriptionNote: String?,
)