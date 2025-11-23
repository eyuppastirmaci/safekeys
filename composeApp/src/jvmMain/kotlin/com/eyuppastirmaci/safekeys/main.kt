package com.eyuppastirmaci.safekeys

import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState

fun main() = application {
    val windowState = rememberWindowState(
        position = WindowPosition(Alignment.Center),
        width = 640.dp,
        height = 880.dp
    )
    Window(
        onCloseRequest = ::exitApplication,
        title = "SafeKeys",
        state = windowState
    ) {
        App()
    }
}