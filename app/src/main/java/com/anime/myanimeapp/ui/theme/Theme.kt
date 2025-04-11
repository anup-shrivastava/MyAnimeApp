package com.anime.myanimeapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import com.anime.myanimeapp.ui.theme.AppThemeColors.Background
import com.anime.myanimeapp.ui.theme.AppThemeColors.OnBackground
import com.anime.myanimeapp.ui.theme.AppThemeColors.OnPrimary
import com.anime.myanimeapp.ui.theme.AppThemeColors.OnSecondary
import com.anime.myanimeapp.ui.theme.AppThemeColors.OnSurface
import com.anime.myanimeapp.ui.theme.AppThemeColors.Primary
import com.anime.myanimeapp.ui.theme.AppThemeColors.Secondary
import com.anime.myanimeapp.ui.theme.AppThemeColors.Surface
import com.anime.myanimeapp.ui.theme.AppThemeColors.Tertiary

private val LightColorPalette = lightColorScheme(
    primary = Primary,
    secondary = Secondary,
    tertiary = Tertiary,
    background = Background,
    surface = Surface,
    onPrimary = OnPrimary,
    onSecondary = OnSecondary,
    onBackground = OnBackground,
    onSurface = OnSurface
)

private val DarkColorPalette = darkColorScheme(
    primary = Primary,
    secondary = Secondary,
    tertiary = Tertiary,
    background = Background,
    surface = Surface,
    onPrimary = OnPrimary,
    onSecondary = OnSecondary,
    onBackground = OnBackground,
    onSurface = OnSurface
)

@Composable
fun MyAnimeAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }
    MaterialTheme(
        colorScheme = colors,
        typography = AppTypography,
        content = content
    )
}