package com.phung.catastrophicapp.data.local

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.phung.catastrophicapp.data.local.dao.CatImageDao
import com.phung.catastrophicapp.data.local.entities.CatImageEntity
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CatImageDatabaseTest {

    private lateinit var catImageDatabase: CatImageDatabase

    private lateinit var catImageDao: CatImageDao

    @Before
    fun initDb() {
        catImageDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            CatImageDatabase::class.java
        ).allowMainThreadQueries().build()

        catImageDao = catImageDatabase.catImageDao()
    }

    @After
    fun closeDb() {
        catImageDatabase.close()
    }

    @Test
    fun testGetCatImages() = runTest {
        // Given a list of CatImageEntity objects
        val catImages = List(100) { i -> CatImageEntity(id = "$i", url = "https://cdn2.thecatapi.com/images/1ds$i.png") }

        // Insert all images into the database
        catImageDao.insertAll(catImages)

        // When fetching the first 10 cat images with page 0
        val limit = 20
        val page = 0
        val retrievedCatImages = catImageDao.getCatImages(limit = limit, page = page * limit)

        // Then the retrieved list should have 20 items
        assertEquals(limit, retrievedCatImages.size)
        assertEquals("0", retrievedCatImages.first().id)
        assertEquals("19", retrievedCatImages.last().id)

        // When fetching the next 20 cat images with page 1
        val nextPage = 1
        val nextRetrievedCatImages = catImageDao.getCatImages(limit = limit, page = nextPage * limit)

        // Then the retrieved list should have 20 items, starting with IDs from 20 to 39.
        assertEquals(limit, nextRetrievedCatImages.size)
        assertEquals("20", nextRetrievedCatImages.first().id)
        assertEquals("39", nextRetrievedCatImages.last().id)
    }
}