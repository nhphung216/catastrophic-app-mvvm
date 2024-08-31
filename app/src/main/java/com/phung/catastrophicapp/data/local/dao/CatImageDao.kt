package com.phung.catastrophicapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.phung.catastrophicapp.data.local.entities.CatImageEntity

@Dao
interface CatImageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(catImages: List<CatImageEntity>)

    @Query("SELECT * FROM cat_images LIMIT :limit OFFSET :page")
    suspend fun getAllCatImages(limit: Int, page: Int): List<CatImageEntity>

    @Query("DELETE FROM cat_images")
    suspend fun clearAll()
}