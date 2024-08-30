package com.phung.catastrophicapp.domain.repository

import androidx.paging.PagingData
import com.phung.catastrophicapp.domain.model.CatImage
import kotlinx.coroutines.flow.Flow

interface CatRepository {
    fun getCatImages(): Flow<PagingData<CatImage>>
    suspend fun fetchCatImages(page: Int)
}