package com.phung.catastrophicapp.domain.repository

import com.phung.catastrophicapp.domain.model.CatImage

interface CatRepository {

    suspend fun getCatImages(limit: Int, page: Int): List<CatImage>
}