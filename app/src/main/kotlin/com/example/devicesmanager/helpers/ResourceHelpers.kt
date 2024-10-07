package com.example.devicesmanager.helpers

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.devicesmanager.R

@Composable
fun getMessagesStrings(isReceived: Boolean) = listOf(
    R.string.message_field_id,
    R.string.message_field_time,
    R.string.message_field_date,
    if (isReceived) R.string.message_field_author else R.string.message_field_recipient,
    R.string.message_field_text
).map { stringResource(it) }

@Composable
fun getDevicesStrings() = listOf(
    R.string.devices_field_name,
    R.string.devices_field_type,
    R.string.devices_field_status,
    R.string.devices_field_mac,
    R.string.devices_field_subs,
).map { stringResource(it) }

@Composable
fun getMessageFieldsTitles() = listOf(
    R.string.devices_field_name,
    R.string.devices_field_type,
    R.string.devices_field_status,
    R.string.devices_field_mac,
    R.string.devices_field_subs
).map { stringResource(it) }