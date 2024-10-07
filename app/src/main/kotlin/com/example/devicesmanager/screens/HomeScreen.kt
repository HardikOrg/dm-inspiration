package com.example.devicesmanager.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.example.devicesmanager.composables.DMTextButtonFilled
import com.example.devicesmanager.helpers.DevicePreview
import com.example.devicesmanager.helpers.PreviewWrapper
import com.example.devicesmanager.ui.theme.DMTheme
import com.example.devicesmanager.viewmodels.HomeViewModel
import org.koin.androidx.compose.koinViewModel
import org.orbitmvi.orbit.compose.collectAsState

@Composable
fun HomeScreen(viewModel: HomeViewModel = koinViewModel<HomeViewModel>()) {
    val state by viewModel.collectAsState()

    HomeContent(
        messagesAmount = state.messagesAmount,
        onMessagesPopulate = { viewModel.populateWithMessages() },
        onMessagesClear = { viewModel.deleteAllMessages() },
        devicesAmount = state.devicesAmount,
        onDevicesPopulate = { viewModel.populateWithDevices() },
        onDevicesClear = { viewModel.deleteAllDevices() }
    )
}

@Composable
fun HomeContent(
    messagesAmount: Int,
    onMessagesPopulate: () -> Unit = {},
    onMessagesClear: () -> Unit = {},
    devicesAmount: Int,
    onDevicesPopulate: () -> Unit = {},
    onDevicesClear: () -> Unit = {},
) {
    Column(
        modifier = Modifier.padding(DMTheme.paddings.small)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center),
                verticalArrangement = Arrangement.spacedBy(DMTheme.paddings.large),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Row(horizontalArrangement = Arrangement.spacedBy(DMTheme.paddings.small)) {
                        DMTextButtonFilled(
                            text = "Populate with test data",
                            onClick = onMessagesPopulate
                        )
                        DMTextButtonFilled(
                            text = "Clear all",
                            onClick = onMessagesClear
                        )
                    }
                    Text(text = "Messages in the DB: $messagesAmount")
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Row(horizontalArrangement = Arrangement.spacedBy(DMTheme.paddings.small)) {
                        DMTextButtonFilled(
                            text = "Populate with test data",
                            onClick = onDevicesPopulate
                        )
                        DMTextButtonFilled(
                            text = "Clear all",
                            onClick = onDevicesClear
                        )
                    }
                    Text(text = "Devices in the DB: $devicesAmount")
                }
            }
        }
        BottomBar()
    }
}

@Composable
private fun BottomBar() {
    Text(
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center,
        text = "Do not forget to check out\n[Apparatuses], [Statistics] or [Settings]"
    )
}

@DevicePreview
@Composable
private fun Preview() {
    PreviewWrapper {
        HomeContent(
            messagesAmount = 10,
            devicesAmount = 10
        )
    }
}