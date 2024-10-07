package com.example.devicesmanager.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.devicesmanager.models.ListDevice
import com.example.devicesmanager.models.Status
import com.example.devicesmanager.repositories.DevicesRepository
import com.example.devicesmanager.room.devicesToModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container

class DevicesViewModel(
    private val repository: DevicesRepository
) : ContainerHost<DevicesViewModel.DevicesState, Nothing>, ViewModel() {
    data class DevicesState(
        val currentFilterId: Int = 0,
        val devices: ImmutableList<ListDevice> = persistentListOf(),
        val selectedDevice: ListDevice? = null,
        val text: String = ""
    )

    override val container = container<DevicesState, Nothing>(DevicesState())

    init {
        intent {
            viewModelScope.launch {
                fetchFiltered(state.currentFilterId)
            }
        }
    }

    private suspend fun fetchFiltered(filterId: Int) {
        val deviceEntityListFlow = if (filterId == 0) {
            repository.getAllDevices()
        } else {
            repository.getFilteredDevices(Status.entries[filterId - 1])
        }

        deviceEntityListFlow.collectLatest {
            intent {
                reduce {
                    state.copy(devices = devicesToModel(it).toPersistentList())
                }
            }
        }
    }

    fun onFilterCLicked(clickedId: Int) {
        intent {
            if (state.currentFilterId != clickedId) {
                viewModelScope.launch { fetchFiltered(clickedId) }
                reduce {
                    state.copy(currentFilterId = clickedId)
                }
            }
        }
    }

    fun onDeviceCLicked(item: ListDevice) {
        intent {
            if (state.selectedDevice != item) {
                reduce {
                    state.copy(selectedDevice = item)
                }
            }
        }
    }


    fun changeText(newString: String) {
        intent {
            reduce {
                state.copy(text = newString)
            }
        }
    }
}