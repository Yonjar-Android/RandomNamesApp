package com.example.randomnamesapp.data.database.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "names",
    foreignKeys = [
        ForeignKey(
            entity = GenderEntity::class,
            parentColumns = ["id"],
            childColumns = ["genderId"],
            onDelete = ForeignKey.NO_ACTION,
            onUpdate = ForeignKey.NO_ACTION
        ),
        ForeignKey(
            entity = OriginEntity::class,
            parentColumns = ["id"],
            childColumns = ["originId"],
            onDelete = ForeignKey.NO_ACTION,
            onUpdate = ForeignKey.NO_ACTION
        )
    ])
data class NameEntity(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val name: String,
    val genderId: Int,
    val originId: Int
)

@Entity(tableName = "genders")
data class GenderEntity(
    @PrimaryKey val id: Int? = null,
    val label: String // Ej: Masculine, Feminine, Either, Ambiguous
)

@Entity(tableName = "origins")
data class OriginEntity(
    @PrimaryKey val id: Int? = null,
    val name: String // Ej: Russian, English, Arabic, etc.
)