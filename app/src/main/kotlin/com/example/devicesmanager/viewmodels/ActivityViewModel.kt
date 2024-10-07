package com.example.devicesmanager.viewmodels

import androidx.lifecycle.ViewModel
import com.example.devicesmanager.models.Destination
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container

class ActivityViewModel : ContainerHost<ActivityViewModel.ActivityState, Nothing>, ViewModel() {
    data class ActivityState(
        val currentScreenId: Int = 0,
    )

    override val container = container<ActivityState, Nothing>(ActivityState())
    val destinations = Destination.orderedDestinations

    fun changeCurrentScreen(newId: Int) {
        intent {
            reduce {
                state.copy(currentScreenId = newId)
            }
        }
    }
}