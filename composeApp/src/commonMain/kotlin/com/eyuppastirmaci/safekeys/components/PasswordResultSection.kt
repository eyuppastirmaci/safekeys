package com.eyuppastirmaci.safekeys.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.eyuppastirmaci.safekeys.model.PasswordMetrics
import com.eyuppastirmaci.safekeys.password.PasswordStrength
import com.eyuppastirmaci.safekeys.theme.StrengthFair
import com.eyuppastirmaci.safekeys.theme.StrengthGood
import com.eyuppastirmaci.safekeys.theme.StrengthStrong
import com.eyuppastirmaci.safekeys.theme.StrengthWeak

@Composable
fun PasswordResultSection(
    password: String?,
    metrics: PasswordMetrics?,
    errorMessage: String?,
    contentWidth: Dp,
    onCopyPassword: (String) -> Unit
) {
    when {
        password != null -> {
            GeneratedPasswordCard(
                password = password,
                metrics = metrics,
                onCopyPassword = onCopyPassword,
                modifier = Modifier
                    .fillMaxWidth()
                    .widthIn(max = contentWidth)
            )
        }
        errorMessage != null -> {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier
                    .fillMaxWidth()
                    .widthIn(max = contentWidth),
                textAlign = TextAlign.Center
            )
        }
        else -> {
            Text(
                "Enter a length and generate a password",
                modifier = Modifier
                    .fillMaxWidth()
                    .widthIn(max = contentWidth),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun GeneratedPasswordCard(
    password: String,
    metrics: PasswordMetrics?,
    onCopyPassword: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
        ),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)),
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Generated password",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(18.dp))
                    .background(MaterialTheme.colorScheme.surface)
                    .pointerHoverIcon(PointerIcon.Hand)
                    .clickable { onCopyPassword(password) }
                    .padding(horizontal = 20.dp, vertical = 16.dp)
            ) {
                Text(
                    text = password,
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f)
                )
                IconButton(onClick = { onCopyPassword(password) }) {
                    Icon(
                        imageVector = Icons.Filled.ContentCopy,
                        contentDescription = "Copy password",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            metrics?.let {
                Spacer(modifier = Modifier.height(20.dp))
                HorizontalDivider(
                    thickness = 1.dp,
                    color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
                )
                Spacer(modifier = Modifier.height(20.dp))

                PasswordMetricsSection(metrics = it)
            }
        }
    }
}

@Composable
private fun PasswordMetricsSection(metrics: PasswordMetrics) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "Strength: ${metrics.strength.label}",
            style = MaterialTheme.typography.bodyMedium,
            color = getStrengthColor(metrics.strength),
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        LinearProgressIndicator(
            progress = { metrics.strength.progress },
            color = getStrengthColor(metrics.strength),
            trackColor = MaterialTheme.colorScheme.surfaceVariant,
            modifier = Modifier
                .fillMaxWidth()
                .height(10.dp)
                .clip(RoundedCornerShape(6.dp))
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Crack time: ${metrics.crackTimeDisplay}",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun getStrengthColor(strength: PasswordStrength) = when (strength) {
    PasswordStrength.WEAK -> StrengthWeak
    PasswordStrength.FAIR -> StrengthFair
    PasswordStrength.GOOD -> StrengthGood
    PasswordStrength.STRONG -> StrengthStrong
}

