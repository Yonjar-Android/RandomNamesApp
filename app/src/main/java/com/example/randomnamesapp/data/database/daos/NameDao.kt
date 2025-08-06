package com.example.randomnamesapp.data.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.randomnamesapp.data.database.entities.NameEntity

@Dao
interface NameDao {
    @Query("""SELECT * FROM names
        ORDER BY RANDOM()
        LIMIT 10
    """)
    suspend fun getRandomName(): List<NameEntity>
}