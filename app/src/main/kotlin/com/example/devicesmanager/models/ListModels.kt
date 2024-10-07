package com.example.devicesmanager.models

interface ListItem {
}

data class ListMessage(
    val id: Int,
    val time: String,
    val date: String,
    val author: String,
    val recipient: String,
    val text: String
) : ListItem {
    fun getAllFieldsStrings(isReceived: Boolean) = listOf(
        id.toString(), time, date, if (isReceived) author else recipient, text
    )
}

enum class Status(val nameString: String) {
    LIVE("live"), APPROVED("approved"), MUTE("mute"), BLOCKED("blocked"), DEAD("dead");
}

data class ListDevice(
    val name: String,
    val type: String,
    val status: Status,
    val mac: String,
    val subscriptions: String,
) : ListItem {
    fun getAllFieldsStrings() = listOf(
        name, type, status.nameString, mac, subscriptions
    )

    companion object {
        fun getSpecialFieldIds(): Set<Int> = setOf(0, 1)
    }
}