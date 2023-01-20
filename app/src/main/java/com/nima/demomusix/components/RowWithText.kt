package com.nima.demomusix.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.nima.demomusix.model.artist.related.RelatedArtists
import com.nima.demomusix.model.chart.artists.TopArtists
import com.nima.demomusix.model.genre.Genres

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RowWithText(
    topArtists: TopArtists,
    onClick: (Int) -> Unit
) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(8.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        items(topArtists.data) {
            val artistName = it.name
            val artistsId = it.id
            val artistPic = it.picture_big
            Card(
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.padding(end = 10.dp),
                elevation = CardDefaults.cardElevation(5.dp),
                onClick = {
                    onClick(artistsId)
                }
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    RowThumbnail(imageUrl = artistPic)
                    Text(
                        text = artistName,
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(vertical = 3.dp),
                        textAlign = TextAlign.Center,
                        softWrap = true


                    )
                }
            }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RowWithText(
    relatedArtists: RelatedArtists,
    onClick: (Int) -> Unit
) {
    LazyRow(
        modifier = Modifier.fillMaxWidth()
            .padding(bottom = 16.dp),
        contentPadding = PaddingValues(8.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        items(relatedArtists.data) {
            val artistName = it.name
            val artistsId = it.id
            val artistPic = it.picture_big
            Column(
                modifier = Modifier.padding(end = 10.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Card(
                    shape = RoundedCornerShape(
                        topStartPercent = 50,
                        topEndPercent = 50,
                        bottomStartPercent = 50,
                        bottomEndPercent = 50
                    ),
                    modifier = Modifier.padding(bottom = 8.dp),
                    elevation = CardDefaults.cardElevation(5.dp),
                    onClick = {
                        onClick(artistsId)
                    }
                ) {
                    RoundedThumbnail(imageUrl = artistPic)
                }
                Text(
                    text = artistName,
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    softWrap = true
                )
            }

        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RowWithText(
    genres: Genres,
    onClick: (Int, String) -> Unit
) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(8.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        items(genres.data.drop(1)) {
            val genreName = it.name
            val genreId = it.id
            val genrePic = it.picture_big
            if (!genreName.equals("all", ignoreCase = true)) {
                Card(
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.padding(end = 10.dp),
                    elevation = CardDefaults.cardElevation(5.dp),
                    onClick = {
                        onClick(genreId, genreName.replace('/', '-'))
                    }
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        RowThumbnail(imageUrl = genrePic)
                        Text(
                            text = genreName,
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(vertical = 3.dp),
                            textAlign = TextAlign.Center,
                            softWrap = true
                        )
                    }
                }
            }
        }
    }
}
