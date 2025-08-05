package com.example.randomnamesapp.data.repositories

import com.example.randomnamesapp.data.database.entities.NameEntity

interface RoomRepository {

    suspend fun insertName(name: NameEntity)

    suspend fun preloadNames(names: List<NameEntity>)

    suspend fun getRandomName(): String

    suspend fun getRandomNameByCategories(gender: String, categories: List<String>): String

    suspend fun preloadGenders()

    suspend fun getGenders()

    suspend fun getOrigins()
    suspend fun preloadOrigins()

}