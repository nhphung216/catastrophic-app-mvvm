package com.phung.catastrophicapp.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.phung.catastrophicapp.domain.model.CatImage

@Dao
interface CatImageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(catImages: List<CatImage>)

    @Query("SELECT * FROM cat_images")
    fun getAllCatImages(): PagingSource<Int, CatImage>
}