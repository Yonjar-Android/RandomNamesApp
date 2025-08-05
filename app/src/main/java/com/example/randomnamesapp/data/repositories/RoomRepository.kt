package com.example.randomnamesapp.data.repositories

import com.example.randomnamesapp.data.database.entities.NameEntity

interface RoomRepository {

    suspend fun insertName(name: NameEntity)

    suspend fun getRandomName(): String

    suspend fun getRandomNameByCategories(gender: String, categories: List<String>): String
}