package com.phung.catastrophicapp.di

import com.phung.catastrophicapp.domain.repository.CatRepository
import com.phung.catastrophicapp.viewmodel.CatViewModel
import org.junit.Rule
import org.junit.Test
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.get
import org.koin.test.mock.MockProviderRule
import org.koin.test.mock.declareMock
import org.mockito.Mockito
import kotlin.test.assertNotNull

class AppModuleTest : KoinTest {

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        modules(appModule)
    }

    @get:Rule
    val mockProvider = MockProviderRule.create { clazz ->
        Mockito.mock(clazz.java)
    }

    @Test
    fun testRepositoryInjection() {
        // Replace current definition by a Mock
        val mockRepository = declareMock<CatRepository>()

        val repository = get<CatRepository>()
        assertNotNull(repository)
    }

    @Test
    fun testViewModelInjection() {
        // Replace current definition by a Mock
        val mockViewModel = declareMock<CatViewModel>()

        val viewModel = get<CatViewModel>()
        assertNotNull(viewModel)
    }
}