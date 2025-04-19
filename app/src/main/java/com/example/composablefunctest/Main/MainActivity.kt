package com.example.composablefunctest.Main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.composablefunctest.ChangeImage.ChangeImageScreen
import com.example.composablefunctest.ChangeName.ChangeNameScreen
import com.example.composablefunctest.Drag.DragScreen
import com.example.composablefunctest.Home.HomeScreen
import com.example.composablefunctest.RadioButton.RadioButtonScreen
import com.example.composablefunctest.Route
import com.example.composablefunctest.SendNotification.SendNotificationScreen
import com.example.composablefunctest.Splash.SplashScreen
import com.example.composablefunctest.Video.VideoScreen
import com.example.composablefunctest.ui.theme.ComposableFuncTestTheme
import com.example.domain.usecase.utils.NotificationKeys.ACTION_HOME
import com.example.domain.usecase.utils.NotificationKeys.ACTION_NAME
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val viewModel = hiltViewModel<MainActivityViewModel>()

            val navController = rememberNavController()

            ComposableFuncTestTheme(
                dynamicColor = false
            ) {
                window.statusBarColor = MaterialTheme.colorScheme.primaryContainer.toArgb()

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .padding(innerPadding)
                    ) {
                        NavHost(
                            navController = navController,
                            startDestination = when (intent.action) {
                                ACTION_HOME -> {
                                    Route.Home.route
                                }

                                ACTION_NAME -> {
                                    Route.ChangeName.route
                                }

                                else -> {
                                    Route.Splash.route
                                }
                            },
                            modifier = Modifier.weight(1f),
                            enterTransition = {
                                fadeIn(tween(700, easing = LinearOutSlowInEasing))
                            },
                            exitTransition = {
                                fadeOut(tween(700, easing = LinearOutSlowInEasing))
                            }
                        ) {
                            composable(Route.Splash.route) {
                                SplashScreen(navController)
                            }
                            composable(Route.Home.route) {
                                HomeScreen(navController)
                            }
                            composable(Route.ChangeImage.route) {
                                ChangeImageScreen(navController)
                            }
                            composable(Route.ChangeName.route) {
                                ChangeNameScreen(navController)
                            }
                            composable(Route.RadioButton.route) {
                                RadioButtonScreen(navController)
                            }
                            composable(Route.Drag.route) {
                                DragScreen(navController)
                            }
                            composable(Route.Video.route) {
                                VideoScreen(navController)
                            }
                            composable(Route.SendNotification.route) {
                                SendNotificationScreen(navController)
                            }
                        }
                        Spacer(Modifier.height(20.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ComposableFuncTestTheme {
        Greeting("Android")
    }
}