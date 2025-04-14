package com.example.composablefunctest

sealed class Route(val route: String) {

    data object Splash: Route("Splash")
    data object Home: Route("Home")
    data object ChangeImage: Route("ChangeImage")
}