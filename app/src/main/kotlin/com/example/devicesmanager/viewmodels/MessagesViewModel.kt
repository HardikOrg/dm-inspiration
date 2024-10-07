package com.example.devicesmanager.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.devicesmanager.models.ListMessage
import com.example.devicesmanager.repositories.MessagesRepository
import com.example.devicesmanager.room.MessageEntity
import com.example.devicesmanager.room.messagesToModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import java.time.LocalDateTime

class MessagesViewModel(
    private val repository: MessagesRepository
) : ContainerHost<MessagesViewModel.MessagesState, Nothing>, ViewModel() {
    data class MessagesState(
        val name: String = "JoyBoy",
        val receivedMessages: ImmutableList<ListMessage> = persistentListOf(),
        val sentMessages: ImmutableList<ListMessage> = persistentListOf(),
        val isMsgBlockVisible: Boolean = name != "",
        val msgRecipient: String = "",
        val msgText: String = ""
    )

    override val container = container<MessagesState, Nothing>(MessagesState())
    private var _lastName: String? = null

    init {
        viewModelScope.launch {
            container.stateFlow.collectLatest {
                if (it.name != _lastName) {
                    _lastName = it.name.also { newName ->
                        launch { fetchMessagesReceived(newName) }
                        launch { fetchMessagesSent(newName) }
                    }
                }

                intent {
                    when {
                        it.name == "" && state.isMsgBlockVisible ->
                            reduce {
                                state.copy(isMsgBlockVisible = false)
                            }

                        it.name != "" && !state.isMsgBlockVisible ->
                            reduce {
                                state.copy(isMsgBlockVisible = true)
                            }
                    }
                }
            }
        }
    }

    private suspend fun fetchMessagesReceived(name: String) {
        repository.getAllReceivedBy(name).collectLatest {
            intent {
                reduce {
                    state.copy(receivedMessages = messagesToModel(it).toPersistentList())
                }
            }
        }
    }

    private suspend fun fetchMessagesSent(name: String) {
        repository.getAllSentBy(name).collectLatest {
//            Timber.d("${it.size} name: $name")
            intent {
                reduce {
                    state.copy(sentMessages = messagesToModel(it).toPersistentList())
                }
            }
        }
    }

    fun sendMessage() {
        intent {
            val newItem = MessageEntity(
                timestamp = LocalDateTime.now(),
                author = state.name,
                recipient = state.msgRecipient,
                text = state.msgText
            )

            viewModelScope.launch { repository.insert(newItem) }

            reduce {
                state.copy(msgRecipient = "", msgText = "")
            }
        }
    }

    fun changeName(newStr: String) {
        intent {
            reduce {
                state.copy(name = newStr)
            }
        }
    }

    fun changeText(newStr: String) = intent {
        reduce {
            state.copy(msgText = newStr)
        }
    }

    fun changeRecipient(newStr: String) = intent {
        reduce {
            state.copy(msgRecipient = newStr)
        }
    }
}