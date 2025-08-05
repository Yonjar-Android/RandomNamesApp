package com.example.randomnamesapp.data.database.daos

import androidx.room.Dao
import androidx.room.Query
import com.example.randomnamesapp.data.database.entities.NameEntity

@Dao
interface NameDao {
    @Query("""SELECT * FROM names
        ORDER BY RANDOM()
        LIMIT 1
    """)
    suspend fun getRandomName(): NameEntity

    suspend fun insertName(name: NameEntity)
}