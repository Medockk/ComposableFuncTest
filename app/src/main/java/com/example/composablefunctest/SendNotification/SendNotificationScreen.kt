@file:Suppress("UNCHECKED_CAST")

package com.example.composablefunctest.SendNotification

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.composablefunctest.R
import com.example.composablefunctest.common.CustomButton
import com.example.composablefunctest.common.CustomTextField
import com.example.composablefunctest.common.CustomTopAppBar

@Composable
fun SendNotificationScreen(
    navController: NavController,
    viewModel: SendNotificationViewModel = hiltViewModel()
) {

    val state = viewModel.state.value
    val tfList = listOf(
        listOf(
            stringResource(R.string.title),
            state.notificationTitle,
            {it: String -> viewModel.onEvent(SendNotificationEvent.EnterTitle(it))}
        ),
        listOf(
            stringResource(R.string.description),
            state.notificationDescription,
            {it: String -> viewModel.onEvent(SendNotificationEvent.EnterDescription(it))}
        ),
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
    ) {
        CustomTopAppBar(
            title = stringResource(R.string.notifications),
            icon = null
        ) {
            navController.popBackStack()
        }
        Spacer(Modifier.height(30.dp))

        tfList.forEach {
            CustomTextField(
                value = it[1] as String,
                onValueChange = it[2] as (String) -> Unit,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                hintText = it[0] as String
            )
            Spacer(Modifier.height(15.dp))
        }

        Spacer(Modifier.weight(1f))
        CustomButton(
            text = stringResource(R.string.send_notification),
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(55.dp)
                .padding(horizontal = 20.dp)
        ) {
            viewModel.onEvent(SendNotificationEvent.SendNotificationClick)
        }
    }
}