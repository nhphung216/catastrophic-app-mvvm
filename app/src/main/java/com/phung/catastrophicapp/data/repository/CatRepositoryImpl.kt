package com.phung.catastrophicapp.data.repository

import androidx.paging.PagingData
import com.phung.catastrophicapp.data.local.CatImageDao
import com.phung.catastrophicapp.data.remote.ApiService
import com.phung.catastrophicapp.domain.model.CatImage
import com.phung.catastrophicapp.domain.repository.CatRepository
import kotlinx.coroutines.flow.Flow

class CatRepositoryImpl(private val apiService: ApiService, private val catImageDao: CatImageDao) :
    CatRepository {

    override fun getCatImages(): Flow<PagingData<CatImage>> {
        TODO("Not yet implemented")
    }

    override suspend fun fetchCatImages(page: Int) {
        val images = apiService.getCatImages(20, page)
        catImageDao.insertAll(images)
    }
}