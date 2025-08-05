package com.example.randomnamesapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.randomnamesapp.data.database.entities.NameEntity
import com.example.randomnamesapp.data.repositories.RoomRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val roomRepository: RoomRepository
): ViewModel() {

    private val _name = MutableStateFlow<String>("Random Name")
    val name = _name.asStateFlow()

    init {
        preloadInfo()
    }

    fun getRandomName(){
        viewModelScope.launch {
           _name.value = roomRepository.getRandomName()
            println(_name.value)
        }
    }

    private fun preloadInfo() {
        viewModelScope.launch {
            roomRepository.preloadGenders()
            roomRepository.preloadOrigins()
            roomRepository.preloadNames(emptyList())
        }
    }

    fun insertName(name: NameEntity) {
        viewModelScope.launch {
            roomRepository.insertName(name)
        }
    }
}