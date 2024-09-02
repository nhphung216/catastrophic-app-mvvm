package com.phung.catastrophicapp.di

import androidx.room.Room
import com.phung.catastrophicapp.data.local.CatImageDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {

    // room database
    single {
        Room.databaseBuilder(
            androidContext(),
            CatImageDatabase::class.java,
            "cat_image_database"
        ).fallbackToDestructiveMigration()
            .build()
    }

    // DAO
    single { get<CatImageDatabase>().catImageDao() }
}