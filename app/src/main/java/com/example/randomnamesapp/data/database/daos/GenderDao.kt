package com.example.randomnamesapp.data.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.randomnamesapp.data.database.entities.GenderEntity

@Dao
interface GenderDao {

    @Query("SELECT * FROM genders")
    suspend fun getAllGenders(): List<GenderEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllGenders(genders: List<GenderEntity>)

}