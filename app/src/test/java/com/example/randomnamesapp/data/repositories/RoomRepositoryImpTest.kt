package com.example.randomnamesapp.data.repositories

import com.example.randomnamesapp.MotherObject
import com.example.randomnamesapp.data.database.daos.GenderDao
import com.example.randomnamesapp.data.database.daos.NameDao
import com.example.randomnamesapp.data.database.daos.OriginDao
import com.example.randomnamesapp.data.database.entities.NameEntity
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull


class RoomRepositoryImpTest {

    lateinit var roomRepository: RoomRepositoryImp

    @MockK
    lateinit var nameDao: NameDao

    @MockK
    lateinit var genderDao: GenderDao

    @MockK
    lateinit var originDao: OriginDao

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        roomRepository = RoomRepositoryImp(nameDao, genderDao, originDao)
    }

    @Test
    fun `getRandomName returns name when success`() = runBlocking {
        // Given
        coEvery { nameDao.getRandomName(listOf(1, 3), listOf(1, 2)) } returns NameEntity(
            1,
            "Juan",
            1,
            1
        )

        // When

        val response = roomRepository.getRandomName(1, listOf(1, 2))

        //Then
        assertEquals(response, "Juan")
        assertNotNull(response)
        coVerify(exactly = 1) { nameDao.getRandomName(listOf(1, 3), listOf(1, 2)) }
    }

    @Test
    fun `getRandomName returns error message when failure`() = runBlocking {
        //Given
        coEvery {
            nameDao.getRandomName(
                listOf(1, 3),
                listOf(1, 2)
            )
        } throws Exception("No name found")

        //When
        val response = roomRepository.getRandomName(1, listOf(1, 2))

        //Then
        assertEquals(response, "Error: No name found")
        assertNotNull(response)
        coVerify(exactly = 1) { nameDao.getRandomName(listOf(1, 3), listOf(1, 2)) }
    }

    @Test
    fun `getGenders returns genders when success`() = runBlocking {


        // Given
        coEvery { genderDao.getAllGenders() } returns MotherObject.genders

        // When
        val response = roomRepository.getGenders()

        //Then
        assertEquals(response.size, 3)
        assertNotNull(response)
        coVerify(exactly = 1) { genderDao.getAllGenders() }
    }

    @Test
    fun `getOrigins returns genders when success`() = runBlocking {
        // Given

        val originsList = MotherObject.origins

        coEvery { originDao.getAllOrigins() } returns originsList

        // When
        val response = roomRepository.getOrigins()

        //Then
        assertEquals(response.size, originsList.size)
        assertNotNull(response)
        coVerify(exactly = 1) { originDao.getAllOrigins() }
    }

}