package com.example.randomnamesapp.data.database.daos

import androidx.room.Dao
import androidx.room.Query
import com.example.randomnamesapp.data.database.entities.NameEntity

@Dao
interface NameDao {
    @Query("""SELECT * FROM names
        WHERE genderId IN (:genderId) AND originId IN (:origins)
        ORDER BY RANDOM()
        LIMIT 1
    """)
    suspend fun getRandomName(genderId: List<Int>, origins: List<Int>): NameEntity
}