package com.example.devicesmanager.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.devicesmanager.R
import com.example.devicesmanager.composables.DMTextButtonFilled
import com.example.devicesmanager.composables.DMTextButtonOutlined
import com.example.devicesmanager.composables.ListSegment
import com.example.devicesmanager.composables.TextPair
import com.example.devicesmanager.helpers.DevicePreview
import com.example.devicesmanager.helpers.PreviewWrapper
import com.example.devicesmanager.helpers.getDevicesStrings
import com.example.devicesmanager.helpers.getMessageFieldsTitles
import com.example.devicesmanager.models.ListDevice
import com.example.devicesmanager.ui.theme.DMTheme
import com.example.devicesmanager.viewmodels.DevicesViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import org.koin.androidx.compose.koinViewModel
import org.orbitmvi.orbit.compose.collectAsState

@Composable
fun DevicesScreen(viewModel: DevicesViewModel = koinViewModel<DevicesViewModel>()) {
    val state by viewModel.collectAsState()

    DevicesContent(
        currentFilterId = state.currentFilterId,
        onFilterClicked = { viewModel.onFilterCLicked(it) },
        filtersNamesList = stringArrayResource(R.array.devices_filters).toList(),
        devicesList = state.devices,
        selectedDevice = state.selectedDevice,
        onDeviceClicked = { item -> viewModel.onDeviceCLicked(item) },
        text = state.text,
        onTextChanged = { viewModel.changeText(it) }
    )
}

@Composable
private fun DevicesContent(
    currentFilterId: Int = 0,
    onFilterClicked: (Int) -> Unit = {},
    filtersNamesList: List<String> = emptyList(),
    devicesList: ImmutableList<ListDevice> = persistentListOf(),
    selectedDevice: ListDevice? = null,
    onDeviceClicked: (ListDevice) -> Unit = {},
    text: String = "",
    onTextChanged: (String) -> Unit = {}
) {
    val horizontalPadding = PaddingValues(DMTheme.paddings.medium, 0.dp)
    val innerPaddingsModifiers = listOf(90, 110, 80, 160, 140).map { Modifier.width(it.dp) }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopBar(
            currentFilterId = currentFilterId,
            onFilterClicked = onFilterClicked,
            filterNames = filtersNamesList,
        )

        Box(
            modifier = Modifier.weight(1f)
        ) {
            ListSegment(
                items = devicesList,
                modifiers = innerPaddingsModifiers,
                textsFromItem = { item ->
                    (item as ListDevice).getAllFieldsStrings()
                },
                specialFields = ListDevice.getSpecialFieldIds(),
                textsFromHeader = { getDevicesStrings() },
                onItemClicked = { onDeviceClicked(it as ListDevice) }
            )
        }

        BottomBar(
            selectedListDevice = selectedDevice,
            textString = text,
            onTextChanged = onTextChanged,
            horizontalPadding = horizontalPadding,
            onSendClicked = {}
        )
    }
}

@Composable
private fun TopBar(
    currentFilterId: Int = 0,
    onFilterClicked: (Int) -> Unit = {},
    filterNames: List<String> = emptyList(),
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.padding(0.dp, DMTheme.paddings.small),
        verticalArrangement = Arrangement.spacedBy(DMTheme.paddings.small)
    ) {
        LazyRow(
            modifier = Modifier.padding(DMTheme.paddings.small, 0.dp),
            horizontalArrangement = Arrangement.spacedBy(DMTheme.paddings.small)
        ) {
            repeat(filterNames.size) {
                item {
                    DMTextButtonOutlined(
                        modifier = Modifier
                            .heightIn(0.dp, 30.dp)
                            .width(90.dp),
                        contentPadding = PaddingValues(0.dp, 0.dp),
                        selected = it == currentFilterId,
                        text = filterNames[it],
                        onClick = { onFilterClicked(it) }
                    )
                }
            }
        }
        HorizontalDivider()
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun BottomBar(
    modifier: Modifier = Modifier,
    selectedListDevice: ListDevice? = null,
    textString: String,
    onTextChanged: (String) -> Unit,
    horizontalPadding: PaddingValues,
    onSendClicked: () -> Unit = {}
) {
    selectedListDevice?.let { selectedItem ->
        val titles = getMessageFieldsTitles()

        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(0.dp, 0.dp, 0.dp, DMTheme.paddings.medium),
            verticalArrangement = Arrangement.spacedBy(DMTheme.paddings.small)
        ) {
            HorizontalDivider()

            Column(
                modifier = Modifier.padding(horizontalPadding),
                verticalArrangement = Arrangement.spacedBy(DMTheme.paddings.small)
            ) {
                Row {
                    FlowRow(
                        modifier = Modifier.weight(1f),
                        horizontalArrangement = Arrangement.spacedBy(DMTheme.paddings.small)
                    ) {
                        TextPair(titles[0], selectedItem.name)
                        TextPair(titles[1], selectedItem.type)
                        TextPair(titles[2], selectedItem.status.nameString)
                        TextPair(titles[3], selectedItem.mac)
                        TextPair(titles[4], selectedItem.subscriptions)
                    }
                    Box(
                        modifier = Modifier.align(Alignment.Bottom)
                    ) {
                        DMTextButtonFilled(
                            modifier = Modifier.height(64.dp),
                            text = stringResource(R.string.send_button),
                            onClick = onSendClicked
                        )
                    }
                }

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    value = textString,
                    onValueChange = { onTextChanged(it) }
                )
            }
        }
    }
}

@DevicePreview
@Composable
private fun Preview() {
    PreviewWrapper {
        DevicesContent()
    }
}