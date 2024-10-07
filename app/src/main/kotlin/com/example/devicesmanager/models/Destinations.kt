package com.example.devicesmanager.models

private enum class DestName {
    HOME, DEVS, APPS, MESS, STATS, SETT
}

sealed class Destination(val name: String) {
    object Home : Destination(DestName.HOME.name)
    object Devices : Destination(DestName.DEVS.name)
    object Apparatuses : Destination(DestName.APPS.name)
    object Messages : Destination(DestName.MESS.name)
    object Statistics : Destination(DestName.STATS.name)
    object Setting : Destination(DestName.SETT.name)

    companion object {
        val orderedDestinations = listOf(Home, Devices, Apparatuses, Messages, Statistics, Setting)
    }
}