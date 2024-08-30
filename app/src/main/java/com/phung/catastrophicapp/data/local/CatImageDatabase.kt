package com.phung.catastrophicapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.phung.catastrophicapp.domain.model.CatImage

@Database(entities = [CatImage::class], version = 1)
abstract class CatImageDatabase : RoomDatabase() {
    abstract fun catImageDao(): CatImageDao
}