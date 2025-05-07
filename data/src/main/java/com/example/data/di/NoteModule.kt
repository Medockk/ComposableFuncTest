package com.example.data.di

import android.content.Context
import com.example.data.data_source.local.dao.NoteDao
import com.example.data.data_source.local.database.NoteDatabase
import com.example.data.repository.NoteRepositoryImpl
import com.example.domain.repository.NoteRepository
import com.example.domain.usecase.Note.CreateNoteUseCase
import com.example.domain.usecase.Note.DeleteNoteUseCase
import com.example.domain.usecase.Note.GetNotesUseCase
import com.example.domain.usecase.Note.RefactorNoteUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NoteModule {

    @Provides
    @Singleton
    fun provideNoteDao(@ApplicationContext context: Context) : NoteDao {
        val database = NoteDatabase.createDatabase(context)
        return database.noteDao
    }

    @Provides
    @Singleton
    fun provideNoteRepository(noteDao: NoteDao) : NoteRepository =
        NoteRepositoryImpl(noteDao)

    @Provides
    @Singleton
    fun provideCreateNoteUseCase(noteRepository: NoteRepository) =
        CreateNoteUseCase(noteRepository)
    @Provides
    @Singleton
    fun provideDeleteNoteUseCase(noteRepository: NoteRepository) =
        DeleteNoteUseCase(noteRepository)
    @Provides
    @Singleton
    fun provideRefactorNoteUseCase(noteRepository: NoteRepository) =
        RefactorNoteUseCase(noteRepository)
    @Provides
    @Singleton
    fun provideGetNotesUseCase(noteRepository: NoteRepository) =
        GetNotesUseCase(noteRepository)
}