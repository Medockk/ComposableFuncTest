package com.example.composablefunctest.Note

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.NetworkResult
import com.example.domain.usecase.Note.CreateNoteUseCase
import com.example.domain.usecase.Note.DeleteNoteUseCase
import com.example.domain.usecase.Note.GetNotesUseCase
import com.example.domain.usecase.Note.RefactorNoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val getNotesUseCase: GetNotesUseCase,
    private val createNoteUseCase: CreateNoteUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase,
    private val refactorNoteUseCase: RefactorNoteUseCase
) : ViewModel() {

    private val _state = mutableStateOf(NoteState())
    val state: State<NoteState> = _state

    init {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                getNotes()
            } catch (e: Exception) {
                _state.value = state.value.copy(exception = e.message.toString())
            }
        }
    }

    private suspend fun getNotes() {
        getNotesUseCase().collect {
            when (it) {
                is NetworkResult.Error<*> -> {
                    _state.value = state.value.copy(
                        showIndicator = false,
                        exception = it.message ?: "Unknown error"
                    )
                }

                is NetworkResult.Loading<*> -> {
                    _state.value = state.value.copy(
                        showIndicator = true,
                    )
                }

                is NetworkResult.Success<*> -> {
                    withContext(Dispatchers.Main) {
                        _state.value = state.value.copy(
                            notes = it.data ?: emptyList(),
                            showIndicator = false
                        )
                    }
                }
            }
        }


    }

    fun onEvent(event: NoteEvent) {
        when (event) {
            is NoteEvent.EnterDescription -> {
                _state.value = state.value.copy(
                    noteDescription = event.value
                )
            }

            is NoteEvent.EnterTitle -> {
                _state.value = state.value.copy(
                    noteTitle = event.value
                )
            }

            is NoteEvent.DeleteNote -> {
                viewModelScope.launch(Dispatchers.IO) {
                    try {
                        deleteNoteUseCase(event.noteModel)

                        withContext(Dispatchers.Main) {
                            _state.value = state.value.copy(
                                notes = _state.value.notes.toMutableList().apply {
                                    remove(event.noteModel)
                                }
                            )
                        }
                    } catch (e: Exception) {
                        _state.value = state.value.copy(exception = e.message.toString())
                    }
                }
            }

            is NoteEvent.RefactorNote -> {
                val index = _state.value.notes.indexOf(event.noteModel)
                _state.value = state.value.copy(
                    notes = _state.value.notes.toMutableList().apply {
                        set(
                            index,
                            event.noteModel.copy(
                                isRefactoring = !event.noteModel.isRefactoring
                            )
                        )
                    }
                )
            }

            is NoteEvent.CreateNote -> {
                viewModelScope.launch(Dispatchers.IO) {
                    try {
                        val note = createNoteUseCase(
                            _state.value.noteTitle.ifBlank { "Title" },
                            _state.value.noteDescription.ifBlank { "Description" }
                        )

                        _state.value = state.value.copy(
                            notes = _state.value.notes.toMutableList().apply {
                                add(note)
                            },
                            noteTitle = "",
                            noteDescription = ""
                        )
                    } catch (e: Exception) {
                        _state.value = state.value.copy(exception = e.message.toString())
                    }
                }
            }

            NoteEvent.ResetException -> {
                _state.value = state.value.copy(exception = "")
            }

            NoteEvent.ChangeShowDialogState -> {
                _state.value = state.value.copy(
                    showCreateDialog = !_state.value.showCreateDialog
                )
            }
        }
    }
}