package com.example.randomnamesapp.di

import androidx.room.Room
import com.example.randomnamesapp.data.database.AppDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val roomModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "app_database"
        ).build()
    } // Get Singleton Database instance

    single { get<AppDatabase>().nameDao() } // Get Name DAO

    single { get<AppDatabase>().genderDao() } // Get Gender DAO

    single { get<AppDatabase>().originDao() } // Get Origin DAO


}