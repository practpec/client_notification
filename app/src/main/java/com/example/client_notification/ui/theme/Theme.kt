package com.example.client_notification.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = MediumBlue,  // Azul medio como color primario
    secondary = NavyBlue,  // Azul marino como color secundario
    tertiary = DarkGray,   // Gris oscuro como color terciario
    background = DarkBlueVariant, // Azul oscuro para el fondo principal
    surface = Background, // Fondo de superficie (color azul oscuro)
    onPrimary = Yellow, // Texto amarillo claro sobre el color primario
    onSecondary = Dark, // Texto oscuro sobre el color secundario
    onTertiary = VeryLightWhite, // Texto blanco claro sobre el color terciario
    onBackground = LightGrayishWhite, // Texto blanco grisáceo claro sobre el fondo oscuro
    onSurface = LightGrayishWhite, // Texto blanco grisáceo claro sobre los elementos de superficie
)

@Composable
fun Client_notificationTheme(
    darkTheme: Boolean = true,
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = DarkColorScheme
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
