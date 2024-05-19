package com.halan.ui.extensions

import android.view.View
import android.widget.ImageView
import androidx.annotation.DrawableRes
import coil.load
import coil.request.CachePolicy
import com.halan.core.ui.R

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.GONE
}

fun ImageView.loadImage(imageUrl: String?, @DrawableRes placeholder: Int?) {
    this.load(imageUrl) {
        placeholder(placeholder ?: R.drawable.ic_image_placeholder)
        fallback(R.drawable.image_not_found)
        error(R.drawable.image_not_found)
        diskCachePolicy(CachePolicy.ENABLED)
        memoryCachePolicy(CachePolicy.ENABLED)
    }
}
