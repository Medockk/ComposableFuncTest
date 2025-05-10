package com.example.composablefunctest.Note

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.composablefunctest.Note.components.CreateDialog
import com.example.composablefunctest.Note.components.Note
import com.example.composablefunctest.R
import com.example.composablefunctest.common.CustomAlertDialog
import com.example.composablefunctest.common.CustomFloatingActionButton
import com.example.composablefunctest.common.CustomIndicator
import com.example.composablefunctest.common.CustomTopAppBar

@Composable
fun NoteScreen(
    navController: NavController,
    viewModel: NoteViewModel = hiltViewModel()
) {

    val state = viewModel.state.value

    if (state.exception.isNotEmpty()) {
        CustomAlertDialog(state.exception) {
            viewModel.onEvent(NoteEvent.ResetException)
        }
    }

    if (state.showCreateDialog) {
        CreateDialog(
            title = state.noteTitle,
            description = state.noteDescription,
            onTitleChange = {
                viewModel.onEvent(NoteEvent.EnterNewNoteTitle(it))
            },
            onDescriptionChange = {
                viewModel.onEvent(NoteEvent.EnterNewNoteDescription(it))
            },
            onDismissRequest = {
                viewModel.onEvent(NoteEvent.ChangeShowDialogState)
            },
        ) {
            viewModel.onEvent(NoteEvent.ChangeShowDialogState)
            viewModel.onEvent(NoteEvent.CreateNote)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
    ) {
        CustomTopAppBar(
            title = stringResource(R.string.note),
            icon = Icons.AutoMirrored.Default.KeyboardArrowLeft,
        ) {
            navController.popBackStack()
        }

        Spacer(Modifier.height(30.dp))

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            items(state.notes) {
                Note(
                    title = it.noteTitle,
                    description = it.noteDescription,
                    modifier = Modifier
                        .fillParentMaxWidth(),
                    refactorClick = {
                        viewModel.onEvent(NoteEvent.SetIsRefactoringState(it))
                    },
                    deleteClick = {
                        viewModel.onEvent(NoteEvent.DeleteNote(it))
                    },
                    onTitleChange = { newValue ->
                        viewModel.onEvent(NoteEvent.ChangeNoteTitle(it, newValue))
                    },
                    onDescriptionChange = { newValue ->
                        viewModel.onEvent(NoteEvent.ChangeNoteDescription(it, newValue))
                    },
                    isChanging = it.isRefactoring
                )

                Spacer(Modifier.height(10.dp))
            }
        }
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        contentAlignment = Alignment.BottomEnd
    ) {
        CustomFloatingActionButton {
            viewModel.onEvent(NoteEvent.ChangeShowDialogState)
        }
    }

    CustomIndicator(state.showIndicator)
}