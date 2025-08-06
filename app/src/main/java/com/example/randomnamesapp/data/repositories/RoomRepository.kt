package com.example.randomnamesapp.data.repositories

import com.example.randomnamesapp.data.database.entities.GenderEntity
import com.example.randomnamesapp.data.database.entities.OriginEntity

interface RoomRepository {

    suspend fun getRandomName(): String

    suspend fun getRandomNameByCategories(gender: String, categories: List<String>): String


    suspend fun getGenders(): List<GenderEntity>

    suspend fun getOrigins(): List<OriginEntity>

}