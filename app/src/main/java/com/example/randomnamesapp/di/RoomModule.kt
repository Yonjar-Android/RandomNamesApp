package com.example.randomnamesapp.di

import com.example.randomnamesapp.data.database.AppDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val roomModule = module {
    single {
        AppDatabase.getDatabase(androidContext())
    } // Get Singleton Database instance

    single { get<AppDatabase>().nameDao() } // Get Name DAO

    single { get<AppDatabase>().genderDao() } // Get Gender DAO

    single { get<AppDatabase>().originDao() } // Get Origin DAO


}