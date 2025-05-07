package com.example.composablefunctest.Note

import com.example.domain.model.NoteModel

data class NoteState(
    val noteTitle: String = "",
    val noteDescription: String = "",

    val notes: List<NoteModel> = emptyList(),
    val showIndicator: Boolean = false,
    val exception: String = "",
    val showCreateDialog: Boolean = false,
    val showRefactorDialog: Boolean = false,
)
