package com.eyuppastirmaci.safekeys.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import org.jetbrains.compose.resources.Font
import safekeys.composeapp.generated.resources.Res
import safekeys.composeapp.generated.resources.inter_bold
import safekeys.composeapp.generated.resources.inter_medium
import safekeys.composeapp.generated.resources.inter_regular
import safekeys.composeapp.generated.resources.inter_semibold
import safekeys.composeapp.generated.resources.jetbrainsmono_bold
import safekeys.composeapp.generated.resources.jetbrainsmono_regular

@Composable
fun getInterFontFamily(): FontFamily {
    return FontFamily(
        Font(Res.font.inter_regular, FontWeight.Normal),
        Font(Res.font.inter_medium, FontWeight.Medium),
        Font(Res.font.inter_semibold, FontWeight.SemiBold),
        Font(Res.font.inter_bold, FontWeight.Bold)
    )
}

@Composable
fun getJetBrainsMonoFontFamily(): FontFamily {
    return FontFamily(
        Font(Res.font.jetbrainsmono_regular, FontWeight.Normal),
        Font(Res.font.jetbrainsmono_bold, FontWeight.Bold)
    )
}

@Composable
fun getAppTypography(): Typography {
    val inter = getInterFontFamily()
    val jetBrainsMono = getJetBrainsMonoFontFamily()
    val defaultTypography = Typography()

    return Typography(
        displayLarge = defaultTypography.displayLarge.copy(fontFamily = inter),
        displayMedium = defaultTypography.displayMedium.copy(fontFamily = inter),
        displaySmall = defaultTypography.displaySmall.copy(fontFamily = inter),
        headlineLarge = defaultTypography.headlineLarge.copy(fontFamily = inter),
        headlineMedium = defaultTypography.headlineMedium.copy(fontFamily = inter),
        headlineSmall = defaultTypography.headlineSmall.copy(fontFamily = inter),
        titleLarge = defaultTypography.titleLarge.copy(fontFamily = jetBrainsMono),
        titleMedium = defaultTypography.titleMedium.copy(fontFamily = inter),
        titleSmall = defaultTypography.titleSmall.copy(fontFamily = inter),
        bodyLarge = defaultTypography.bodyLarge.copy(fontFamily = inter),
        bodyMedium = defaultTypography.bodyMedium.copy(fontFamily = inter),
        bodySmall = defaultTypography.bodySmall.copy(fontFamily = inter),
        labelLarge = defaultTypography.labelLarge.copy(fontFamily = inter),
        labelMedium = defaultTypography.labelMedium.copy(fontFamily = inter),
        labelSmall = defaultTypography.labelSmall.copy(fontFamily = inter)
    )
}

