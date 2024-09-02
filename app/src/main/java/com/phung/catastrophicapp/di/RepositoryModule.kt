package com.phung.catastrophicapp.di

import com.phung.catastrophicapp.data.repository.CatRepositoryImpl
import com.phung.catastrophicapp.domain.repository.CatRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<CatRepository> { CatRepositoryImpl(get(), get(), get()) }
}