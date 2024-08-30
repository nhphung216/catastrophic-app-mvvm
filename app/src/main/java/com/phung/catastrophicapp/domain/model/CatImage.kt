package com.phung.catastrophicapp.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cat_images")
data class CatImage(
    @PrimaryKey val id: String,
    val url: String
)