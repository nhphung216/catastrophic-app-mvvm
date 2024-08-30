package com.phung.catastrophicapp.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.phung.catastrophicapp.domain.model.CatImage
import com.phung.catastrophicapp.domain.repository.CatRepository
import kotlinx.coroutines.launch

class CatViewModel(private val repository: CatRepository) : ViewModel() {

    var currentPage = 1

    private var limit = 20

    var isLoading = false

    var catImages: MutableLiveData<ArrayList<CatImage>> = MutableLiveData()

    fun loadMoreItems() {
        isLoading = true
        currentPage += 1
        loadCatImages(currentPage)
    }

    fun loadCatImages(page: Int) {
        // Launch coroutine to fetch data
        viewModelScope.launch {
            try {
                // Call API to fetch cat images
                val images = repository.getCatImages(limit, page)
                Log.e("AAAA", "images: ${images.size}")
                catImages.value = images
                isLoading = false
            } catch (e: Exception) {
                e.printStackTrace()
                isLoading = false
            }
        }
    }
}