package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val SanctuaryColorScheme = darkColorScheme(
    primary = GoldPrimary,
    onPrimary = Color(0xFF1E1002),
    primaryContainer = Color(0xFF452202),
    onPrimaryContainer = GoldLight,
    secondary = EmeraldSecondary,
    onSecondary = Color(0xFF003822),
    secondaryContainer = Color(0xFF005234),
    onSecondaryContainer = EmeraldLight,
    tertiary = CyanTertiary,
    onTertiary = Color(0xFF003642),
    background = BackgroundNight,
    onBackground = Color(0xFFF1F5F9),
    surface = SurfaceDark,
    onSurface = Color(0xFFF8FAFC),
    surfaceVariant = SurfaceCard,
    onSurfaceVariant = Color(0xFFCBD5E1),
    outline = Color(0x33FFFFFF),
    error = RoseWarning,
    onError = Color.White
)

@Composable
fun MyApplicationTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = SanctuaryColorScheme,
        typography = Typography,
        content = content
    )
}
