package com.phung.catastrophicapp.di

import com.phung.catastrophicapp.domain.usecase.GetCatImagesUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single { GetCatImagesUseCase(get()) }
}