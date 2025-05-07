package com.example.domain.repository

import com.example.domain.model.NetworkResult
import com.example.domain.model.NoteModel
import kotlinx.coroutines.flow.Flow

interface NoteRepository {

    suspend fun createNote(title: String, description: String) : NoteModel
    suspend fun refactorNote(id: Int, title: String, description: String)
    suspend fun deleteNote(noteModel: NoteModel)

    suspend fun getNotes() : Flow<NetworkResult<List<NoteModel>>>
}