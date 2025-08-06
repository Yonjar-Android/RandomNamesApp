package com.example.randomnamesapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.randomnamesapp.data.database.entities.GenderEntity
import com.example.randomnamesapp.data.database.entities.OriginEntity
import com.example.randomnamesapp.data.repositories.RoomRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val roomRepository: RoomRepository
): ViewModel() {

    private val _name = MutableStateFlow<String>("Random Name")
    val name = _name.asStateFlow()

    private val _origins = MutableStateFlow<List<OriginEntity>>(emptyList())
    val origins = _origins.asStateFlow()

    private val _genders = MutableStateFlow<List<GenderEntity>>(emptyList())
    val genders = _genders.asStateFlow()

    init {
        viewModelScope.launch {
            _genders.value = roomRepository.getGenders()
            _origins.value = roomRepository.getOrigins()
        }
    }


    fun getRandomName(){
        viewModelScope.launch {
           _name.value = roomRepository.getRandomName()
        }
    }
}