package com.example.devicesmanager.di

import com.example.devicesmanager.repositories.DevicesRepository
import com.example.devicesmanager.repositories.MessagesRepository
import com.example.devicesmanager.room.DMDatabase
import com.example.devicesmanager.room.getDeviceDao
import com.example.devicesmanager.room.getMessageDao
import com.example.devicesmanager.room.getRoomDatabase
import com.example.devicesmanager.viewmodels.ActivityViewModel
import com.example.devicesmanager.viewmodels.DevicesViewModel
import com.example.devicesmanager.viewmodels.HomeViewModel
import com.example.devicesmanager.viewmodels.MessagesViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.core.qualifier.named
import org.koin.dsl.module
import kotlin.coroutines.CoroutineContext

object KoinModules {
    private val contextModule = module {
        single<CoroutineContext>(named("ioContext")) { Dispatchers.IO }
    }

    private val roomModule = module {
        single<DMDatabase> { getRoomDatabase(androidContext()) }

        single { getMessageDao(get()) }
        single { getDeviceDao(get()) }
    }

    private val repositoriesModule = module {
        single<MessagesRepository> { MessagesRepository(get(), get(named("ioContext"))) }
        single<DevicesRepository> { DevicesRepository(get(), get(named("ioContext"))) }
    }

    private val viewModelModule = module {
        viewModelOf(::ActivityViewModel)
        viewModel<HomeViewModel> { HomeViewModel(get(), get(), get(named("ioContext"))) }
        viewModelOf(::MessagesViewModel)
        viewModelOf(::DevicesViewModel)
    }

    fun getModules() = listOf(contextModule, roomModule, repositoriesModule, viewModelModule)
}