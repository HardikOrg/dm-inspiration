package com.example.devicesmanager.room

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.example.devicesmanager.utils.dateAndTimePattern
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@ProvidedTypeConverter
class TimestampConverter {
    @TypeConverter
    fun fromTimestamp(value: String?) = value?.let {
        LocalDateTime.parse(it, DateTimeFormatter.ofPattern(dateAndTimePattern))
    }

    @TypeConverter
    fun toTimestamp(value: LocalDateTime?) = value?.let {
        value.format(DateTimeFormatter.ofPattern(dateAndTimePattern))
    }
}