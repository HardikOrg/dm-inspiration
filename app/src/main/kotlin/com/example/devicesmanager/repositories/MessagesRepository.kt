package com.example.devicesmanager.repositories

import com.example.devicesmanager.room.MessageDao
import com.example.devicesmanager.room.MessageEntity
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class MessagesRepository(
    private val dao: MessageDao,
    private val ioContext: CoroutineContext
) : GenericRepository<MessageEntity>(dao, ioContext) {
    fun getAllMessages() = dao.getAllMessages()

    fun getAllReceivedBy(name: String) = dao.getAllReceivedBy(name)

    fun getAllSentBy(name: String) = dao.getAllSentBy(name)

    suspend fun deleteAllMessages() = withContext(ioContext) {
        dao.deleteAllMessages()
    }
}