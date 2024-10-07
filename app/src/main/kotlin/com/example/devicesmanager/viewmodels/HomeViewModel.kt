package com.example.devicesmanager.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.devicesmanager.repositories.DevicesRepository
import com.example.devicesmanager.repositories.MessagesRepository
import com.example.devicesmanager.utils.Generator
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import java.time.LocalDateTime
import kotlin.coroutines.CoroutineContext

class HomeViewModel(
    private val messagesRepository: MessagesRepository,
    private val devicesRepository: DevicesRepository,
    private val ioContext: CoroutineContext
) : ContainerHost<HomeViewModel.HomeState, Nothing>, ViewModel() {
    data class HomeState(
        val messagesAmount: Int = 0,
        val devicesAmount: Int = 0
    )

    override val container = container<HomeState, Nothing>(HomeState())

    private val messagesStateFlow = messagesRepository.getAllMessages().stateIn(
        viewModelScope, SharingStarted.Lazily, emptyList()
    )
    private val devicesStateFlow = devicesRepository.getAllDevices().stateIn(
        viewModelScope, SharingStarted.Lazily, emptyList()
    )

    private var messagePopulateJob: Job? = null
    private var devicesPopulateJob: Job? = null

    init {
        viewModelScope.launch {
            messagesStateFlow.collectLatest {
                changeMessagesAmount(it.size)
            }
        }

        viewModelScope.launch {
            devicesStateFlow.collectLatest {
                changeDevicesAmount(it.size)
            }
        }
    }

    private fun changeMessagesAmount(newAmount: Int) = intent {
        reduce {
            state.copy(messagesAmount = newAmount)
        }
    }

    private fun changeDevicesAmount(newAmount: Int) = intent {
        reduce {
            state.copy(devicesAmount = newAmount)
        }
    }

    fun populateWithMessages() {
        messagePopulateJob?.cancel()
        messagePopulateJob = viewModelScope.launch {
            withContext(ioContext) {
                messagesRepository.insertAll(getMessagesTestData())
            }
        }
    }

    fun deleteAllMessages() {
        viewModelScope.launch {
            withContext(ioContext) {
                messagesRepository.deleteAllMessages()
            }
        }
    }

    fun populateWithDevices() {
        devicesPopulateJob?.cancel()
        devicesPopulateJob = viewModelScope.launch {
            withContext(ioContext) {
                devicesRepository.insertAll(getDevicesTestData())
            }
        }
    }

    fun deleteAllDevices() {
        viewModelScope.launch {
            withContext(ioContext) {
                devicesRepository.deleteAllDevices()
            }
        }
    }

    private fun getMessagesTestData() = Generator.generateMessages(LocalDateTime.now(), "JoyBoy")
    private fun getDevicesTestData() = Generator.generateDevice()
}