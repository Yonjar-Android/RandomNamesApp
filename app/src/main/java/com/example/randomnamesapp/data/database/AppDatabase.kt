package com.example.randomnamesapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.randomnamesapp.data.database.daos.GenderDao
import com.example.randomnamesapp.data.database.daos.NameDao
import com.example.randomnamesapp.data.database.daos.OriginDao
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
    abstract fun genderDao(): GenderDao
    abstract fun originDao(): OriginDao

    companion object {
        fun getDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "names.db"
            )
                .createFromAsset("names.db")
                .fallbackToDestructiveMigration(true)
                .build()

        }
    }

}