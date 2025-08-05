package com.example.randomnamesapp.data.repositories

import com.example.randomnamesapp.data.database.daos.NameDao
import com.example.randomnamesapp.data.database.entities.NameEntity

class RoomRepositoryImp(
    private val nameDao: NameDao
): RoomRepository {
    override suspend fun insertName(name: NameEntity) {
        nameDao.insertName(name)
    }

    override suspend fun getRandomName(): String {
        val response = nameDao.getRandomName()
        return response.name
    }

    override suspend fun getRandomNameByCategories(
        gender: String,
        categories: List<String>
    ): String {
        return ""
    }
}