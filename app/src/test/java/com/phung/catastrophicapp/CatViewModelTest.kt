package com.phung.catastrophicapp

import com.phung.catastrophicapp.domain.repository.CatRepository
import com.phung.catastrophicapp.viewmodel.CatViewModel
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class CatViewModelTest {

    private lateinit var catRepository: CatRepository

    private lateinit var catViewModel: CatViewModel

    @Before
    fun setup() {
        catRepository = Mockito.mock(CatRepository::class.java)
        catViewModel = CatViewModel(catRepository)
    }

    @Test
    fun `should fetch cat images from repository`(): Unit = runBlocking {

    }
}