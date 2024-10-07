package com.example.devicesmanager.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.dp

object Paddings {
    val default = 0.dp
    val extraSmall = 4.dp
    val small = 8.dp
    val medium = 16.dp
    val large = 24.dp
    val extraLarge = 32.dp
}

internal val LocalPaddings = staticCompositionLocalOf { Paddings }