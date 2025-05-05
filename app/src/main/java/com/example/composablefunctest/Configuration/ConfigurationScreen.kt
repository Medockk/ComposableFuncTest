@file:Suppress("UNCHECKED_CAST")
@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.composablefunctest.Configuration

import android.content.pm.ActivityInfo
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.composablefunctest.Configuration.components.CustomLanguageCard
import com.example.composablefunctest.Configuration.components.CustomSlider
import com.example.composablefunctest.Configuration.components.CustomThemeCard
import com.example.composablefunctest.R
import com.example.composablefunctest.common.CustomTopAppBar

@Composable
fun ConfigurationScreen(
    navController: NavController,
    viewModel: ConfigurationViewModel = hiltViewModel(),
    changeTheme: (Boolean?) -> Unit
) {

    val state = viewModel.state.value
    val activity = LocalActivity.current

    val themeContent = listOf(
        listOf(
            stringResource(R.string.system_theme),
            Icons.Default.Settings,
            {
                viewModel.onEvent(ConfigurationEvent.SetTheme(null))
            },
            state.appTheme == null,
        ),
        listOf(
            stringResource(R.string.dark_theme),
            ImageVector.vectorResource(R.drawable.dark_theme_icon),
            {
                viewModel.onEvent(ConfigurationEvent.SetTheme(true))
            },
            state.appTheme != null && state.appTheme
        ),
        listOf(
            stringResource(R.string.light_theme),
            ImageVector.vectorResource(R.drawable.light_theme_icon),
            {
                viewModel.onEvent(ConfigurationEvent.SetTheme(false))
            },
            state.appTheme != null && !state.appTheme
        ),
    )

    val languageList = listOf(
        listOf(
            stringResource(R.string.english),
            {
                viewModel.onEvent(ConfigurationEvent.SetLanguage(LanguageList.English))
            }
        ),
        listOf(
            stringResource(R.string.russian),
            {
                viewModel.onEvent(ConfigurationEvent.SetLanguage(LanguageList.Russian))
            }
        ),
    )

    val orientationList = listOf(
        listOf(
            stringResource(R.string.horizontal),
            {
                viewModel.onEvent(
                    ConfigurationEvent.ChangeOrientation(
                        activity, ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                    )
                )
            },
        ),
        listOf(
            stringResource(R.string.vertical),
            {
                viewModel.onEvent(
                    ConfigurationEvent.ChangeOrientation(
                        activity, ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
                    )
                )
            }
        )
    )

    LaunchedEffect(Unit) {
        viewModel.onEvent(ConfigurationEvent.SetChangeThemeClick(changeTheme))
    }

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
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            themeContent.forEach {
                CustomThemeCard(
                    title = it[0] as String,
                    icon = it[1] as ImageVector,
                    onClick = {
                        (it[2] as () -> Unit).invoke()
                    },
                    modifier = Modifier
                        .weight(1f),
                    isSelected = it[3] as Boolean,
                )
                if (it != themeContent.last()) {
                    Spacer(Modifier.width(20.dp))
                }
            }
        }

        Spacer(Modifier.height(30.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            languageList.forEach {
                CustomLanguageCard(
                    title = it[0] as String,
                    onClick = it[1] as () -> Unit,
                    isSelected = (it[0] as String) == state.currentLanguage,
                    modifier = Modifier
                        .weight(1f)
                )

                if (it != languageList.last()) {
                    Spacer(Modifier.width(20.dp))
                }
            }
        }

        Spacer(Modifier.height(30.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            orientationList.forEach {
                CustomLanguageCard(
                    title = it[0] as String,
                    onClick = it[1] as () -> Unit,
                    modifier = Modifier
                        .weight(1f)
                )

                if (it != orientationList.last()) {
                    Spacer(Modifier.width(20.dp))
                }
            }
        }

        Spacer(Modifier.height(30.dp))

//        Slider(
//            value = state.brightness,
//            onValueChange = {
//                viewModel.onEvent(ConfigurationEvent.ChangeBrightness(it))
//            },
//            thumb = {
//                Box(Modifier.size(24.dp).background(MaterialTheme.colorScheme.onPrimary, CircleShape))
//                SliderDefaults.Thumb(
//                    interactionSource = remember { MutableInteractionSource() },
//                    thumbSize = DpSize(24.dp, 24.dp),
//                    colors = SliderDefaults.colors(
//                        MaterialTheme.colorScheme.onPrimary
//                    ),
//                    modifier = Modifier
//                        .size(24.dp)
//                        .background(MaterialTheme.colorScheme.onPrimary, CircleShape)
//                )
//            },
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(horizontal = 20.dp),
//            colors = SliderDefaults.colors(
//                thumbColor = MaterialTheme.colorScheme.onPrimary,
//                activeTrackColor = MaterialTheme.colorScheme.primaryContainer,
//                inactiveTrackColor = MaterialTheme.colorScheme.onPrimary.copy(0.5f)
//            )
//        )

        CustomSlider(
            value = state.brightness,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        ) {
            viewModel.onEvent(ConfigurationEvent.ChangeBrightness(it))
        }
    }
}