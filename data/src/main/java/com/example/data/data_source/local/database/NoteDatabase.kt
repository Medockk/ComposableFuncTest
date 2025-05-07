package com.example.data.data_source.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.data.data_source.local.dao.NoteDao
import com.example.data.model.NoteModelImpl

@Database([NoteModelImpl::class], version = 1)
abstract class NoteDatabase : RoomDatabase() {

    abstract val noteDao: NoteDao

    companion object{
        fun createDatabase(context: Context) : NoteDatabase {
            return Room.databaseBuilder(context, NoteDatabase::class.java, "note.db")
                .build()
        }
    }
}