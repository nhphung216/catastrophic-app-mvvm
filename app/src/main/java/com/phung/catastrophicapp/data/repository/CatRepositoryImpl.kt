package com.phung.catastrophicapp.data.repository

import android.content.Context
import com.phung.catastrophicapp.data.local.CatImageDao
import com.phung.catastrophicapp.data.remote.ApiService
import com.phung.catastrophicapp.domain.model.CatImage
import com.phung.catastrophicapp.domain.repository.CatRepository
import com.phung.catastrophicapp.utils.isInternetAvailable

class CatRepositoryImpl(
    private val apiService: ApiService,
    private val catImageDao: CatImageDao,
    private val context: Context
) : CatRepository {

    // Method to fetch data from API
    override suspend fun getCatImages(limit: Int, page: Int): List<CatImage> {
        return try {
            if (isInternetAvailable(context)) {
                val response = apiService.getCatImages(limit, page)
                // Save to Room
                catImageDao.insertAll(response)
                response
            } else {
                // page * limit = OFFSET
                catImageDao.getAllCatImages(limit, page * limit)
            }
        } catch (e: Exception) {
            listOf()
        }
    }
}