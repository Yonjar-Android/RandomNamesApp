package com.example.randomnamesapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.randomnamesapp.data.database.entities.NameEntity
import com.example.randomnamesapp.data.repositories.RoomRepository
import kotlinx.coroutines.launch

class MainViewModel(
    private val roomRepository: RoomRepository
): ViewModel() {
    fun insertName(name: NameEntity) {
        viewModelScope.launch {
            roomRepository.insertName(name)
        }
    }
}