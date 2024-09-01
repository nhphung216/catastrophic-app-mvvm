package com.phung.catastrophicapp

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.phung.catastrophicapp.data.local.dao.CatImageDao
import com.phung.catastrophicapp.data.network.ApiService
import com.phung.catastrophicapp.data.network.models.CatImageResponse
import com.phung.catastrophicapp.data.repository.CatRepositoryImpl
import com.phung.catastrophicapp.utils.isInternetAvailable
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CatRepositoryTest {

    private lateinit var catRepository: CatRepositoryImpl

    private lateinit var apiService: ApiService

    private lateinit var catImageDao: CatImageDao

    private lateinit var context: Context

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
        apiService = Mockito.mock(ApiService::class.java)
        catImageDao = Mockito.mock(CatImageDao::class.java)
        catRepository = CatRepositoryImpl(apiService, catImageDao, context)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testFetchCatImages() = runBlocking {
        // given
        Mockito.`when`(isInternetAvailable(context)).thenReturn(true)

        // mock list data
        val catImages = listOf(
            CatImageResponse(id = "1ds", url = "https://cdn2.thecatapi.com/images/1ds.png")
        )
        Mockito.`when`(apiService.getCatImages(20, 1)).thenReturn(catImages)

        // call api
        catRepository.getCatImages(20, 1)

        // verify data is inserted into room database
        Mockito.verify(catImageDao).insertAll(catImages.map { it.toEntity() })
    }
}
