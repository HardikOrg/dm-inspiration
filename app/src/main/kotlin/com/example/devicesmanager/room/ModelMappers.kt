package com.example.devicesmanager.room

import com.example.devicesmanager.models.ListDevice
import com.example.devicesmanager.models.ListMessage
import com.example.devicesmanager.utils.dateAndTimePattern
import com.example.devicesmanager.utils.datePattern
import com.example.devicesmanager.utils.timePattern
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun messagesToModel(list: List<MessageEntity>) = list.map { it.toListModel() }
fun messageToEntity(list: List<ListMessage>) = list.map { it.toEntity() }

fun MessageEntity.toListModel() = ListMessage(
    id = id,
    time = timestamp.format(DateTimeFormatter.ofPattern(timePattern)),
    date = timestamp.format(DateTimeFormatter.ofPattern(datePattern)),
    author = author,
    recipient = recipient,
    text = text
)

fun ListMessage.toEntity() = MessageEntity(
    timestamp = LocalDateTime.parse(
        "$time $date",
        DateTimeFormatter.ofPattern(dateAndTimePattern)
    ),
    author = author,
    recipient = recipient,
    text = text
)

fun devicesToModel(list: List<DeviceEntity>) = list.map { it.toListModel() }
fun devicesToEntity(list: List<ListDevice>) = list.map { it.toEntity() }

fun DeviceEntity.toListModel() = ListDevice(
    name = name,
    type = type,
    status = status,
    mac = mac,
    subscriptions = subscriptions
)

fun ListDevice.toEntity() = DeviceEntity(
    name = name,
    type = type,
    status = status,
    mac = mac,
    subscriptions = subscriptions
)