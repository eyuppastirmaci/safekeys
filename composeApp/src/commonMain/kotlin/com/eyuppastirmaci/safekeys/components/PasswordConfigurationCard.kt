package com.eyuppastirmaci.safekeys.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.eyuppastirmaci.safekeys.components.shared.TextInput
import com.eyuppastirmaci.safekeys.config.PasswordConfig
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun PasswordConfigurationCard(
    lengthText: String,
    onLengthChange: (String) -> Unit,
    includeUppercase: Boolean,
    includeLowercase: Boolean,
    includeNumbers: Boolean,
    includeSymbols: Boolean,
    onIncludeUppercaseChange: (Boolean) -> Unit,
    onIncludeLowercaseChange: (Boolean) -> Unit,
    onIncludeNumbersChange: (Boolean) -> Unit,
    onIncludeSymbolsChange: (Boolean) -> Unit,
    excludeAmbiguous: Boolean,
    onExcludeAmbiguousChange: (Boolean) -> Unit,
    guessesPerSecondText: String,
    onGuessesPerSecondChange: (String) -> Unit,
    isAdvancedOptionsVisible: Boolean,
    onToggleAdvancedOptions: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.1f))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
        ) {
            TextInput(
                value = lengthText,
                onValueChange = onLengthChange,
                label = "Password length (${PasswordConfig.MIN_PASSWORD_LENGTH}–${PasswordConfig.MAX_PASSWORD_LENGTH})",
                placeholder = "16",
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.Start
            ) {
                PasswordOptionRow(
                    label = "Uppercase (A-Z)",
                    checked = includeUppercase,
                    onCheckedChange = onIncludeUppercaseChange
                )
                PasswordOptionRow(
                    label = "Lowercase (a-z)",
                    checked = includeLowercase,
                    onCheckedChange = onIncludeLowercaseChange
                )
                PasswordOptionRow(
                    label = "Numbers (0-9)",
                    checked = includeNumbers,
                    onCheckedChange = onIncludeNumbersChange
                )
                PasswordOptionRow(
                    label = "Symbols (!@#\$...)",
                    checked = includeSymbols,
                    onCheckedChange = onIncludeSymbolsChange
                )

                Spacer(modifier = Modifier.height(12.dp))

                TextButton(onClick = onToggleAdvancedOptions) {
                    Text(
                        if (isAdvancedOptionsVisible) "Hide Advanced Options"
                        else "Show Advanced Options"
                    )
                }

                if (isAdvancedOptionsVisible) {
                    AdvancedOptionsContent(
                        excludeAmbiguous = excludeAmbiguous,
                        onExcludeAmbiguousChange = onExcludeAmbiguousChange,
                        guessesPerSecondText = guessesPerSecondText,
                        onGuessesPerSecondChange = onGuessesPerSecondChange
                    )
                }
            }
        }
    }
}

@Composable
private fun AdvancedOptionsContent(
    excludeAmbiguous: Boolean,
    onExcludeAmbiguousChange: (Boolean) -> Unit,
    guessesPerSecondText: String,
    onGuessesPerSecondChange: (String) -> Unit
) {
    PasswordOptionRow(
        label = "Exclude Ambiguous (il1...)",
        checked = excludeAmbiguous,
        onCheckedChange = onExcludeAmbiguousChange
    )

    Spacer(modifier = Modifier.height(12.dp))

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        TextInput(
            value = guessesPerSecondText,
            onValueChange = onGuessesPerSecondChange,
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

