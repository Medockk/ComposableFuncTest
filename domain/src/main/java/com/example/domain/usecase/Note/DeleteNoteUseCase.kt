package com.example.domain.usecase.Note

import com.example.domain.model.NoteModel
import com.example.domain.repository.NoteRepository

class DeleteNoteUseCase(
    private val noteRepository: NoteRepository
) {

    suspend operator fun invoke(noteModel: NoteModel) =
        noteRepository.deleteNote(noteModel)
}