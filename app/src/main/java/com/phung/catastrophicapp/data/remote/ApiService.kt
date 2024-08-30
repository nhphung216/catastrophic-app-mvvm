package com.phung.catastrophicapp.data.remote

import com.phung.catastrophicapp.domain.model.CatImage
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("v1/images/search")
    suspend fun getCatImages(
        @Query("limit") limit: Int,
        @Query("page") page: Int,
        @Query("mime_types") mimeTypes: String = "png"
    ): ArrayList<CatImage>
}