package com.example.randomnamesapp.di

import com.example.randomnamesapp.data.database.daos.NameDao
import com.example.randomnamesapp.data.repositories.RoomRepository
import com.example.randomnamesapp.data.repositories.RoomRepositoryImp
import com.example.randomnamesapp.presentation.MainViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    single<RoomRepository> { RoomRepositoryImp(nameDao = get<NameDao>()) }

    viewModelOf(::MainViewModel) // Inject viewModel
}