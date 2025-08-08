package com.example.randomnamesapp.data.repositories

import com.example.randomnamesapp.data.database.daos.GenderDao
import com.example.randomnamesapp.data.database.daos.NameDao
import com.example.randomnamesapp.data.database.daos.OriginDao
import com.example.randomnamesapp.data.database.entities.GenderEntity
import com.example.randomnamesapp.data.database.entities.OriginEntity

class RoomRepositoryImp(
    private val nameDao: NameDao,
    private val genderDao: GenderDao,
    private val originDao: OriginDao): RoomRepository {

    override suspend fun getRandomName(gender: Int, origins: List<Int>): String {
        return try {
            val genderIds = when (gender) {
                1 -> listOf(1, 3) // Masculine + Either
                2 -> listOf(2, 3) // Feminine + Either
                3 -> listOf(3)    // Only Either
                4 -> listOf(1, 2, 3) // All
                else -> emptyList()
            }

            val response = nameDao.getRandomName(genderIds, origins)
            response.name
        } catch (e: Exception) {
            "Error: ${e.message}"
        }
    }

    override suspend fun getGenders(): List<GenderEntity> {
        val response = genderDao.getAllGenders()
        return response
    }

    override suspend fun getOrigins(): List<OriginEntity> {
        val response = originDao.getAllOrigins()
        return response
    }

}