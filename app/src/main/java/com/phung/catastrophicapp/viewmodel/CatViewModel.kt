package com.phung.catastrophicapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.phung.catastrophicapp.domain.model.CatImage
import com.phung.catastrophicapp.domain.usecase.GetCatImagesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CatViewModel(private val getCatImagesUseCase: GetCatImagesUseCase) : ViewModel() {

    private var limit = 20 // limit each page to 20 images

    var currentPage = 1 // default page

    var isRefreshData = false

    // detect show/hide loading view
    var isLoading: MutableLiveData<Boolean> = MutableLiveData()

    var error: MutableLiveData<String> = MutableLiveData()

    private val _catImages: MutableLiveData<List<CatImage>> = MutableLiveData()
    val catImages: LiveData<List<CatImage>> = _catImages

    fun loadMoreItems() {
        currentPage += 1
        loadCatImages(currentPage)
    }

    fun refreshData() {
        isRefreshData = true
        currentPage = 1
        loadCatImages(currentPage)
    }

    fun loadCatImages(page: Int) {
        isLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val images = getCatImagesUseCase.execute(limit, page)

                withContext(Dispatchers.Main) {
                    _catImages.value = images.toMutableList()
                    isLoading.value = false
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    isLoading.value = false
                    error.value = e.message
                }
            }
        }
    }
}