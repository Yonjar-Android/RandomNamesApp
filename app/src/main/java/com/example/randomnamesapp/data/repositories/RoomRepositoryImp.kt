package com.example.randomnamesapp.data.repositories

import android.content.Context
import com.example.randomnamesapp.data.database.daos.GenderDao
import com.example.randomnamesapp.data.database.daos.NameDao
import com.example.randomnamesapp.data.database.daos.OriginDao
import com.example.randomnamesapp.data.database.entities.GenderEntity
import com.example.randomnamesapp.data.database.entities.OriginEntity

class RoomRepositoryImp(
    private val nameDao: NameDao,
    private val genderDao: GenderDao,
    private val originDao: OriginDao): RoomRepository {

    override suspend fun getRandomName(): String {
        val response = nameDao.getRandomName()
        println(response)
        return response[0].name
    }

    override suspend fun getRandomNameByCategories(
        gender: String,
        categories: List<String>
    ): String {
        return ""
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