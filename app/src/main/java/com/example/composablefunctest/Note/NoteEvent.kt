package com.example.composablefunctest.Note

import com.example.domain.model.NoteModel

sealed class NoteEvent {

    data class EnterNewNoteTitle(val value: String) : NoteEvent()
    data class EnterNewNoteDescription(val value: String) : NoteEvent()
    data class DeleteNote(val noteModel: NoteModel) : NoteEvent()
    data class SetIsRefactoringState(val noteModel: NoteModel) : NoteEvent()

    data class ChangeNoteTitle(
        val note: NoteModel,
        val newValue: String
    ) : NoteEvent()
    data class ChangeNoteDescription(
        val note: NoteModel,
        val newValue: String
    ) : NoteEvent()

    data object CreateNote : NoteEvent()
    data object ChangeShowDialogState : NoteEvent()
    data object ResetException : NoteEvent()
}