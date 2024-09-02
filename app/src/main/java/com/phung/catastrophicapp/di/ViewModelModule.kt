package com.phung.catastrophicapp.di

import com.phung.catastrophicapp.viewmodel.CatViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { CatViewModel(get()) }
}