package com.example.randomnamesapp.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "names")
data class NameEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val genderId: Int,
    val originId: Int
)

@Entity(tableName = "genders")
data class GenderEntity(
    @PrimaryKey val id: Int,
    val label: String // Ej: Masculine, Feminine, Either, Ambiguous
)

@Entity(tableName = "origins")
data class OriginEntity(
    @PrimaryKey val id: Int,
    val name: String // Ej: Russian, English, Arabic, etc.
)