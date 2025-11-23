package com.eyuppastirmaci.safekeys.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material3.*
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.eyuppastirmaci.safekeys.components.shared.TextInput
import com.eyuppastirmaci.safekeys.config.PasswordConfig
import com.eyuppastirmaci.safekeys.password.PasswordStrength
import com.eyuppastirmaci.safekeys.platform.getClipboardHelper
import com.eyuppastirmaci.safekeys.theme.StrengthFair
import com.eyuppastirmaci.safekeys.theme.StrengthGood
import com.eyuppastirmaci.safekeys.theme.StrengthStrong
import com.eyuppastirmaci.safekeys.theme.StrengthWeak
import com.eyuppastirmaci.safekeys.viewmodel.AppViewModel

@Composable
fun getStrengthColor(strength: PasswordStrength): Color {
    return when (strength) {
        PasswordStrength.WEAK -> StrengthWeak
        PasswordStrength.FAIR -> StrengthFair
        PasswordStrength.GOOD -> StrengthGood
        PasswordStrength.STRONG -> StrengthStrong
    }
}

@Composable
fun MainScreenContent(
    viewModel: AppViewModel,
    modifier: Modifier = Modifier
) {
    val clipboardHelper = getClipboardHelper()

    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "SafeKeys",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.align(Alignment.Center)
            )

            IconButton(
                onClick = { viewModel.toggleTheme() },
                modifier = Modifier.align(Alignment.CenterEnd)
            ) {
                Icon(
                    imageVector = if (viewModel.isDarkTheme) Icons.Filled.LightMode else Icons.Filled.DarkMode,
                    contentDescription = "Toggle theme"
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextInput(
            value = viewModel.lengthText,
            onValueChange = { viewModel.updateLengthText(it) },
            label = "Password length (${PasswordConfig.MIN_PASSWORD_LENGTH}–${PasswordConfig.MAX_PASSWORD_LENGTH})",
            placeholder = "16",
            singleLine = true,
            modifier = Modifier.width(300.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier.width(IntrinsicSize.Max),
            horizontalAlignment = Alignment.Start
        ) {
            PasswordOptionRow(
                label = "Uppercase (A-Z)",
                checked = viewModel.includeUppercase,
                onCheckedChange = { viewModel.updateIncludeUppercase(it) }
            )
            PasswordOptionRow(
                label = "Lowercase (a-z)",
                checked = viewModel.includeLowercase,
                onCheckedChange = { viewModel.updateIncludeLowercase(it) }
            )
            PasswordOptionRow(
                label = "Numbers (0-9)",
                checked = viewModel.includeNumbers,
                onCheckedChange = { viewModel.updateIncludeNumbers(it) }
            )
            PasswordOptionRow(
                label = "Symbols (!@#\$...)",
                checked = viewModel.includeSymbols,
                onCheckedChange = { viewModel.updateIncludeSymbols(it) }
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            TextButton(onClick = { viewModel.toggleAdvancedOptions() }) {
                Text(if (viewModel.isAdvancedOptionsVisible) "Hide Advanced Options" else "Show Advanced Options")
            }
            
            if (viewModel.isAdvancedOptionsVisible) {
                PasswordOptionRow(
                    label = "Exclude Ambiguous (il1...)",
                    checked = viewModel.excludeAmbiguous,
                    onCheckedChange = { viewModel.updateExcludeAmbiguous(it) }
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextInput(
                        value = viewModel.guessesPerSecondText,
                        onValueChange = { viewModel.updateGuessesPerSecondText(it) },
                        label = "Guesses/sec",
                        placeholder = "1000000000000",
                        singleLine = true,
                        modifier = Modifier.weight(1f),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                    
                    Spacer(modifier = Modifier.width(8.dp))
                    
                    InfoTooltip(
                        text = "The number of password guesses an attacker can attempt per second. Higher values mean faster cracking hardware."
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            viewModel.generatePassword()
        }) {
            Text("Generate password")
        }

        Spacer(modifier = Modifier.height(16.dp))

        when {
            viewModel.password != null -> {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
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
                                    viewModel.password?.let { pass ->
                                        clipboardHelper.copyToClipboard(pass)
                                        viewModel.showToast("Password copied!")
                                    }
                                }
                                .padding(8.dp)
                        ) {
                            Text(
                                text = viewModel.password!!,
                                style = MaterialTheme.typography.titleLarge,
                                modifier = Modifier.weight(1f, fill = false),
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Icon(
                                imageVector = Icons.Filled.ContentCopy,
                                contentDescription = "Copy password",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                    
                    viewModel.passwordMetrics?.let { metrics ->
                        Spacer(modifier = Modifier.height(16.dp))
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.width(300.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Start,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = "Strength: ${metrics.strength.label}",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = getStrengthColor(metrics.strength),
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            
                            Spacer(modifier = Modifier.height(4.dp))
                            
                            // Progress Bar
                            LinearProgressIndicator(
                                progress = { metrics.strength.progress },
                                color = getStrengthColor(metrics.strength),
                                trackColor = MaterialTheme.colorScheme.surfaceVariant,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(8.dp)
                                    .clip(RoundedCornerShape(4.dp))
                            )
                            
                            Spacer(modifier = Modifier.height(4.dp))
                            
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Start,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = "Crack time: ${metrics.crackTimeDisplay}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }
            }
            viewModel.errorMessage != null -> {
                Text(
                    text = viewModel.errorMessage!!,
                    color = MaterialTheme.colorScheme.error
                )
            }
            else -> {
                Text("Enter a length and generate a password")
            }
        }
    }
}

