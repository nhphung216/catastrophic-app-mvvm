package com.phung.catastrophicapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.phung.catastrophicapp.databinding.ActivityImageDetailBinding

class ImageDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityImageDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImageDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get the image URL passed from the MainActivity
        val imageUrl = intent.getStringExtra("image_url")

        // Load the image into the PhotoView for zoom functionality
        Glide.with(this)
            .load(imageUrl)
            .into(binding.photoView)
    }
}