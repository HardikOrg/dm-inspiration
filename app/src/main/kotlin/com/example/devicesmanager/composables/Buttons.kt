package com.example.devicesmanager.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.devicesmanager.helpers.PreviewWrapper
import com.example.devicesmanager.ui.theme.DMTheme

@Composable
private fun DMTextButton(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    text: String = "",
    colors: ButtonColors,
    border: BorderStroke? = null,
    onClick: () -> Unit = {}
) {
    Button(
        modifier = modifier,
        shape = DMTheme.shapes.small,
        colors = colors,
        border = border,
        onClick = onClick,
        contentPadding = contentPadding
    ) {
        Text(text = text)
    }
}

@Composable
fun DMTextButtonFilled(
    modifier: Modifier = Modifier,
    text: String = "",
    onClick: () -> Unit = {}
) {
    DMTextButton(
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(),
        text = text,
        onClick = onClick,
    )
}

@Composable
fun DMTextButtonBorderless(
    modifier: Modifier = Modifier,
    selected: Boolean = false,
    text: String = "",
    onClick: () -> Unit = {}
) {
    DMTextButton(
        modifier = modifier,
        colors = ButtonDefaults.textButtonColors().let {
            if (selected) {
                // TODO fix hardcoded value
                it.copy(contentColor = Color.Black)
            } else it
        },
        text = text,
        onClick = onClick,
    )
}

@Composable
fun DMTextButtonOutlined(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    selected: Boolean = false,
    text: String = "",
    onClick: () -> Unit = {}
) {
    val (colors, border) = if (selected) {
        ButtonDefaults.buttonColors().copy(
            // TODO fix hardcoded values
            contentColor = Color.Black,
            containerColor = Color.Black.copy(alpha = 0.2f)
        ) to null
    } else {
        ButtonDefaults.outlinedButtonColors() to
                BorderStroke(
                    width = ButtonDefaults.outlinedButtonBorder(true).width,
                    color = ButtonDefaults.outlinedButtonColors().contentColor
                )
    }

    DMTextButton(
        modifier = modifier,
        colors = colors,
        border = border,
        text = text,
        onClick = onClick,
        contentPadding = contentPadding
    )
}

@Preview
@Composable
private fun Preview() {
    PreviewWrapper {
        Column(
            modifier = Modifier.background(color = Color.White)
        ) {
            Row {
                DMTextButtonFilled(text = "Hello1")
            }
            Row {
                DMTextButtonBorderless(text = "Hello1")
                DMTextButtonBorderless(text = "Hello2", selected = true)
            }
            Row {
                DMTextButtonOutlined(text = "Hello1")
                DMTextButtonOutlined(text = "Hello2", selected = true)
            }
        }
    }
}