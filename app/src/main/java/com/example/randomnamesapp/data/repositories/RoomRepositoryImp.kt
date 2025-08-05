package com.example.randomnamesapp.data.repositories

import android.content.Context
import com.example.randomnamesapp.data.database.daos.GenderDao
import com.example.randomnamesapp.data.database.daos.NameDao
import com.example.randomnamesapp.data.database.daos.OriginDao
import com.example.randomnamesapp.data.database.entities.GenderEntity
import com.example.randomnamesapp.data.database.entities.NameEntity
import com.example.randomnamesapp.data.database.entities.OriginEntity
import com.example.randomnamesapp.utils.CsvReader

class RoomRepositoryImp(
    private val nameDao: NameDao,
    private val genderDao: GenderDao,
    private val originDao: OriginDao,
    private val context: Context
): RoomRepository {
    override suspend fun insertName(name: NameEntity) {
        nameDao.insertName(name)
    }

    override suspend fun preloadNames(names: List<NameEntity>) {
        val namess = CsvReader.readCsvFromAssets(context = context, "names.csv") { tokens ->
            NameEntity(
                name = tokens[1],
                genderId = tokens[2].toInt(),
                originId = tokens[3].toInt()
            )
        }
        nameDao.insertAllNames(namess)
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

    override suspend fun preloadGenders() {
        val genders = CsvReader.readCsvFromAssets(context = context, "genders.csv") { tokens ->
            GenderEntity(
                id = tokens[0].toInt(),
                label = tokens[1]
            )
        }

        genderDao.insertAllGenders(genders)
    }

    override suspend fun getGenders() {
        val response = genderDao.getAllGenders()
        println(response)
    }

    override suspend fun getOrigins() {
        val response = originDao.getAllOrigins()
        println(response)
    }

    override suspend fun preloadOrigins() {
        val origins = CsvReader.readCsvFromAssets(context = context, "origins.csv") { tokens ->
            OriginEntity(
                id = tokens[0].toInt(),
                name = tokens[1]
            )
        }

        originDao.insertAllOrigins(origins)
    }
}