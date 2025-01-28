package me.brandonray.fetchapp.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "items")
data class ItemEntity(
    @PrimaryKey val id: Int,
    val listId: Int,
    val name: String?
)