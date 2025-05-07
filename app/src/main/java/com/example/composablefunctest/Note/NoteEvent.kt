package com.example.composablefunctest.Note

import com.example.domain.model.NoteModel

sealed class NoteEvent {

    data class EnterTitle(val value: String) : NoteEvent()
    data class EnterDescription(val value: String) : NoteEvent()
    data class DeleteNote(val noteModel: NoteModel) : NoteEvent()
    data class RefactorNote(val noteModel: NoteModel) : NoteEvent()

    data object CreateNote : NoteEvent()
    data object ChangeShowDialogState : NoteEvent()
    data object ResetException : NoteEvent()
}