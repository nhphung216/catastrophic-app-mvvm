package com.phung.catastrophicapp.data.repository

import com.phung.catastrophicapp.data.remote.ApiService
import com.phung.catastrophicapp.domain.model.CatImage
import com.phung.catastrophicapp.domain.repository.CatRepository

class CatRepositoryImpl(private val apiService: ApiService) : CatRepository {

    // Method to fetch data from API
    override suspend fun getCatImages(limit: Int, page: Int): ArrayList<CatImage> {
        return apiService.getCatImages(20, page)
    }
}