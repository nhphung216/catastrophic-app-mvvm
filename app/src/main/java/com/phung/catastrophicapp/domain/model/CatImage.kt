package com.phung.catastrophicapp.domain.model

import com.phung.catastrophicapp.data.local.entities.CatImageEntity

data class CatImage(
    val id: String,
    val url: String
) {
    fun toEntity(): CatImageEntity {
        return CatImageEntity(id = id, url = url)
    }
}