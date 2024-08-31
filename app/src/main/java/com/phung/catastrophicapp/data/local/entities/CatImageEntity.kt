package com.phung.catastrophicapp.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.phung.catastrophicapp.domain.model.CatImage

@Entity(tableName = "cat_images")
data class CatImageEntity(
    @PrimaryKey val id: String,
    val url: String
) {
    fun toDomain(): CatImage {
        return CatImage(id = id, url = url)
    }
}