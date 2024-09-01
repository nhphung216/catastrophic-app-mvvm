package com.phung.catastrophicapp

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.phung.catastrophicapp.data.local.CatImageDatabase
import com.phung.catastrophicapp.data.local.dao.CatImageDao
import com.phung.catastrophicapp.data.local.entities.CatImageEntity
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CatImageDaoTest {

    private lateinit var database: CatImageDatabase

    private lateinit var catImageDao: CatImageDao

    @Before
    fun initDb() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            CatImageDatabase::class.java
        ).allowMainThreadQueries().build()

        catImageDao = database.catImageDao()
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun testGetCatImages() = runBlocking {
        // Given a list of CatImageEntity objects
        val catImages = List(100) { i -> CatImageEntity(id = "$i", url = "https://cdn2.thecatapi.com/images/1ds$i.png") }

        // Insert all images into the database
        catImageDao.insertAll(catImages)

        // When fetching the first 10 cat images with page 0
        val limit = 20
        val page = 0
        val retrievedCatImages = catImageDao.getCatImages(limit = limit, page = page * limit)

        // Then the retrieved list should have 20 items
        Assert.assertEquals(limit, retrievedCatImages.size)
        Assert.assertEquals("0", retrievedCatImages.first().id)
        Assert.assertEquals("19", retrievedCatImages.last().id)

        // When fetching the next 20 cat images with page 1
        val nextPage = 1
        val nextRetrievedCatImages = catImageDao.getCatImages(limit = limit, page = nextPage * limit)

        // Then the retrieved list should have 20 items, starting with IDs from 20 to 39.
        Assert.assertEquals(limit, nextRetrievedCatImages.size)
        Assert.assertEquals("20", nextRetrievedCatImages.first().id)
        Assert.assertEquals("39", nextRetrievedCatImages.last().id)
    }
}