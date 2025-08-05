package com.example.randomnamesapp.data.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.randomnamesapp.data.database.entities.NameEntity

@Dao
interface NameDao {
    @Query("""SELECT * FROM names
        ORDER BY RANDOM()
        LIMIT 1
    """)
    suspend fun getRandomName(): NameEntity

    @Insert
    suspend fun insertName(name: NameEntity)

    @Insert
    suspend fun insertAllNames(names: List<NameEntity>)

}