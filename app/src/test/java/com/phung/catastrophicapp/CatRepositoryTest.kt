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
    fun testFetchCatImages() = runBlocking {
        // mock list data
        val catImages = listOf(
            CatImage(id = "1ds", url = "https://cdn2.thecatapi.com/images/1ds.png")
        )
        Mockito.`when`(apiService.getCatImages(20, 1)).thenReturn(catImages)

        // call api
        catRepository.fetchCatImages(1)

        // verify data is inserted into room database
        Mockito.verify(catImageDao).insertAll(catImages)
    }
}
