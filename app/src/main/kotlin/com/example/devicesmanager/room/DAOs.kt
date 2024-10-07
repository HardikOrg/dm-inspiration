package com.example.devicesmanager.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.devicesmanager.models.Status
import kotlinx.coroutines.flow.Flow

@Dao
interface MessageDao : GenericDao<MessageEntity> {
    @Query("SELECT * FROM $tableMessages")
    fun getAllMessages(): Flow<List<MessageEntity>>

    @Query("SELECT * FROM $tableMessages WHERE recipient = :name")
    fun getAllReceivedBy(name: String): Flow<List<MessageEntity>>

    @Query("SELECT * FROM $tableMessages WHERE author = :name")
    fun getAllSentBy(name: String): Flow<List<MessageEntity>>

    @Query("DELETE FROM $tableMessages")
    suspend fun deleteAllMessages()
}

@Dao
interface DeviceDao : GenericDao<DeviceEntity> {
    @Query("SELECT * FROM $tableDevices")
    fun getAllDevices(): Flow<List<DeviceEntity>>

    @Query("SELECT * FROM $tableDevices WHERE status = :status")
    fun getFilteredDevices(status: Status): Flow<List<DeviceEntity>>

    @Query("DELETE FROM $tableDevices")
    suspend fun deleteAllDevices()
}

@Dao
interface GenericDao<Type> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: Type)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(entities: List<Type>)

    @Delete
    suspend fun delete(entity: Type)

    @Update
    suspend fun update(entity: Type)
}