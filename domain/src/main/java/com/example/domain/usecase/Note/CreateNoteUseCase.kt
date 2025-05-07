package com.example.domain.usecase.Note

import com.example.domain.repository.NoteRepository

class CreateNoteUseCase(
    private val noteRepository: NoteRepository
) {

    suspend operator fun invoke(title: String, description: String) =
        noteRepository.createNote(title, description)
}