package com.example.devicesmanager.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [MessageEntity::class, DeviceEntity::class], version = 1)
@TypeConverters(TimestampConverter::class)
abstract class DMDatabase : RoomDatabase() {
    abstract fun messageDao(): MessageDao
    abstract fun deviceDao(): DeviceDao
}

fun getMessageDao(db: DMDatabase) = db.messageDao()
fun getDeviceDao(db: DMDatabase) = db.deviceDao()

fun getRoomDatabase(applicationContext: Context) =
    Room.databaseBuilder(applicationContext, DMDatabase::class.java, "database-dm")
        .addTypeConverter(TimestampConverter())
        .build()
