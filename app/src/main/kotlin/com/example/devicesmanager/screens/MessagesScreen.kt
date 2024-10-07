package com.example.devicesmanager.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.devicesmanager.R
import com.example.devicesmanager.composables.DMTextButtonFilled
import com.example.devicesmanager.composables.ListSegment
import com.example.devicesmanager.helpers.DevicePreview
import com.example.devicesmanager.helpers.PreviewWrapper
import com.example.devicesmanager.helpers.getMessagesStrings
import com.example.devicesmanager.models.ListMessage
import com.example.devicesmanager.ui.theme.DMTheme
import com.example.devicesmanager.viewmodels.MessagesViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import org.koin.androidx.compose.koinViewModel
import org.orbitmvi.orbit.compose.collectAsState

@Composable
fun MessagesScreen(viewModel: MessagesViewModel = koinViewModel<MessagesViewModel>()) {
    val state by viewModel.collectAsState()

    MessagesContent(
        name = state.name,
        onNameChanged = { viewModel.changeName(it) },
        receivedMessages = state.receivedMessages,
        sentMessages = state.sentMessages,
        recipientString = state.msgRecipient,
        onRecipientChanged = { viewModel.changeRecipient(it) },
        textString = state.msgText,
        isBottomBarVisible = state.isMsgBlockVisible,
        onTextChanged = { viewModel.changeText(it) },
        onSendClicked = { viewModel.sendMessage() }
    )
}

@Composable
private fun MessagesContent(
    name: String = "",
    onNameChanged: (String) -> Unit = {},
    receivedMessages: ImmutableList<ListMessage> = persistentListOf(),
    sentMessages: ImmutableList<ListMessage> = persistentListOf(),
    recipientString: String = "",
    onRecipientChanged: (String) -> Unit = {},
    textString: String = "",
    isBottomBarVisible: Boolean = true,
    onTextChanged: (String) -> Unit = {},
    onSendClicked: () -> Unit = {}
) {
    val horizontalPadding = PaddingValues(DMTheme.paddings.medium, 0.dp)
    val innerPaddingsModifiers = listOf(50, 80, 100, 100, 400).map { Modifier.width(it.dp) }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopBar(
            name = name,
            onNameChanged = onNameChanged,
            horizontalPadding = horizontalPadding,
        )
        BoxWithConstraints(
            modifier = Modifier.weight(1f)
        ) {
            val listSegmentsSpace =
                this.maxHeight - DMTheme.paddings.medium * 2 - DividerDefaults.Thickness
//                        DMTheme.typography.bodyLarge.lineHeight.value.dp * 2

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(
                    space = DMTheme.paddings.small,
                    alignment = Alignment.CenterVertically
                )
            ) {
                ListSegment(
                    maxHeight = listSegmentsSpace / 2,
                    items = receivedMessages,
                    modifiers = innerPaddingsModifiers,
                    textsFromItem = { item ->
                        (item as ListMessage).getAllFieldsStrings(true)
                    },
                    textsFromHeader = { getMessagesStrings(true) },
                    title = stringResource(R.string.messages_received)
                )
                HorizontalDivider()
                ListSegment(
                    maxHeight = listSegmentsSpace / 2,
                    items = sentMessages,
                    modifiers = innerPaddingsModifiers,
                    textsFromItem = { item ->
                        (item as ListMessage).getAllFieldsStrings(false)
                    },
                    textsFromHeader = { getMessagesStrings(false) },
                    title = stringResource(R.string.messages_sent)
                )
            }
        }
        BottomBar(
            isVisible = isBottomBarVisible,
            recipientString = recipientString,
            onRecipientChanged = onRecipientChanged,
            textString = textString,
            onTextChanged = onTextChanged,
            horizontalPadding = horizontalPadding,
            onSendClicked = onSendClicked
        )
    }
}

@Composable
private fun TopBar(
    name: String,
    onNameChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
    horizontalPadding: PaddingValues
) {
    Column(
        modifier = modifier.padding(0.dp, DMTheme.paddings.small, 0.dp, 0.dp),
        verticalArrangement = Arrangement.spacedBy(DMTheme.paddings.medium)
    ) {
        Row(
            modifier = Modifier.padding(horizontalPadding),
            horizontalArrangement = Arrangement.spacedBy(DMTheme.paddings.medium)
        ) {
            OutlinedTextField(
                modifier = Modifier.weight(1f),
                value = name,
                label = { Text(stringResource(R.string.messages_name)) },
                singleLine = true,
                onValueChange = { onNameChanged(it) }
            )

            Row(
                // TODO Fix hardcoded padding
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically)
                    .padding(0.dp, 8.dp, 0.dp, 0.dp),
            ) {
                Text(
                    text = stringResource(R.string.messages_period)
                )
                Text(
                    text = "All",
                    color = DMTheme.colorScheme.primary,
                )
            }
        }
        HorizontalDivider()
    }
}

@Composable
private fun BottomBar(
    isVisible: Boolean = true,
    modifier: Modifier = Modifier,
    recipientString: String,
    onRecipientChanged: (String) -> Unit,
    textString: String,
    onTextChanged: (String) -> Unit,
    horizontalPadding: PaddingValues,
    onSendClicked: () -> Unit = {}
) {
    if (!isVisible) return

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(0.dp, 0.dp, 0.dp, DMTheme.paddings.medium),
        verticalArrangement = Arrangement.spacedBy(DMTheme.paddings.small)
    ) {
        HorizontalDivider()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontalPadding),
            horizontalArrangement = Arrangement.spacedBy(DMTheme.paddings.medium)
        ) {
            OutlinedTextField(
                modifier = Modifier.weight(1f),
                value = recipientString,
                label = { Text(stringResource(R.string.messages_recipient)) },
                singleLine = true,
                onValueChange = { onRecipientChanged(it) }
            )
            DMTextButtonFilled(
                // TODO Fix hardcoded padding
                modifier = Modifier
                    .height(64.dp)
                    .padding(0.dp, 8.dp, 0.dp, 0.dp),
                text = stringResource(R.string.send_button),
                onClick = onSendClicked
            )
        }
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontalPadding),
            value = textString,
            label = { Text(stringResource(R.string.messages_text)) },
            onValueChange = { onTextChanged(it) }
        )
    }
}

@DevicePreview
@Composable
private fun Preview() {
    PreviewWrapper {
        MessagesContent()
    }
}