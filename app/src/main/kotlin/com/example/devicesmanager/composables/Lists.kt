package com.example.devicesmanager.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.devicesmanager.models.ListItem
import com.example.devicesmanager.ui.theme.DMTheme
import kotlinx.collections.immutable.ImmutableList

@Composable
fun ListSegment(
    maxHeight: Dp? = null,
    items: ImmutableList<ListItem>,
    modifiers: List<Modifier>,
    textsFromItem: (ListItem) -> List<String>,
    textsFromHeader: @Composable () -> List<String>,
    title: String? = null,
    titleStyle: TextStyle = DMTheme.typography.titleMedium,
    specialFields: Set<Int> = emptySet(),
    onItemClicked: ((ListItem) -> Unit) = {},
    backgroundColor: Color = DMTheme.colorScheme.surfaceContainerLow,
) {
    val scrollState = rememberScrollState()

    Column(
        verticalArrangement = Arrangement.spacedBy(DMTheme.paddings.small)
    ) {
        if (title == null) {
            Spacer(modifier = Modifier)
        } else {
            Text(
                modifier = Modifier.padding(DMTheme.paddings.medium, 0.dp, 0.dp, 0.dp),
                style = titleStyle,
                color = Color.Black,
                text = title
            )
        }
        Box(
            modifier = Modifier.horizontalScroll(scrollState)
        ) {
            LazyColumn(
                modifier = (if (maxHeight == null) Modifier else Modifier.heightIn(0.dp, maxHeight))
                    .fillMaxWidth()
                    .background(color = backgroundColor),
                verticalArrangement = Arrangement.spacedBy(DMTheme.paddings.extraSmall),
            ) {
                item {
                    ListElementHeader(
                        texts = textsFromHeader(),
                        modifiers = modifiers
                    )
                }
                repeat(items.size) { index ->
                    item {
                        ListElementDefault(
                            texts = textsFromItem(items[index]),
                            modifiers = modifiers,
                            specialFields = specialFields,
                            onItemClicked = { onItemClicked(items[index]) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ListElementHeader(
    texts: List<String>,
    modifiers: List<Modifier>,
    headerColor: Color = Color.Black,
    headerStyle: TextStyle = DMTheme.typography.titleMedium,
) {
    ListElement(
        texts = texts,
        modifiers = modifiers,
        style = headerStyle,
        color = { headerColor }
    )
}

@Composable
private fun ListElementDefault(
    texts: List<String>,
    modifiers: List<Modifier>,
    specialFields: Set<Int> = emptySet(),
    defaultColor: Color = DMTheme.colorScheme.secondary,
    specialColor: Color = DMTheme.colorScheme.primary,
    textStyle: TextStyle = DMTheme.typography.bodyLarge,
    onItemClicked: () -> Unit = {}
) {
    ListElement(
        texts = texts,
        modifiers = modifiers,
        style = textStyle,
        color = { if (specialFields.contains(it)) specialColor else defaultColor },
        onItemClicked = onItemClicked
    )
}

@Composable
private fun ListElement(
    texts: List<String>,
    modifiers: List<Modifier>,
    style: TextStyle,
    color: (Int) -> Color,
    onItemClicked: (() -> Unit)? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                enabled = onItemClicked != null,
                onClick = onItemClicked ?: {}
            ),
        horizontalArrangement = Arrangement.spacedBy(DMTheme.paddings.extraSmall),
    ) {
        Spacer(modifier = Modifier.width(10.dp))

        repeat(modifiers.size) { index ->
            Text(
                modifier = modifiers[index],
                style = style,
                text = texts[index],
                color = color(index),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}