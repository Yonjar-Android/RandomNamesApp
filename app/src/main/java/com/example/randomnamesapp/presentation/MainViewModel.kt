package com.example.randomnamesapp.presentation

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.randomnamesapp.R
import com.example.randomnamesapp.data.database.entities.GenderEntity
import com.example.randomnamesapp.data.database.entities.OriginEntity
import com.example.randomnamesapp.data.repositories.RoomRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val roomRepository: RoomRepository): ViewModel() {

    private val _name = MutableStateFlow("Random Name")
    val name = _name.asStateFlow()

    private val _origins = MutableStateFlow<List<OriginEntity>>(emptyList())
    val origins = _origins.asStateFlow()

    private val _genders = MutableStateFlow<List<GenderEntity>>(emptyList())
    val genders = _genders.asStateFlow()

    private val _message = MutableStateFlow("")
    val message = _message.asStateFlow()

    init {
        viewModelScope.launch {
            _genders.value = roomRepository.getGenders()
            _origins.value = roomRepository.getOrigins()
        }
    }


    fun getRandomName(genderId: Int, origins: List<Int>, context: Context){
        viewModelScope.launch {
            val response = roomRepository.getRandomName(genderId, origins)
            if (response.contains("Error")){
                _message.value = context.getString(R.string.noNamesStr)
            } else{
                _name.value = response
            }
        }
    }

    fun clearMessage(){
        _message.value = ""
    }
}