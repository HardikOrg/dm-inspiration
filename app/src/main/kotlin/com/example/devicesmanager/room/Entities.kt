package com.example.devicesmanager.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.devicesmanager.models.Status
import java.time.LocalDateTime

const val tableMessages = "tableMessages"
const val tableDevices = "tableDevices"

@Entity(tableName = tableMessages)
data class MessageEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val timestamp: LocalDateTime,
    val author: String,
    val recipient: String,
    val text: String
)

@Entity(tableName = tableDevices)
data class DeviceEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val name: String,
    val type: String,
    val status: Status,
    val mac: String,
    val subscriptions: String
)