package com.nima.demomusix.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.produceState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.nima.demomusix.R
import com.nima.demomusix.ThemeDataStore
import com.nima.demomusix.components.RowWithText
import com.nima.demomusix.components.RowWithoutText
import com.nima.demomusix.model.chart.albums.TopAlbums
import com.nima.demomusix.model.chart.artists.TopArtists
import com.nima.demomusix.model.chart.tracks.TopTracks
import com.nima.demomusix.model.genre.Genres
import com.nima.demomusix.navigation.Screens
import com.nima.demomusix.viewmodel.HomeViewModel
import kotlinx.coroutines.launch


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun HomeScreen (
    navController: NavController,
    viewModel: HomeViewModel
){

    val genres = produceState<Genres?>(initialValue = null){
        value = viewModel.getAllGenres()
    }.value

    val topArtists = produceState<TopArtists?>(initialValue = null){
        value = viewModel.getTopArtists()
    }.value

    val topAlbums = produceState<TopAlbums?>(initialValue = null){
        value = viewModel.getTopAlbums()
    }.value

    val context = LocalContext.current

    val themeDataStore = ThemeDataStore(context)

    val isDark =
        themeDataStore.getTheme.collectAsState(initial = false).value ?: false

    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            Text(text = "Demo Musix",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 6.dp),
                color = MaterialTheme.colorScheme.tertiary
            )
            Spacer(modifier = Modifier.weight(1f))
            IconButton(onClick = {
                scope.launch {
                    themeDataStore.saveTheme(!isDark)
                }
            },
                modifier = Modifier.size(48.dp),
            ) {

                AnimatedContent(targetState = isDark) {
                    when (it){
                        true ->{
                            Column(
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ){
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_baseline_light_mode_24),
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.tertiary
                                )
                            }
                        }else -> {
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ){
                            Icon(
                                painter = painterResource(id = R.drawable.ic_baseline_dark_mode_24),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.tertiary
                            )
                        }
                    }
                    }
                }

            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Text(text = "Genres",
                modifier = Modifier.padding(top = 8.dp, start = 8.dp, end = 8.dp),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.tertiary
            )
            AnimatedVisibility(visible = genres == null) {
                CircularProgressIndicator(modifier = Modifier
                    .padding(top = 8.dp)
                    .size(16.dp),
                    strokeWidth = 2.dp
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            AnimatedVisibility(visible = genres != null) {
                TextButton(onClick = {
                     navController.navigate(Screens.GenresScreen.name)
                },
                    modifier = Modifier.padding(top = 8.dp, start = 8.dp, end = 8.dp),
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = MaterialTheme.colorScheme.tertiary
                    )
                ) {
                    Text(text = "View All")
                }
            }
        }
        AnimatedVisibility(visible = genres != null) {
            if (genres != null){
                if (genres.data.isNotEmpty()){
                    RowWithText(genres){genreId, genreName ->
                        navController.navigate(Screens.GenreScreen.name+"/$genreId/$genreName")
                    }
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Text(text = "Top Artists",
                modifier = Modifier.padding(top = 8.dp, start = 8.dp, end = 8.dp),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.tertiary

            )
            AnimatedVisibility(visible = topArtists == null) {
                CircularProgressIndicator(modifier = Modifier
                    .padding(top = 8.dp)
                    .size(16.dp),
                    strokeWidth = 2.dp
                )
            }
        }
        AnimatedVisibility(visible = topArtists != null) {
            if (topArtists != null){
                if (topArtists.data.isNotEmpty()){
                    RowWithText(topArtists){
                        navController.navigate(Screens.ArtistScreen.name+"/$it")
                    }
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Text(text = "Top Albums",
                modifier = Modifier.padding(top = 8.dp, start = 8.dp, end = 8.dp),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.tertiary

            )
            AnimatedVisibility(visible = topAlbums == null) {
                CircularProgressIndicator(modifier = Modifier
                    .padding(top = 8.dp)
                    .size(16.dp),
                    strokeWidth = 2.dp
                )
            }
        }
        AnimatedVisibility(visible = topAlbums != null) {
            if (topAlbums != null){
                if (topAlbums.data.isNotEmpty()){
                    RowWithoutText(topAlbums){
                        navController.navigate(Screens.ArtistAlbum.name+"/$it")
                    }
                }
            }
        }
        
    }
}


