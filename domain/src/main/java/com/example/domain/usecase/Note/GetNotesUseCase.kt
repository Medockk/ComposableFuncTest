package com.example.domain.usecase.Note

import com.example.domain.repository.NoteRepository

class GetNotesUseCase(
    private val noteRepository: NoteRepository
) {

    suspend operator fun invoke() =
        noteRepository.getNotes()
}