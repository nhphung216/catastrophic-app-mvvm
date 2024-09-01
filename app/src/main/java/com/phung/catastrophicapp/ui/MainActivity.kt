package com.phung.catastrophicapp.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.phung.catastrophicapp.R
import com.phung.catastrophicapp.databinding.ActivityMainBinding
import com.phung.catastrophicapp.utils.IntentKey
import com.phung.catastrophicapp.viewmodel.CatViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: CatViewModel by viewModel()

    private lateinit var adapter: CatImageAdapter

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        adapter = CatImageAdapter { openImageDetail(it) }

        // Set up RecyclerView with GridLayoutManager
        binding.catRecyclerView.layoutManager = GridLayoutManager(this, 3)
        binding.catRecyclerView.adapter = adapter

        // Observe the cat images from the ViewModel
        observeDatas()

        // Load initial data
        viewModel.loadCatImages(viewModel.currentPage)

        // Set up scrolling for load more
        setUpScrollListener(binding.catRecyclerView)

        // Set up SwipeRefreshLayout
        binding.swipeRefreshLayout.setOnRefreshListener {

            // Reload data when pull-to-refresh is triggered
            viewModel.refreshData()
        }
    }

    private fun openImageDetail(imageUrl: String) {
        val intent = Intent(this, ImageDetailActivity::class.java)
        intent.putExtra(IntentKey.IMAGE_URL, imageUrl)
        startActivity(intent)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }

    private fun observeDatas() {
        viewModel.catImages.observe(this) { catImages ->
            // Stop the refreshing animation once data is loaded
            binding.swipeRefreshLayout.isRefreshing = false

            // If the user pulls the refresh successfully, clear the data list.
            if (viewModel.isRefreshData) {
                viewModel.isRefreshData = false
                adapter.setCatImages(catImages)
            } else {
                adapter.addCatImages(catImages)
            }
        }

        viewModel.isLoadingMore.observe(this) {
            binding.loadingProgressBar.visibility = if (it) View.VISIBLE else View.GONE
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