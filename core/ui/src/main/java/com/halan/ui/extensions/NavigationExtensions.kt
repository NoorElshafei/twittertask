package com.halan.ui.extensions

import android.content.Intent
import android.net.Uri
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import timber.log.Timber

fun Fragment.navigateSafe(direction: NavDirections) {
    try {
        findNavController().navigateSafe(direction)
    } catch (e: IllegalStateException) {
        Timber.e(e)
    } catch (e: IllegalArgumentException) {
        Timber.e(e)
    }
}

fun NavController.navigateSafe(direction: NavDirections) {
    currentDestination?.getAction(direction.actionId)?.run { navigate(direction) }
}

fun Fragment.openLink(url: String) {
    try {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(browserIntent)
    } catch (e: Exception) {
        Timber.e(e)
    }
}
