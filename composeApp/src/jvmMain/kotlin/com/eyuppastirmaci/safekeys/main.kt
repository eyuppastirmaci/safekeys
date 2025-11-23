package com.eyuppastirmaci.safekeys

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.eyuppastirmaci.safekeys.password.PasswordGenerator

fun main() = application {
    val passwordGenerator = PasswordGenerator()

    Window(
        onCloseRequest = ::exitApplication,
        title = "SafeKeys",
    ) {

        // Inject dependencies into UI
        App(passwordGenerator)
    }
}
