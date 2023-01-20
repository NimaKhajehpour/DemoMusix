package com.nima.demomusix.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun RoundedThumbnail(
    modifier: Modifier = Modifier,
    imageUrl: String,
    showLoading: Boolean = true
    ) {
    var loading by remember {
        mutableStateOf(true)
    }

    Box(
        modifier = modifier
    ){
        AsyncImage(model = imageUrl,
            contentDescription = null,
            onLoading = {
                loading = true
            },
            onSuccess = {
                loading = false
            },
            contentScale = ContentScale.Fit,
            modifier = Modifier.align(Alignment.Center)
                .clip(RoundedCornerShape(
                    topEndPercent = 50, bottomEndPercent = 50,
                    bottomStartPercent = 50, topStartPercent = 50))
        )
        AnimatedVisibility(visible = loading && showLoading) {
            CircularProgressIndicator()
        }
    }
}