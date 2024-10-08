package com.phung.catastrophicapp.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.phung.catastrophicapp.R
import com.phung.catastrophicapp.databinding.ActivityMainBinding
import com.phung.catastrophicapp.utils.IntentKey
import com.phung.catastrophicapp.utils.showAlertDialog
import com.phung.catastrophicapp.viewmodel.CatViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: CatViewModel by viewModel()

    private lateinit var adapter: CatImageAdapter

    val readOnlySet: Set<String> = setOf("Alice", "Bob", "Bob", "Charlie")

    val array = arrayOf(1, 2, 3)
    val arrayList = arrayListOf(1, 2, 3)

    val list = listOf(1, 2, 3)
    val mutableList = mutableListOf(1, 2, 3)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        setupRecyclerView()

        // Observe the cat images from the ViewModel
        observeViewModel()

        // Load initial data
        viewModel.loadCatImages(viewModel.currentPage)

        // Set up scrolling for load more
        setUpScrollListener(binding.catRecyclerView)

        // Set up SwipeRefreshLayout
        binding.swipeRefreshLayout.setOnRefreshListener {

            // Reload data when pull-to-refresh is triggered
            binding.swipeRefreshLayout.isRefreshing = false
            viewModel.refreshData()
        }
    }

    private fun setupRecyclerView() {
        // Set up adapter
        adapter = CatImageAdapter { openImageDetail(it) }

        // Set up RecyclerView with GridLayoutManager
        binding.catRecyclerView.layoutManager = GridLayoutManager(this, 3)
        binding.catRecyclerView.adapter = adapter
    }

    private fun observeViewModel() {
        viewModel.catImages.observe(this) { catImages ->
            // Stop the refreshing animation once data is loaded
            binding.swipeRefreshLayout.isRefreshing = false
            if (adapter.isFirstLoad) {
                adapter.isFirstLoad = false
                adapter.setCatImages(catImages)
            } else if (viewModel.isRefreshData) {
                viewModel.isRefreshData = false
                adapter.setCatImages(catImages)
            } else {
                adapter.addCatImages(catImages)
            }
        }

        viewModel.isLoading.observe(this) { isLoading ->
            binding.loadingProgressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        viewModel.error.observe(this) {
            showAlertDialog(this, it)
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
                if (viewModel.isLoading.value == false
                    && visibleItemCount + firstVisibleItemPosition >= totalItemCount
                    && firstVisibleItemPosition >= 0
                ) {
                    viewModel.loadMoreItems()
                }
            }
        })
    }

    private fun openImageDetail(imageUrl: String) {
        val intent = Intent(this, ImageDetailActivity::class.java)
        intent.putExtra(IntentKey.IMAGE_URL, imageUrl)
        startActivity(intent)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }
}