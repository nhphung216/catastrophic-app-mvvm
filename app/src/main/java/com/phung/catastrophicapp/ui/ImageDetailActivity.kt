package com.phung.catastrophicapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory
import com.phung.catastrophicapp.R
import com.phung.catastrophicapp.databinding.ActivityImageDetailBinding
import com.phung.catastrophicapp.extensions.addOnBackPressedDispatcher
import com.phung.catastrophicapp.utils.IntentKey

class ImageDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityImageDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImageDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get the image URL passed from the MainActivity
        val imageUrl = intent.getStringExtra(IntentKey.IMAGE_URL)

        // Load the image into the PhotoView for zoom functionality
        Glide.with(this)
            .load(imageUrl)
            .transition(
                withCrossFade(
                    DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build()
                )
            )
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(R.drawable.cat_paw)
            .into(binding.photoView)

        addOnBackPressedDispatcher { doFinish() }

        binding.closeBtn.setOnClickListener { doFinish() }
    }

    private fun doFinish() {
        finish()
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }
}