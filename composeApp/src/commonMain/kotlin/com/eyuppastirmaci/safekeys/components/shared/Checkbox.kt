package com.eyuppastirmaci.safekeys.components.shared

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.semantics.Role

@Composable
fun ThemedCheckbox(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    val interactionSource = remember { MutableInteractionSource() }
    val shape = RoundedCornerShape(6.dp)

    val backgroundColor by animateColorAsState(
        targetValue = when {
            !enabled -> MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)
            checked -> MaterialTheme.colorScheme.primary
            else -> MaterialTheme.colorScheme.surfaceColorAtElevation(1.dp)
        },
        label = "CheckboxBackground"
    )

    val borderColor by animateColorAsState(
        targetValue = when {
            checked -> Color.Transparent
            !enabled -> MaterialTheme.colorScheme.outline.copy(alpha = 0.4f)
            else -> MaterialTheme.colorScheme.outline.copy(alpha = 0.8f)
        },
        label = "CheckboxBorder"
    )

    val iconTint = if (!enabled) {
        MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f)
    } else {
        MaterialTheme.colorScheme.onPrimary
    }

    Box(
        modifier = modifier
            .size(22.dp)
            .clip(shape)
            .background(backgroundColor)
            .border(width = 1.5.dp, color = borderColor, shape = shape)
            .toggleable(
                value = checked,
                enabled = enabled,
                onValueChange = onCheckedChange,
                role = Role.Checkbox,
                interactionSource = interactionSource
            ),
        contentAlignment = Alignment.Center
    ) {
        if (checked) {
            Icon(
                imageVector = Icons.Filled.Check,
                contentDescription = null,
                tint = iconTint,
                modifier = Modifier.size(14.dp)
            )
        }
    }
}

