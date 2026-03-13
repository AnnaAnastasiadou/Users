package com.example.userlist.features

import android.view.View
import androidx.core.view.isVisible
import com.example.userlist.databinding.StatesBinding

fun StatesBinding.renderState(
    isLoading: Boolean,
    error: String?,
    contentView: View,
) {
    progressBar.isVisible = isLoading
    errorLayout.isVisible = error != null
    tvErrorMessage.text = error ?: ""

    contentView.isVisible = error==null && !isLoading
}