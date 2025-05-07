package com.example.domain.usecase.Note

import com.example.domain.repository.NoteRepository

class RefactorNoteUseCase(
    private val noteRepository: NoteRepository
) {

    suspend operator fun invoke(id: Int, title: String, description: String) =
        noteRepository.refactorNote(id, title, description)
}