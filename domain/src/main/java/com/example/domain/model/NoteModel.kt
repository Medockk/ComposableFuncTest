package com.example.domain.model

data class NoteModel(

    val noteId: Int,
    val noteTitle: String,
    val noteDescription: String,
    var isRefactoring: Boolean,
)