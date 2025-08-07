package com.example.randomnamesapp.data.repositories

import com.example.randomnamesapp.data.database.entities.GenderEntity
import com.example.randomnamesapp.data.database.entities.OriginEntity

interface RoomRepository {

    suspend fun getRandomName(gender: Int, origins: List<Int>): String

    suspend fun getGenders(): List<GenderEntity>

    suspend fun getOrigins(): List<OriginEntity>

}