package com.example.data.data_source.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.data.model.NoteModelImpl

@Dao
interface NoteDao {

    @Insert
    fun createNote(noteModelImpl: NoteModelImpl)

    @Update
    fun updateNote(noteModelImpl: NoteModelImpl)

    @Delete
    fun deleteNote(noteModelImpl: NoteModelImpl)

    @Query("select * from NoteModelImpl")
    fun getNotes() : List<NoteModelImpl>
}