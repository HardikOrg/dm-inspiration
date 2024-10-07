package com.example.devicesmanager.repositories

import com.example.devicesmanager.models.Status
import com.example.devicesmanager.room.DeviceDao
import com.example.devicesmanager.room.DeviceEntity
import kotlin.coroutines.CoroutineContext

class DevicesRepository(
    private val dao: DeviceDao,
    private val ioContext: CoroutineContext
) : GenericRepository<DeviceEntity>(dao, ioContext) {
    fun getAllDevices() = dao.getAllDevices()

    fun getFilteredDevices(status: Status) = dao.getFilteredDevices(status)

    suspend fun deleteAllDevices() = dao.deleteAllDevices()
}