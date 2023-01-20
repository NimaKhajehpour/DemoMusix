package com.nima.demomusix.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.nima.demomusix.components.RowThumbnail
import com.nima.demomusix.model.genre.Genres
import com.nima.demomusix.navigation.Screens
import com.nima.demomusix.viewmodel.GenresViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GenresScreen (
    navController: NavController,
    viewModel: GenresViewModel
){

    val genres = produceState<Genres?>(initialValue = null){
        value = viewModel.getAllGenres()
    }.value

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.tertiaryContainer,
            shadowElevation = 5.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                IconButton(onClick = {
                    navController.popBackStack()
                },
                    Modifier.padding(start = 8.dp)
                ) {
                    Icon(imageVector = Icons.Rounded.ArrowBack,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onTertiaryContainer
                    )
                }

                Text(text = "All Genres",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(end = 16.dp),
                    color = MaterialTheme.colorScheme.onTertiaryContainer
                )
            }
        }

        AnimatedVisibility(visible = genres == null) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator()
            }
        }
        AnimatedVisibility(visible = genres != null) {
            if (genres != null){
                if (genres.data.isNotEmpty()){
                    LazyVerticalGrid(columns = GridCells.Fixed(2),
                        verticalArrangement = Arrangement.Center,
                        horizontalArrangement = Arrangement.Center,
                    ){
                        items(genres.data.drop(1)){
                            val genreName = it.name
                            val genrePic = it.picture_big
                            val genreId = it.id
                            ElevatedCard(
                                elevation = CardDefaults.cardElevation(5.dp),
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier.padding(8.dp),
                                onClick = {
                                    navController.navigate(
                                        Screens.GenreScreen.name+"/$genreId/${genreName.replace('/', '-')}")
                                }
                            ) {
                                Box{
                                    RowThumbnail(imageUrl = genrePic)
                                    Text(text = genreName,
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White,
                                        modifier = Modifier.align(Alignment.BottomCenter)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}