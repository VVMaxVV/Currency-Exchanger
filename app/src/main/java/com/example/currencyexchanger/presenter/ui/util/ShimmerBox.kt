package com.example.currencyexchanger.presenter.ui.util

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush

@Composable
fun ShimmerBox(modifier: Modifier) {
    Box(modifier.background(Brush.shimmerBrush()))
}
