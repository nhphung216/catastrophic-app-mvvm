package com.phung.catastrophicapp.data.repository

import android.content.Context
import com.phung.catastrophicapp.data.local.dao.CatImageDao
import com.phung.catastrophicapp.data.network.ApiService
import com.phung.catastrophicapp.domain.model.CatImage
import com.phung.catastrophicapp.domain.repository.CatRepository
import com.phung.catastrophicapp.utils.isInternetAvailable

class CatRepositoryImpl(
    private val apiService: ApiService,
    private val catImageDao: CatImageDao,
    private val context: Context
) : CatRepository {

    // Method to fetch data from API and local
    override suspend fun getCatImages(limit: Int, page: Int): List<CatImage> {
        return if (isInternetAvailable(context)) {
            try {
                val catImageResponseList = apiService.getCatImages(limit, page)

                // save data to room
                catImageDao.insertAll(catImageResponseList.map { it.toEntity() })

                // return data display UI
                catImageResponseList.map { it.toDomain() }
            } catch (e: Exception) {
                // get data from local
                catImageDao.getAllCatImages(limit, page * limit).map { it.toDomain() }
            }
        } else {
            // get data from local
            catImageDao.getAllCatImages(limit, page * limit).map { it.toDomain() }
        }
    }
}