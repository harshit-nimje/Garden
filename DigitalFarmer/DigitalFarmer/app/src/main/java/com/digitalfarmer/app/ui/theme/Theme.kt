package com.digitalfarmer.app.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import android.content.Context
import androidx.compose.ui.platform.LocalContext

private val LightColors = lightColorScheme(
    primary = MossPrimary,
    onPrimary = Cream,
    primaryContainer = MossPrimaryContainer,
    secondary = Soil,
    secondaryContainer = SoilContainer,
    tertiary = CitrusContainer,
    tertiaryContainer = Citrus,
    background = Cream,
    surface = Cream,
    onSurface = InkDark,
    error = ErrorRed
)

private val DarkColors = darkColorScheme(
    primary = MossPrimaryContainer,
    onPrimary = InkDark,
    primaryContainer = MossPrimary,
    secondary = SoilContainer,
    tertiary = Citrus,
    background = InkDark,
    surface = InkDark,
    onSurface = Cream,
    error = ErrorRed
)

@Composable
fun DigitalFarmerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val context: Context = LocalContext.current
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S ->
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        darkTheme -> DarkColors
        else -> LightColors
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = DigitalFarmerTypography,
        shapes = DigitalFarmerShapes,
        content = content
    )
}
