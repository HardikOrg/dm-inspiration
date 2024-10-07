package com.example.devicesmanager.repositories

import com.example.devicesmanager.room.GenericDao
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

open class GenericRepository<EntityType>(
    private val dao: GenericDao<EntityType>,
    private val ioContext: CoroutineContext
) {
    suspend fun insert(entity: EntityType) {
        withContext(ioContext) {
            dao.insert(entity)
        }
    }

    suspend fun insertAll(entities: List<EntityType>) {
        withContext(ioContext) {
            dao.insertAll(entities)
        }
    }

    suspend fun delete(entity: EntityType) {
        withContext(ioContext) {
            dao.delete(entity)
        }
    }

    suspend fun update(entity: EntityType) {
        withContext(ioContext) {
            dao.update(entity)
        }
    }
}