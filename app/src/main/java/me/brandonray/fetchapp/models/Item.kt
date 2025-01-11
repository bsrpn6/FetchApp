package me.brandonray.fetchapp.models

import com.squareup.moshi.Json


data class Item(
    val id: Int,
    @Json(name = "listId") val listId: Int,
    @Json(name = "name") val name: String?
)