package com.phung.catastrophicapp.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.phung.catastrophicapp.databinding.ActivityMainBinding
import com.phung.catastrophicapp.viewmodel.CatViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: CatViewModel by viewModel()

    private lateinit var adapter: CatImageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        adapter = CatImageAdapter { imageUrl ->
            val intent = Intent(this, ImageDetailActivity::class.java)
            intent.putExtra("image_url", imageUrl)
            startActivity(intent)
        }

        // Set up RecyclerView with GridLayoutManager
        binding.catRecyclerView.layoutManager = GridLayoutManager(this, 3)
        binding.catRecyclerView.adapter = adapter

        // Observe the cat images from the ViewModel
        observeCatImages()

        // Load initial data
        viewModel.loadCatImages(viewModel.currentPage)

        // Set up scrolling for load more
        setUpScrollListener(binding.catRecyclerView)
    }

    private fun observeCatImages() {
        viewModel.catImages.observe(this) { catImages ->
            adapter.addCatImages(catImages)
        }
    }

    private fun setUpScrollListener(recyclerView: RecyclerView) {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as GridLayoutManager
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                // Trigger load more when reaching the end of the list
                if (!viewModel.isLoading && visibleItemCount + firstVisibleItemPosition >= totalItemCount
                    && firstVisibleItemPosition >= 0
                ) {
                    viewModel.loadMoreItems()
                }
            }
        })
    }
}