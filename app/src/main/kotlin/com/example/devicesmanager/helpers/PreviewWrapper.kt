package com.example.devicesmanager.helpers

import androidx.compose.runtime.Composable
import com.example.devicesmanager.ui.theme.DevicesManagerTheme

@Composable
fun PreviewWrapper(content: @Composable () -> Unit) {
    DevicesManagerTheme(darkTheme = false) {
//        Surface(
//            content = content
//        )
        content()
    }
}