package com.example.randomnamesapp

import android.app.Application
import com.example.randomnamesapp.di.appModule
import com.example.randomnamesapp.di.roomModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class RandomNamesApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@RandomNamesApp)
            modules(roomModule, appModule)
        }
    }
}