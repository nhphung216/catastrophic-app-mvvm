package com.phung.catastrophicapp.ui

import android.annotation.SuppressLint
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory
import com.phung.catastrophicapp.R
import com.phung.catastrophicapp.databinding.ShimmerLayoutBinding
import com.phung.catastrophicapp.databinding.ViewItemCatImageBinding
import com.phung.catastrophicapp.domain.model.CatImage

@SuppressLint("NotifyDataSetChanged")
class CatImageAdapter(private val onItemClick: (String) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val catImages = mutableListOf<CatImage>()

    var isFirstLoad = true

    companion object {
        private const val VIEW_TYPE_SHIMMER = 0
        private const val VIEW_TYPE_IMAGE = 1
        private const val SHIMMER_ITEM_COUNT = 20 // Number of shimmer items to display
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_SHIMMER) {
            val binding = ShimmerLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            ShimmerViewHolder(binding)
        } else {
            val binding = ViewItemCatImageBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            CatImageViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is CatImageViewHolder && !isFirstLoad) {
            holder.bind(catImages[position])

            // Set click listener to open ImageDetailActivity with the selected image URL
            holder.itemView.setOnClickListener { onItemClick(catImages[position].url) }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (isFirstLoad) VIEW_TYPE_SHIMMER else VIEW_TYPE_IMAGE
    }

    override fun getItemCount(): Int {
        return if (isFirstLoad) SHIMMER_ITEM_COUNT else catImages.size
    }

    fun addCatImages(newImages: List<CatImage>) {
        val startPosition = catImages.size
        catImages.addAll(newImages)
        notifyItemRangeInserted(startPosition, newImages.size)
    }

    fun setCatImages(newImages: List<CatImage>) {
        catImages.clear()
        catImages.addAll(newImages)
        notifyDataSetChanged()
    }

    class CatImageViewHolder(private val binding: ViewItemCatImageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val displayMetrics: DisplayMetrics = binding.root.context.resources.displayMetrics

        private val screenWidth = displayMetrics.widthPixels

        // Adjust according to your number of columns
        private val itemSize = screenWidth / 3

        init {
            // Set the width and height of the ImageView to ensure it's square
            binding.imageView.layoutParams.width = itemSize
            binding.imageView.layoutParams.height = itemSize
        }

        fun bind(catImage: CatImage) {
            Glide.with(binding.imageView.context)
                .load(catImage.url)
                .transition(
                    withCrossFade(
                        DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build()
                    )
                )
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.cat_paw)
                .into(binding.imageView)
        }
    }

    class ShimmerViewHolder(binding: ShimmerLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val displayMetrics: DisplayMetrics = binding.root.context.resources.displayMetrics

        private val screenWidth = displayMetrics.widthPixels

        // Adjust according to your number of columns
        private val itemSize = screenWidth / 3

        init {
            binding.main.layoutParams.width = itemSize
            binding.main.layoutParams.height = itemSize
        }
    }
}