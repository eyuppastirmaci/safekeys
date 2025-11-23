package com.eyuppastirmaci.safekeys

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.runtime.rememberCoroutineScope
import java.awt.datatransfer.StringSelection
import androidx.compose.ui.platform.ClipEntry
import androidx.compose.ui.platform.LocalClipboard
import kotlinx.coroutines.launch
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.unit.dp
import com.eyuppastirmaci.safekeys.config.PasswordConfig
import com.eyuppastirmaci.safekeys.password.PasswordGenerator
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App(
    passwordGenerator: PasswordGenerator = PasswordGenerator()
) {
    MaterialTheme {
        val clipboardManager = LocalClipboard.current
        val scope = rememberCoroutineScope()

        var password by remember { mutableStateOf<String?>(null) }
        var errorMessage by remember { mutableStateOf<String?>(null) }
        var lengthText by remember { mutableStateOf("16") }

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {

                Text(
                    text = "SafeKeys",
                    style = MaterialTheme.typography.headlineMedium
                )

                Spacer(modifier = Modifier.height(16.dp))

                TextField(
                    value = lengthText,
                    onValueChange = { newValue ->
                        // Only allow digits
                        lengthText = newValue.filter { it.isDigit() }
                    },
                    label = {
                        Text(
                            "Password length (${PasswordConfig.MIN_PASSWORD_LENGTH}–${PasswordConfig.MAX_PASSWORD_LENGTH})"
                        )
                    },
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(onClick = {
                    val length = lengthText.toIntOrNull()
                    if (length == null) {
                        errorMessage = "Please enter a number"
                        password = null
                    } else if (length !in PasswordConfig.MIN_PASSWORD_LENGTH..PasswordConfig.MAX_PASSWORD_LENGTH) {
                        errorMessage =
                            "Please enter a length between ${PasswordConfig.MIN_PASSWORD_LENGTH} and ${PasswordConfig.MAX_PASSWORD_LENGTH}"
                        password = null
                    } else {
                        errorMessage = null
                        password = passwordGenerator.generate(length)
                    }
                }) {
                    Text("Generate password")
                }

                Spacer(modifier = Modifier.height(16.dp))

                when {
                    password != null -> {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center,
                                modifier = Modifier
                                    .pointerHoverIcon(PointerIcon.Hand)
                                    .clickable {
                                        scope.launch {
                                            clipboardManager.setClipEntry(
                                                ClipEntry(
                                                    StringSelection(password!!)
                                                )
                                            )
                                        }
                                    }
                                    .padding(8.dp)
                            ) {
                                Text(
                                    text = password!!,
                                    style = MaterialTheme.typography.titleLarge
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Icon(
                                    imageVector = Icons.Filled.ContentCopy,
                                    contentDescription = "Copy password",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    }

                    errorMessage != null -> {
                        Text(
                            text = errorMessage!!,
                            color = MaterialTheme.colorScheme.error
                        )
                    }

                    else -> {
                        Text("Enter a length and generate a password")
                    }
                }
            }
        }
    }
}
