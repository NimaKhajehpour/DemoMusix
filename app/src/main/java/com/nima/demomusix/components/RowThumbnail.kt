package com.nima.demomusix.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.wrapContentSize
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
fun RowThumbnail(
    imageUrl: String,
    showLoading: Boolean = true,
    modifier: Modifier = Modifier
) {
    var loading by remember {
        mutableStateOf(true)
    }

    Box(
        modifier = modifier.wrapContentSize()
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
                .clip(RoundedCornerShape(5.dp))
        )
        AnimatedVisibility(visible = loading && showLoading) {
            CircularProgressIndicator()
        }
    }
}