package com.eyuppastirmaci.safekeys.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InfoTooltip(
    text: String,
    modifier: Modifier = Modifier
) {
    TooltipBox(
        positionProvider = TooltipDefaults.rememberPlainTooltipPositionProvider(),
        tooltip = {
            PlainTooltip {
                Text(text)
            }
        },
        state = rememberTooltipState()
    ) {
        Icon(
            imageVector = Icons.Default.Info,
            contentDescription = "Info",
            modifier = modifier,
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

