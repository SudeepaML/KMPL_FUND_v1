package com.example.ui.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = PitchGreen,
    onPrimary = OnPitchGreen,
    primaryContainer = PitchGreen,
    onPrimaryContainer = OnPitchGreen,
    secondary = RulesGold,
    onSecondary = SurfaceContainerLowest,
    secondaryContainer = RulesGoldContainer,
    onSecondaryContainer = SurfaceDark,
    tertiary = PortalPink,
    onTertiary = TextPrimary,
    tertiaryContainer = PortalPinkLight,
    background = SurfaceDark,
    onBackground = TextPrimary,
    surface = CardDarkTier1,
    onSurface = TextPrimary,
    surfaceVariant = SurfaceContainerHigh,
    onSurfaceVariant = TextSecondary,
    outline = BorderSubtle,
    outlineVariant = BorderMedium
)

@Composable
fun MyApplicationTheme(
    content: @Composable () -> Unit
) {
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as? Activity)?.window
            if (window != null) {
                window.statusBarColor = SurfaceDark.toArgb()
                window.navigationBarColor = SurfaceDark.toArgb()
                val insetsController = WindowCompat.getInsetsController(window, view)
                insetsController.isAppearanceLightStatusBars = false
                insetsController.isAppearanceLightNavigationBars = false
            }
        }
    }

    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = AppTypography,
        content = content
    )
}
