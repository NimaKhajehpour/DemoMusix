package com.nima.demomusix.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nima.demomusix.model.artist.albums.ArtistAlbums
import com.nima.demomusix.model.chart.albums.TopAlbums
import com.nima.demomusix.model.chart.tracks.TopTracks


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RowWithoutText(
    topAlbums: TopAlbums,
    onClick: (Int) -> Unit
) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(8.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        items(topAlbums.data) {
            val albumName = it.title
            val albumId = it.id
            val albumPic = it.cover_big
            Card(
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.padding(end = 10.dp),
                elevation = CardDefaults.cardElevation(5.dp),
                onClick = {
                    onClick(albumId)
                }
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    RowThumbnail(imageUrl = albumPic)
                }
            }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RowWithoutText(
    albums: ArtistAlbums,
    onClick: (Int) -> Unit
) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(8.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        items(albums.data) {
            val albumName = it.title
            val albumId = it.id
            val albumPic = it.cover_big
            Card(
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.padding(end = 10.dp),
                elevation = CardDefaults.cardElevation(5.dp),
                onClick = {
                    onClick(albumId)
                }
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    RowThumbnail(imageUrl = albumPic)
                }
            }

        }
    }
}

@Composable
fun RowWithoutText(topTracks: TopTracks) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(8.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        items(topTracks.data) {
            val trackName = it.title
            val trackId = it.id
            val trackPic = it.album.cover_big
            Card(
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.padding(end = 10.dp),
                elevation = CardDefaults.cardElevation(5.dp),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    RowThumbnail(imageUrl = trackPic)
                }
            }

        }
    }
}