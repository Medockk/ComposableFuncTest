package com.example.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.domain.model.NoteModel

@Entity
data class NoteModelImpl(
    @PrimaryKey(true) val noteId: Int = 0,
    @ColumnInfo(defaultValue = "") val noteTitle: String,
    @ColumnInfo(defaultValue = "") val noteDescription: String,
    var isRefactoring: Boolean = false
) {
    fun toNoteModel() =
        NoteModel(noteId, noteTitle, noteDescription, isRefactoring)
}
