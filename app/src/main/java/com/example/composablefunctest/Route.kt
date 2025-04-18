package com.example.composablefunctest

sealed class Route(val route: String) {

    data object Splash: Route("Splash")
    data object Home: Route("Home")
    data object ChangeImage: Route("ChangeImage")
    data object ChangeName: Route("ChangeName")
    data object RadioButton: Route("RadioButton")
    data object Drag: Route("Drag")
    data object Video: Route("Video")
    data object SendNotification: Route("SendNotification")
}