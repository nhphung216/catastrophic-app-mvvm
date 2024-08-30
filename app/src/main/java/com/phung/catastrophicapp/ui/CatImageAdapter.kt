package com.phung.catastrophicapp.ui

import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.phung.catastrophicapp.R
import com.phung.catastrophicapp.databinding.ViewItemCatImageBinding
import com.phung.catastrophicapp.domain.model.CatImage

class CatImageAdapter(private val onItemClick: (String) -> Unit) :
    RecyclerView.Adapter<CatImageAdapter.CatImageViewHolder>() {

    private val catImages = mutableListOf<CatImage>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatImageViewHolder {
        val binding =
            ViewItemCatImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CatImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CatImageViewHolder, position: Int) {
        holder.bind(catImages[position])

        // Set click listener to open ImageDetailActivity with the selected image URL
        holder.itemView.setOnClickListener {
            onItemClick(catImages[position].url)
        }
    }

    override fun getItemCount(): Int {
        return catImages.size
    }

    fun addCatImages(newImages: List<CatImage>) {
        val startPosition = catImages.size
        catImages.addAll(newImages)
        notifyItemRangeInserted(startPosition, newImages.size)
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
                .placeholder(R.drawable.cat_paw)
                .into(binding.imageView)
        }
    }
}