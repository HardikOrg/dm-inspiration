package com.example.devicesmanager.utils

import com.example.devicesmanager.models.Status
import com.example.devicesmanager.room.DeviceEntity
import com.example.devicesmanager.room.MessageEntity
import okhttp3.internal.toHexString
import java.time.LocalDateTime
import java.time.ZoneOffset
import kotlin.random.Random

const val timePattern = "HH:mm:ss"
const val datePattern = "dd.MM.yyyy"
const val dateAndTimePattern = "HH:mm:ss dd.MM.yyyy"

object Generator {
    private val names = listOf(
        "Andre", "Anri", "Rosaria",
        "Quelana", "Petrus", "Solaire",
        "Melina", "Marika", "Malenia",
        "JoyBoy", "Steve", "Corvo",
        "GDI", "Nod", "Artanis"
    )

    private val texts = listOf(
        "Lorem Ipsum", "Praise the sun", "Press F",
        "Approve Live Data", "Check CAM-1", "For Ashina",
        "For Kolechia", "Petrichor V", "You're Gonna Need a Bigger Ukulele",
        "You feel an evil presence watching you...", "Impending doom approaches...",
        "Was that the bite of 87?", "You have angered the gods",
        "You were a weapon, a bringer of death.", "Also try Minecraft", "Mushroom!"
    )

    private val types = listOf(
        "Camera", "Participant", "LiveData",
        "Block", "Dungeon", "Item",
        "NexusEvent", "Weapon", "Boss",
        "StateFlow", "Forge", "Kitchen"
    )

    private val deviceNames = listOf(
        "Cam-1", "Cam-2", "Cam-42", "Gwyn", "JoyBoy", "Terraprisma", "Solaire", "Machine",
        "Kolobok", "chad", "Rock", "Rainbolt", "Protag-1", "Protag-2", "Protag-3",
        "xXx", "King-2", "Leon Kennedy", "Cookie Clicker", "Drill", "Staion", "Space Station"
    )

    private val subs = listOf(
        "no", "SM-1", "SM-2", "SM-3", "hehe", "SM-5",
        "X5-93", "98", "5$", "bruh", "manCO", "based", "based on what"
    )

    private fun timeAndDateString(before: LocalDateTime): LocalDateTime {
        val maxDay = before.minusDays(4).toEpochSecond(ZoneOffset.UTC)
        val newEpoch = Random.nextLong(10, maxDay)

        return LocalDateTime.ofEpochSecond(newEpoch, 0, ZoneOffset.UTC)
    }

    private fun mac6String() = listOf(
        (16..255).random(),
        (16..255).random(),
        (16..255).random(),
        (16..255).random(),
        (16..255).random(),
        (16..255).random()
    ).foldIndexed("") { index, res, num ->
        res + if (index == 0) num.toHexString() else ":${num.toHexString()}"
    }

    private fun status() = Status.entries.random()

    private fun randomMessage(
        beforeTime: LocalDateTime, author: String? = null, recipient: String? = null
    ) = MessageEntity(
        timestamp = timeAndDateString(beforeTime),
        author = author ?: names.random(),
        recipient = recipient ?: names.random(),
        text = texts.random()
    )

    private fun randomDevice(biasName: String?) = DeviceEntity(
        name = biasName ?: names.random(),
        type = types.random(),
        status = status(),
        mac = mac6String(),
        subscriptions = subs.random()
    )

    fun generateMessages(beforeTime: LocalDateTime, biasName: String?): List<MessageEntity> {
        val res = mutableListOf<MessageEntity>()

        if (biasName == null) {
            repeat(8) { res.add(randomMessage(beforeTime)) }
        } else {
            repeat(2) { res.add(randomMessage(beforeTime, author = biasName)) }
            repeat(2) { res.add(randomMessage(beforeTime, recipient = biasName)) }
            repeat(4) { res.add(randomMessage(beforeTime)) }
        }

        return res
    }

    fun generateDevice() = deviceNames.shuffled().take(8).map { randomDevice(it) }
}