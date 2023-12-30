package com.example.mediclient.ui.home

import androidx.annotation.DrawableRes

data class HomeContent(
    @DrawableRes val profileImage: Int,
    val title: String,
    val writer: String,
)
