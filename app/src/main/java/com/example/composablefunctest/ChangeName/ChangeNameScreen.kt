package com.example.composablefunctest.ChangeName

import androidx.activity.compose.BackHandler
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.composablefunctest.R
import com.example.composablefunctest.Route
import com.example.composablefunctest.common.CustomAlertDialog
import com.example.composablefunctest.common.CustomButton
import com.example.composablefunctest.common.CustomTextField
import com.example.composablefunctest.common.CustomTopAppBar

@Composable
fun ChangeNameScreen(
    navController: NavController,
    viewModel: ChangeNameViewModel = hiltViewModel()
) {

    val state = viewModel.state.value

    BackHandler {
        if (state.isChangeSaved){
            navController.navigate(Route.Home.route){
                popUpTo(Route.ChangeName.route){
                    inclusive = true
                }
            }
        }else {
            viewModel.onEvent(ChangeNameEvent.SetException("Ignore this changes?"))
        }
    }

    if (state.exception.isNotEmpty() && !state.isChangeSaved){
        CustomAlertDialog(
            description = state.exception,
            confirmClick = {
                viewModel.onEvent(ChangeNameEvent.SetException())
                navController.navigate(Route.Home.route){
                    popUpTo(Route.ChangeName.route)
                }
            }
        ) {
            viewModel.onEvent(ChangeNameEvent.SetException())
        }
    }


    LaunchedEffect(!state.isSuccessfulSavingChanges) {
        if (state.isSuccessfulSavingChanges){
            navController.navigate(Route.Home.route){
                popUpTo(Route.ChangeName.route)
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
    ) {
        CustomTopAppBar(
            title = stringResource(R.string.change_your_name),
            userImage = state.userImage,
            icon = null
        ) {
            navController.popBackStack()
        }

        Spacer(Modifier.height(30.dp))

        CustomTextField(
            value = state.userName,
            onValueChange = {viewModel.onEvent(ChangeNameEvent.EnterUserName(it))},
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            hintText = stringResource(R.string.your_name)
        )

        Spacer(Modifier.weight(1f))

        CustomButton(
            text = stringResource(R.string.save_changes),
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(55.dp)
                .padding(horizontal = 20.dp)
        ) {
            viewModel.onEvent(ChangeNameEvent.SaveChanges)
        }
    }
}