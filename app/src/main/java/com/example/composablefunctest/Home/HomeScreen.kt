@file:Suppress("UNCHECKED_CAST")
@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.composablefunctest.Home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.composablefunctest.R
import com.example.composablefunctest.Route
import com.example.composablefunctest.common.CustomAlertDialog
import com.example.composablefunctest.common.CustomTopAppBar
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {

    val state = viewModel.state.value

    val coroutineScope = rememberCoroutineScope()
    val modalDrawerState = rememberDrawerState(
        DrawerValue.Closed
    )
    val modalDrawerSheetContent = listOf(
        listOf(
            Icons.Default.AccountCircle,
            stringResource(R.string.get_image),
            { navController.navigate(Route.ChangeImage.route) }
        ),
        listOf(
            Icons.Default.AccountCircle,
            stringResource(R.string.get_image),
            { navController.navigate(Route.ChangeImage.route) }
        ),
        listOf(
            Icons.Default.AccountCircle,
            stringResource(R.string.get_image),
            { navController.navigate(Route.ChangeImage.route) }
        ),
    )

    if (state.exception.isNotEmpty()){
        CustomAlertDialog(state.exception) {
            viewModel.onEvent(HomeEvent.ResetException)
        }
    }


    ModalNavigationDrawer(
        drawerState = modalDrawerState,
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = MaterialTheme.colorScheme.primary
            ) {
                modalDrawerSheetContent.forEach {
                    Row(
                        modifier = Modifier
                            .padding(horizontal = 10.dp)
                            .fillMaxWidth()
                            .background(
                                MaterialTheme.colorScheme.secondary,
                                RoundedCornerShape(10.dp)
                            )
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = ripple()
                            ) {
                                coroutineScope.launch { modalDrawerState.close() }
                                (it[2] as () -> Unit).invoke()
                            },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Spacer(Modifier.width(5.dp))
                        Icon(
                            imageVector = it[0] as ImageVector,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primaryContainer,
                            modifier = Modifier
                                .padding(vertical = 10.dp)
                        )
                        Spacer(Modifier.width(5.dp))
                        Text(
                            text = it[1] as String,
                            color = MaterialTheme.colorScheme.primaryContainer
                        )
                    }

                    Spacer(Modifier.height(10.dp))
                    if (it != modalDrawerSheetContent.last()) {
                        Spacer(
                            Modifier
                                .padding(horizontal = 10.dp)
                                .height(1.dp)
                                .fillMaxWidth()
                                .background(MaterialTheme.colorScheme.secondary)
                        )
                        Spacer(Modifier.height(10.dp))
                    }
                }
            }
        },
        scrimColor = MaterialTheme.colorScheme.primaryContainer.copy(0.2f),
        modifier = Modifier
            .fillMaxHeight(),

        ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primary),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CustomTopAppBar(
                userImage = state.userImage
            ) {
                coroutineScope.launch {
                    if (modalDrawerState.isOpen) {
                        modalDrawerState.close()
                    } else {
                        modalDrawerState.open()
                    }
                }
            }

            Spacer(Modifier.height(30.dp))
        }
    }
}