package com.example.randomnamesapp.data.database.daos

import androidx.room.Dao
import androidx.room.Query
import com.example.randomnamesapp.data.database.entities.OriginEntity

@Dao
interface OriginDao {

    @Query("SELECT * FROM origins")
    suspend fun getAllOrigins(): List<OriginEntity>

}