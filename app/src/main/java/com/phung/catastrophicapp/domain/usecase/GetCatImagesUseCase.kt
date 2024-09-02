package com.phung.catastrophicapp.domain.usecase

import com.phung.catastrophicapp.domain.model.CatImage
import com.phung.catastrophicapp.domain.repository.CatRepository

class GetCatImagesUseCase(private val catRepository: CatRepository) {

    suspend fun execute(limit: Int, page: Int): List<CatImage> =
        catRepository.getCatImages(limit, page)
}