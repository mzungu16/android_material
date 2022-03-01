package com.gleb.android_material.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [NoteTable::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteTableDAO
}