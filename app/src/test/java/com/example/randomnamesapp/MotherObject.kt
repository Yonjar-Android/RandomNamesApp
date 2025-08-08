package com.example.randomnamesapp

import com.example.randomnamesapp.data.database.entities.GenderEntity
import com.example.randomnamesapp.data.database.entities.OriginEntity

object MotherObject {

    val genders = listOf(
        GenderEntity(1, "Masculine"),
        GenderEntity(2, "Femenine"),
        GenderEntity(3, "Either"))

    val origins = listOf(
        OriginEntity(1, "Italian"),
        OriginEntity(2, "Spanish"),
        OriginEntity(3, "German"))
}