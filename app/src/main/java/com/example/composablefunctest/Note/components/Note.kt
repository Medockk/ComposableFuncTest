package com.example.composablefunctest.Note.components

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.composablefunctest.R
import com.example.composablefunctest.common.CustomTextField

@Composable
fun Note(
    title: String,
    description: String,
    modifier: Modifier = Modifier,
    refactorIcon: ImageVector = Icons.Default.Create,
    deleteIcon: ImageVector = Icons.Default.Delete,
    isChanging: Boolean = false,
    onTitleChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    refactorClick: () -> Unit,
    deleteClick: () -> Unit,
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(
            MaterialTheme.colorScheme.secondary
        )
    ) {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(15.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = title,
                        color = MaterialTheme.colorScheme.primary,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(Modifier.height(5.dp))
                    Text(
                        text = description,
                        color = MaterialTheme.colorScheme.primary,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                IconButton(
                    onClick = refactorClick,
                    colors = IconButtonDefaults.iconButtonColors(Color.Transparent)
                ) {
                    Icon(
                        imageVector = refactorIcon,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
                IconButton(
                    onClick = deleteClick,
                    colors = IconButtonDefaults.iconButtonColors(
                        Color.Transparent
                    )
                ) {
                    Icon(
                        imageVector = deleteIcon,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }

            androidx.compose.animation.AnimatedVisibility(
                visible = isChanging,
                enter = fadeIn(tween()),
                exit = fadeOut(tween())
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                ) {
                    Text(
                        text = stringResource(R.string.title),
                        color = MaterialTheme.colorScheme.onPrimary,
                    )

                    Spacer(Modifier.height(5.dp))
                    CustomTextField(
                        value = title,
                        onValueChange = onTitleChange,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                    Spacer(Modifier.height(15.dp))

                    Text(
                        text = stringResource(R.string.description),
                        color = MaterialTheme.colorScheme.onPrimary
                    )

                    Spacer(Modifier.height(5.dp))
                    CustomTextField(
                        value = description,
                        onValueChange = onDescriptionChange,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }
            }
        }
    }
}