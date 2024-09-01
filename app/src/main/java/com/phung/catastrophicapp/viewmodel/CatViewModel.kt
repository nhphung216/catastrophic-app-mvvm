package com.phung.catastrophicapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.phung.catastrophicapp.domain.model.CatImage
import com.phung.catastrophicapp.domain.repository.CatRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CatViewModel(private val repository: CatRepository) : ViewModel() {

    private var limit = 20

    var currentPage = 1

    var isLoading = false

    var isRefreshData = false

    var catImages: MutableLiveData<MutableList<CatImage>> = MutableLiveData()

    // detect show/hide loading view
    var isLoadingMore: MutableLiveData<Boolean> = MutableLiveData()

    fun loadMoreItems() {
        isLoadingMore.value = true
        isLoading = true
        currentPage += 1
        loadCatImages(currentPage)
    }

    fun refreshData() {
        isRefreshData = true
        currentPage = 1
        loadCatImages(currentPage)
    }

    fun loadCatImages(page: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                // Call API to fetch cat images
                val images = repository.getCatImages(limit, page)

                withContext(Dispatchers.Main) {
                    catImages.value = images.toMutableList()
                    isLoading = false
                    isLoadingMore.value = false
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    isLoading = false
                    isLoadingMore.value = false
                }
            }
        }
    }
}