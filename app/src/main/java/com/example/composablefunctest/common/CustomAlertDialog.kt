package com.example.composablefunctest.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composablefunctest.R

@Composable
fun CustomAlertDialog(
    description: String,
    modifier: Modifier = Modifier,
    title: String = stringResource(R.string.error),
    onClick: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onClick,
        confirmButton = {},
        modifier = modifier,
        tonalElevation = 10.dp,
        icon = {
            Box(
                Modifier
                    .size(55.dp)
                    .background(MaterialTheme.colorScheme.errorContainer, CircleShape),
                contentAlignment = Alignment.Center
            ){
                Icon(
                    imageVector = Icons.Default.Warning,
                    contentDescription = title,
                    tint = Color.White,
                    modifier = Modifier
                        .padding(5.dp)
                        .matchParentSize()
                )
            }
        },
        title = {
            Text(
                text = title,
                color = MaterialTheme.colorScheme.onPrimary,
                fontSize = 24.sp
            )
        },
        containerColor = MaterialTheme.colorScheme.primary,
        text = {
            Text(
                text = description,
                color = MaterialTheme.colorScheme.onPrimary,
                fontSize = 12.sp
            )
        }
    )
}

@Composable
fun CustomAlertDialog(
    description: String,
    modifier: Modifier = Modifier,
    title: String = stringResource(R.string.some_changes_not_saved),
    confirmButtonText: String = stringResource(R.string.ignore_this_changes),
    icon: ImageVector = Icons.Default.Warning,
    confirmClick: () -> Unit,
    onClick: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onClick,
        confirmButton = {
            CustomButton(
                text = confirmButtonText,
            ) {
                confirmClick()
            }
        },
        modifier = modifier,
        tonalElevation = 10.dp,
        icon = {
            Box(
                Modifier
                    .size(55.dp)
                    .background(MaterialTheme.colorScheme.errorContainer, CircleShape),
                contentAlignment = Alignment.Center
            ){
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier
                        .padding(5.dp)
                        .matchParentSize()
                )
            }
        },
        title = {
            Text(
                text = title,
                color = MaterialTheme.colorScheme.onPrimary,
                fontSize = 24.sp
            )
        },
        containerColor = MaterialTheme.colorScheme.primary,
        text = {
            Text(
                text = description,
                color = MaterialTheme.colorScheme.onPrimary,
                fontSize = 12.sp
            )
        }
    )
}