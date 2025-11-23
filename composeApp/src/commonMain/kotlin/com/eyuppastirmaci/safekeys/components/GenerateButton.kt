package com.eyuppastirmaci.safekeys.components

import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun GenerateButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    height: Dp = 52.dp
) {
    Button(
        onClick = onClick,
        modifier = modifier.height(height)
    ) {
        Text("Generate password")
    }
}

