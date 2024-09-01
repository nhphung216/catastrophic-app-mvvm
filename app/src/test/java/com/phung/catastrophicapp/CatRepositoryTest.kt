package com.phung.catastrophicapp

import android.content.Context
import com.phung.catastrophicapp.data.local.dao.CatImageDao
import com.phung.catastrophicapp.data.local.entities.CatImageEntity
import com.phung.catastrophicapp.data.network.ApiService
import com.phung.catastrophicapp.data.repository.CatRepositoryImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class CatRepositoryTest {

    private lateinit var catRepository: CatRepositoryImpl

    @Mock
    private lateinit var apiService: ApiService

    @Mock
    private lateinit var catImageDao: CatImageDao

    @Mock
    private lateinit var context: Context

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        catRepository = CatRepositoryImpl(apiService, catImageDao, context)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `test getCatImages success`() = runTest {
        val limit = 20
        val page = 0
        // Given
        val catImages = List(20) { i ->
            CatImageEntity(
                id = "$i",
                url = "https://cdn2.thecatapi.com/images/1ds$i.png"
            )
        }
        Mockito.`when`(catImageDao.getCatImages(limit = limit, page = page * limit))
            .thenReturn(catImages)

        // When
        val result = catRepository.getCatImages(limit, page)

        // Then
        Mockito.verify(catImageDao).getCatImages(limit, page)
        assertEquals(catImages, result.map { it.toEntity() })
    }
}
