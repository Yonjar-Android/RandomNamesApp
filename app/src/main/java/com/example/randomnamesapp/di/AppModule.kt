package com.example.randomnamesapp.di

import com.example.randomnamesapp.data.database.daos.GenderDao
import com.example.randomnamesapp.data.database.daos.NameDao
import com.example.randomnamesapp.data.database.daos.OriginDao
import com.example.randomnamesapp.data.repositories.RoomRepository
import com.example.randomnamesapp.data.repositories.RoomRepositoryImp
import com.example.randomnamesapp.presentation.MainViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    single<RoomRepository> { RoomRepositoryImp(nameDao = get<NameDao>(),
        genderDao = get<GenderDao>(),
        originDao = get<OriginDao>()) }

    viewModelOf(::MainViewModel) // Inject viewModel
}