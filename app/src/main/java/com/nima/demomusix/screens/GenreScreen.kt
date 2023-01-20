package com.nima.demomusix.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.nima.demomusix.components.RoundedThumbnail
import com.nima.demomusix.model.genre.artists.GenreArtists
import com.nima.demomusix.navigation.Screens
import com.nima.demomusix.viewmodel.GenreViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GenreScreen(
    navController: NavController,
    viewModel: GenreViewModel,
    genreId: Int?,
    genreName: String?
) {

    val genre by remember{
        mutableStateOf(genreId ?: 0)
    }
    val name by remember{
        mutableStateOf(genreName?.replace('-', '/') ?: "")
    }

    val genreArtists = produceState<GenreArtists?>(initialValue = null){
        value = viewModel.getGenreArtist(genre)
    }.value


    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AnimatedVisibility(visible = genreArtists == null) {
            CircularProgressIndicator()
        }
        AnimatedVisibility(visible = genreArtists != null) {
            if (genreArtists != null && genreArtists.data.isNotEmpty()){
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        color = MaterialTheme.colorScheme.tertiaryContainer,
                        shadowElevation = 5.dp
                    ){
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start
                        ) {
                            IconButton(
                                onClick = {
                                    navController.popBackStack()
                                },
                                Modifier.padding(start = 8.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Rounded.ArrowBack,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.onTertiaryContainer
                                )
                            }

                            Text(
                                text = name,
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(end = 16.dp),
                                color = MaterialTheme.colorScheme.onTertiaryContainer
                            )
                        }
                    }

                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        verticalArrangement = Arrangement.Center,
                        horizontalArrangement = Arrangement.Center,
                    )   {
                        items(genreArtists.data){
                            val artistName = it.name
                            val artistId = it.id
                            val artistPic = it.picture_big
                            Column(
                                modifier = Modifier.padding(8.dp),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ){
                                ElevatedCard(
                                    elevation = CardDefaults.cardElevation(5.dp),
                                    shape = RoundedCornerShape(
                                        topStartPercent = 50,
                                        topEndPercent = 50,
                                        bottomStartPercent = 50,
                                        bottomEndPercent = 50
                                    ),
                                    modifier = Modifier.padding(8.dp),
                                    onClick = {
                                        navController.navigate(
                                            Screens.ArtistScreen.name+"/$artistId")
                                    }

                                ) {
                                    RoundedThumbnail(imageUrl = artistPic)
                                }

                                Text(text = artistName,
                                    style = MaterialTheme.typography.bodyLarge,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                }
            }
        }
    }

}