package com.example.composablefunctest.Configuration

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.composablefunctest.R
import com.example.composablefunctest.common.CustomTopAppBar

@Composable
fun ConfigurationScreen(
    navController: NavController
) {

    val themeContent = listOf(
        listOf(
            stringResource(R.string.light_theme),
            {}
        ),
        listOf(
            stringResource(R.string.dark_theme),
            {}
        ),
        listOf(
            stringResource(R.string.light_theme),
            {}
        ),
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
    ) {
        CustomTopAppBar(
            title = stringResource(R.string.configuration),
            icon = Icons.AutoMirrored.Default.KeyboardArrowLeft
        ) {
            navController.popBackStack()
        }

        Spacer(Modifier.height(30.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

        }
    }
}