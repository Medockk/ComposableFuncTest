package com.example.data.repository

import com.example.data.data_source.local.dao.NoteDao
import com.example.data.model.NoteModelImpl
import com.example.domain.model.NetworkResult
import com.example.domain.model.NoteModel
import com.example.domain.repository.NoteRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class NoteRepositoryImpl(
    private val noteDao: NoteDao
) : NoteRepository {

    override suspend fun createNote(title: String, description: String): NoteModel {

        val note = NoteModelImpl(noteTitle = title, noteDescription = description)
        noteDao.createNote(note)

        return note.toNoteModel()
    }

    override suspend fun refactorNote(id: Int, title: String, description: String) {
        noteDao.updateNote(NoteModelImpl(id, title, description))
    }

    override suspend fun deleteNote(noteModel: NoteModel) {
        noteDao.deleteNote(
            NoteModelImpl(
                noteModel.noteId,
                noteModel.noteTitle,
                noteModel.noteDescription
            )
        )
    }

    override suspend fun getNotes() = flow {

        emit(NetworkResult.Loading())

        val notes = noteDao.getNotes().map { it.toNoteModel() }

        emit(NetworkResult.Success(notes))
    }.catch {
        emit(NetworkResult.Error(it.localizedMessage))
    }
}