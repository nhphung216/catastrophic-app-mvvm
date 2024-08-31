package com.phung.catastrophicapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.phung.catastrophicapp.data.local.dao.CatImageDao
import com.phung.catastrophicapp.data.local.entities.CatImageEntity

@Database(entities = [CatImageEntity::class], version = 1, exportSchema = false)
abstract class CatImageDatabase : RoomDatabase() {
    abstract fun catImageDao(): CatImageDao
}