package com.example.composablefunctest.Note.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.composablefunctest.R
import com.example.composablefunctest.common.CustomButton
import com.example.composablefunctest.common.CustomTextField

@Composable
fun CreateDialog(
    title: String,
    description: String,
    onTitleChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    createClick: () -> Unit,
) {

    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(dismissOnClickOutside = false)
    ) {
        Column(
            modifier = modifier
                .background(
                    MaterialTheme.colorScheme.primary,
                    RoundedCornerShape(10.dp)
                )
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.onPrimary.copy(0.5f),
                    shape = RoundedCornerShape(10.dp)
                )
                .padding(15.dp)
        ) {
            Text(
                text = stringResource(R.string.title),
                color = MaterialTheme.colorScheme.onPrimary,
            )

            Spacer(Modifier.height(5.dp))
            CustomTextField(
                value = title,
                onValueChange = onTitleChange,
            )
            Spacer(Modifier.height(15.dp))

            Text(
                text = stringResource(R.string.description),
                color = MaterialTheme.colorScheme.onPrimary
            )

            Spacer(Modifier.height(5.dp))
            CustomTextField(
                value = description,
                onValueChange = onDescriptionChange
            )
            Spacer(Modifier.height(25.dp))

            CustomButton(
                text = stringResource(R.string.create),
                modifier = Modifier
                    .align(Alignment.End)
            ) {
                createClick()
            }
        }
    }
}