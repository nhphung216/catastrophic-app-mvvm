package com.phung.catastrophicapp

import com.phung.catastrophicapp.data.local.CatImageDao
import com.phung.catastrophicapp.data.remote.ApiService
import com.phung.catastrophicapp.data.repository.CatRepositoryImpl
import com.phung.catastrophicapp.domain.model.CatImage
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class CatRepositoryTest {

    private lateinit var apiService: ApiService

    private lateinit var catImageDao: CatImageDao

    private lateinit var catRepository: CatRepositoryImpl

    @Before
    fun setup() {
        apiService = Mockito.mock(ApiService::class.java)
        catImageDao = Mockito.mock(CatImageDao::class.java)
        catRepository = CatRepositoryImpl(apiService, catImageDao)
    }

    @Test
    fun testFetchCatImagesFromApi() = runBlocking {
        // mock data
        val catImages = listOf<CatImage>()

        // call api
        catRepository.fetchCatImages(1)
    }
}