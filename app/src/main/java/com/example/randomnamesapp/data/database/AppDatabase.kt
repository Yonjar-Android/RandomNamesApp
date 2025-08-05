package com.example.randomnamesapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.randomnamesapp.data.database.daos.NameDao
import com.example.randomnamesapp.data.database.entities.GenderEntity
import com.example.randomnamesapp.data.database.entities.NameEntity
import com.example.randomnamesapp.data.database.entities.OriginEntity

@Database(
    entities = [NameEntity::class, GenderEntity::class, OriginEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun nameDao(): NameDao
}