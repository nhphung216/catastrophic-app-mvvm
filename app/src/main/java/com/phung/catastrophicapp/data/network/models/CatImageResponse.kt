package com.phung.catastrophicapp.data.network.models

import com.phung.catastrophicapp.data.local.entities.CatImageEntity
import com.phung.catastrophicapp.domain.model.CatImage

data class CatImageResponse(
    val id: String,
    val url: String
) {
    fun toEntity(): CatImageEntity {
        return CatImageEntity(id = id, url = url)
    }
    fun toDomain(): CatImage {
        return CatImage(id = id, url = url)
    }
}