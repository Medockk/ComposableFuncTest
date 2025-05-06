package com.example.composablefunctest.SegmentButton

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MultiChoiceSegmentedButtonRow
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.composablefunctest.R
import com.example.composablefunctest.common.CustomTopAppBar
import com.example.composablefunctest.ui.theme.primaryLight

@Composable
fun SegmentButtonScreen(
    navController: NavController,
    viewModel: SegmentButtonViewModel = hiltViewModel()
) {

    val state = viewModel.state.value
    val singleSegmentButtonList = listOf("First", "Second", "Third", "Fourth", "Fifth")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
    ) {
        CustomTopAppBar(
            title = stringResource(R.string.segment_button),
            icon = Icons.AutoMirrored.Default.KeyboardArrowLeft,
        ) {
            navController.popBackStack()
        }

        Spacer(Modifier.height(30.dp))

        SingleChoiceSegmentedButtonRow(
            modifier = Modifier
                .padding(horizontal = 20.dp)
        ) {
            singleSegmentButtonList.forEachIndexed { index, value ->
                SegmentedButton(
                    selected = index == state.singleButtonIndex,
                    onClick = {
                        viewModel.onEvent(SegmentButtonEvent.SetSingleSegmentButtonIndex(index))
                    },
                    shape = SegmentedButtonDefaults.itemShape(
                        index = index,
                        count = singleSegmentButtonList.size,
                        baseShape = RoundedCornerShape(10.dp)
                    ),
                    modifier = Modifier
                        .fillMaxWidth(),
                    colors = SegmentedButtonDefaults.colors(
                        activeContainerColor = MaterialTheme.colorScheme.primaryContainer,
                        inactiveContainerColor = MaterialTheme.colorScheme.secondary
                    )
                ) {
                    Text(
                        text = value,
                        color = if (index == state.singleButtonIndex) {
                            primaryLight
                        } else {
                            MaterialTheme.colorScheme.onPrimary
                        }
                    )
                }
            }
        }

        Spacer(Modifier.height(30.dp))

        MultiChoiceSegmentedButtonRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        ) {
            state.multiSegmentState.forEachIndexed { index, pair ->
                SegmentedButton(
                    checked = pair,
                    onCheckedChange = {
                        viewModel.onEvent(
                            SegmentButtonEvent.SetMultiSegmentButton(
                                index, !pair
                            )
                        )
                    },
                    shape = SegmentedButtonDefaults.itemShape(
                        index = index,
                        count = state.multiSegmentState.size,
                        baseShape = RoundedCornerShape(10.dp)
                    ),
                    colors = SegmentedButtonDefaults.colors(
                        activeContainerColor = MaterialTheme.colorScheme.primaryContainer,
                        inactiveContainerColor = MaterialTheme.colorScheme.secondary
                    )
                ) {
                    Text(
                        text = state.multiSegmentTitle[index],
                        color = if (pair) primaryLight
                        else MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }
    }
}