package com.example.devicesmanager.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.devicesmanager.ui.theme.DMTheme

@Composable
fun TextPair(
    title: String,
    content: String,
    titleColor: Color = Color.Black,
    contentColor: Color = DMTheme.colorScheme.primary,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(DMTheme.paddings.extraSmall)
    ) {
        Text(
            text = "$title:",
            color = titleColor
        )
        Text(
            text = content,
            color = contentColor
        )
    }
}