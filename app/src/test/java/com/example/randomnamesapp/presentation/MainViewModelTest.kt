package com.example.randomnamesapp.presentation

import app.cash.turbine.turbineScope
import com.example.randomnamesapp.MotherObject
import com.example.randomnamesapp.data.repositories.RoomRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest {

    @MockK
    lateinit var roomRepository: RoomRepository

    lateinit var mainViewModel: MainViewModel

    val dispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown(){
        Dispatchers.resetMain()
    }

    @Test
    fun `getRandomName should change name state when success`() = runTest {
        mainViewModel = MainViewModel(roomRepository)

        //Given
        coEvery { roomRepository.getRandomName(1, listOf(1, 2)) } returns "Juan"
        coEvery { roomRepository.getGenders() } returns MotherObject.genders
        coEvery { roomRepository.getOrigins() } returns MotherObject.origins

        turbineScope {
            val turbineName = mainViewModel.name.testIn(backgroundScope)
            val turbineGenders = mainViewModel.genders.testIn(backgroundScope)
            val turbineOrigins = mainViewModel.origins.testIn(backgroundScope)

            assertEquals(turbineName.awaitItem(),"Random Name")
            assertEquals(turbineGenders.awaitItem(),emptyList())
            assertEquals(turbineOrigins.awaitItem(),emptyList())

            assertEquals(turbineGenders.awaitItem().size, MotherObject.genders.size)
            assertEquals(turbineOrigins.awaitItem().size, MotherObject.origins.size)

            mainViewModel.getRandomName(1, listOf(1, 2))

            assertEquals(turbineName.awaitItem(),"Juan")

            turbineName.cancel()
            turbineGenders.cancel()
            turbineOrigins.cancel()

        }
    }
}