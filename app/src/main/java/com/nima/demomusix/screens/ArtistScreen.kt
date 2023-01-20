package com.nima.demomusix.screens

import android.media.AudioManager
import android.media.MediaPlayer
import android.os.CountDownTimer
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.nima.demomusix.components.RowThumbnail
import com.nima.demomusix.components.RowWithText
import com.nima.demomusix.components.RowWithoutText
import com.nima.demomusix.model.artist.Artist
import com.nima.demomusix.model.artist.albums.ArtistAlbums
import com.nima.demomusix.model.artist.related.RelatedArtists
import com.nima.demomusix.model.artist.toptracks.ArtistTopTracks
import com.nima.demomusix.navigation.Screens
import com.nima.demomusix.viewmodel.ArtistViewModel
import com.nima.demomusix.R
import kotlinx.coroutines.runBlocking

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArtistScreen (
    navController: NavController,
    viewModel: ArtistViewModel,
    id: Int?
){
    val artistId by remember{
        mutableStateOf(id ?: 0)
    }

    var mediaPlayer: MediaPlayer = MediaPlayer()
    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)

    var audioUrl by remember {
        mutableStateOf("")
    }

    var mediaPlaying by remember {
        mutableStateOf(false)
    }

    var mediaPosition by remember {
        mutableStateOf(0f)
    }

    var showPlayer by remember {
        mutableStateOf(false)
    }

    var audioImg by remember {
        mutableStateOf("")
    }

    var audioName by remember {
        mutableStateOf("")
    }

    var showDetails by remember{
        mutableStateOf(false)
    }

    mediaPlayer.setOnCompletionListener { mediaPlaying = false }

    val artist = produceState<Artist?>(initialValue = null){
        value = viewModel.getArtist(artistId)
    }.value

    val artistTopTracks = produceState<ArtistTopTracks?>(initialValue = null){
        value = viewModel.getArtistTopTracks(artistId)
    }.value

    val artistAlbums = produceState<ArtistAlbums?>(initialValue = null){
        value = viewModel.getArtistAlbums(artistId)
    }.value

    val relatedArtists = produceState<RelatedArtists?>(initialValue = null){
        value = viewModel.getRelatedArtists(artistId)
    }.value

    BackHandler(true) {
        if (!showDetails){
            mediaPlaying = false
            mediaPlayer.stop()
            mediaPlayer.reset()
            mediaPlayer.release()
            navController.popBackStack()
        }else{
            showDetails = !showDetails
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        AnimatedVisibility(visible = artist == null) {
            CircularProgressIndicator()
        }
        AnimatedVisibility(visible = artist != null) {
            if (artist != null){
                Box(
                    modifier = Modifier.fillMaxSize()
                ){
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Surface(
                            modifier = Modifier.fillMaxWidth(),
                            color = MaterialTheme.colorScheme.tertiaryContainer,
                            shadowElevation = 5.dp
                        ){
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                verticalArrangement = Arrangement.Top,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ){
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(color = Color.Transparent),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Start
                                ) {
                                    IconButton(
                                        onClick = {
                                            mediaPlaying = false
                                            mediaPlayer.stop()
                                            mediaPlayer.reset()
                                            mediaPlayer.release()
                                            navController.popBackStack()
                                        },
                                        Modifier.padding(start = 8.dp, end = 4.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Rounded.ArrowBack,
                                            contentDescription = null,
                                            tint = MaterialTheme.colorScheme.onTertiaryContainer
                                        )
                                    }
                                    Text(
                                        text = artist.name,
                                        style = MaterialTheme.typography.bodyLarge,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(end = 16.dp, start = 8.dp),
                                        color = MaterialTheme.colorScheme.onTertiaryContainer
                                    )
                                    Spacer(modifier = Modifier.weight(1f))

                                    Text(
                                        text = "${artist.nb_fan} Followers",
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(
                                            vertical = 8.dp,
                                            horizontal = 8.dp
                                        ),
                                        color = MaterialTheme.colorScheme.onTertiaryContainer
                                    )
                                }
                                AnimatedVisibility(visible = showPlayer) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(bottom = 8.dp)
                                            .clickable(role = Role.Button) {
                                                showDetails = true
                                            },
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.Start
                                    ) {
                                        Column(
                                            verticalArrangement = Arrangement.Center,
                                            horizontalAlignment = Alignment.Start,
                                            modifier = Modifier.padding(end = 5.dp, start = 8.dp)
                                        ) {
                                            Row(
                                                modifier = Modifier.fillMaxWidth(),
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.Start
                                            ) {
                                                IconButton(
                                                    onClick = {
                                                        if (mediaPlaying) {
                                                            mediaPlaying = false
                                                            mediaPlayer.pause()
                                                        } else {
                                                            mediaPlaying = true
                                                            mediaPlayer.start()
                                                            object : CountDownTimer(
                                                                mediaPlayer.duration.toLong(),
                                                                100
                                                            ) {
                                                                override fun onTick(
                                                                    millisUntilFinished: Long
                                                                ) {
                                                                    if (mediaPlaying) {
                                                                        mediaPosition =
                                                                            mediaPlayer.currentPosition.toFloat()
//                                                                if (!mediaPlaying) {
//                                                                    this.onFinish()
//                                                                }
                                                                    }
                                                                }

                                                                override fun onFinish() {
                                                                }
                                                            }.start()
                                                        }
                                                    },
                                                    modifier = Modifier.size(24.dp)
                                                ) {
                                                    Icon(
                                                        painter =
                                                        if (mediaPlaying)
                                                            painterResource(id = R.drawable.ic_baseline_pause_24)
                                                        else painterResource(
                                                            id = R.drawable.ic_baseline_play_arrow_24
                                                        ), contentDescription = null,
                                                        tint = MaterialTheme.colorScheme.onTertiaryContainer
                                                    )
                                                }
                                                Text(
                                                    text = if (audioName.length < 20) audioName else "${
                                                        audioName.substring(
                                                            0..17
                                                        )
                                                    }...",
                                                    style = MaterialTheme.typography.labelMedium,
                                                    modifier = Modifier.padding(
                                                        bottom = 1.dp,
                                                        start = 4.dp
                                                    ),
                                                    color = MaterialTheme.colorScheme.onTertiaryContainer
                                                )
                                                Spacer(modifier = Modifier.weight(1f))

                                                IconButton(
                                                    onClick = {
                                                        showDetails = false
                                                        showPlayer = false
                                                        mediaPlayer.stop()
                                                        mediaPlayer.reset()
                                                        mediaPlaying = false
                                                    },
                                                    modifier = Modifier.size(24.dp)
                                                ) {
                                                    Icon(
                                                        imageVector = Icons.Default.Close,
                                                        contentDescription = null,
                                                        tint = MaterialTheme.colorScheme.onTertiaryContainer
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .verticalScroll(rememberScrollState()),
                            verticalArrangement = Arrangement.Top,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Top Tracks",
                                modifier = Modifier.padding(bottom = 8.dp, top = 8.dp),
                                fontWeight = FontWeight.Bold
                            )

                            AnimatedVisibility(visible = artistTopTracks == null) {
                                CircularProgressIndicator()
                            }
                            AnimatedVisibility(visible = artistTopTracks != null) {
                                if (artistTopTracks != null && artistTopTracks.data.isNotEmpty()) {
                                    Column(
                                        modifier = Modifier.fillMaxWidth(),
                                        verticalArrangement = Arrangement.Top,
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        artistTopTracks.data.forEachIndexed { index, data ->
                                            Card(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(vertical = 5.dp, horizontal = 8.dp),
                                                shape = RoundedCornerShape(
                                                    5.dp
                                                ),
                                                elevation = CardDefaults.cardElevation(5.dp),
                                                colors = CardDefaults.cardColors(
                                                    containerColor = MaterialTheme.colorScheme.tertiary
                                                ),
                                                onClick = {
                                                    showPlayer = true
                                                    audioName = data.title_short
                                                    if (!mediaPlaying) {
                                                        runBlocking {
                                                            try {
                                                                if (audioUrl != data.preview) {
                                                                    mediaPlayer.stop()
                                                                    mediaPlayer.reset()
                                                                    mediaPlayer.setDataSource(data.preview)
                                                                    mediaPlayer.prepareAsync()
                                                                    mediaPlayer.setOnPreparedListener {
                                                                        audioImg = data.album.cover_big
                                                                        it.start()
                                                                        mediaPlaying = true
                                                                        audioUrl = data.preview
                                                                        object : CountDownTimer(
                                                                            mediaPlayer.duration.toLong(),
                                                                            100
                                                                        ) {
                                                                            override fun onTick(
                                                                                millisUntilFinished: Long
                                                                            ) {
                                                                                if (mediaPlaying) {
                                                                                    mediaPosition =
                                                                                        mediaPlayer.currentPosition.toFloat()
//                                                                                    if (!mediaPlaying) {
//                                                                                        this.onFinish()
//                                                                                    }
                                                                                }
                                                                            }

                                                                            override fun onFinish() {
                                                                            }
                                                                        }.start()
                                                                    }
                                                                } else {
                                                                    audioImg = data.album.cover_big
                                                                    mediaPlayer.start()
                                                                    mediaPlaying = true
                                                                    object : CountDownTimer(
                                                                        mediaPlayer.duration.toLong(),
                                                                        100
                                                                    ) {
                                                                        override fun onTick(
                                                                            millisUntilFinished: Long
                                                                        ) {
                                                                            if (mediaPlaying) {
                                                                                mediaPosition =
                                                                                    mediaPlayer.currentPosition.toFloat()
//                                                                                if (!mediaPlaying) {
//                                                                                    this.onFinish()
//                                                                                }
                                                                            }
                                                                        }

                                                                        override fun onFinish() {
                                                                        }
                                                                    }.start()
//                                                                while (mediaPlayer.isPlaying){
//                                                                    Log.d("LOL", "ArtistScreen: ${mediaPlayer.currentPosition}")
//
//                                                                }
                                                                }
                                                            } catch (e: Exception) {
                                                                e.printStackTrace()
                                                            }
                                                        }
                                                    } else {
                                                        mediaPlayer.pause()
                                                        if (audioUrl == data.preview) {
                                                            mediaPlaying = false
                                                        } else {
                                                            mediaPlayer.stop()
                                                            mediaPlayer.reset()
                                                            runBlocking {
                                                                try {
                                                                    mediaPlayer.setDataSource(data.preview)
                                                                    mediaPlayer.prepareAsync()
                                                                    mediaPlayer.setOnPreparedListener {
                                                                        audioImg = data.album.cover_big
                                                                        it.start()
                                                                        mediaPlaying = true
                                                                        audioUrl = data.preview
                                                                        object : CountDownTimer(
                                                                            mediaPlayer.duration.toLong(),
                                                                            100
                                                                        ) {
                                                                            override fun onTick(
                                                                                millisUntilFinished: Long
                                                                            ) {
                                                                                if (mediaPlaying) {
                                                                                    mediaPosition =
                                                                                        mediaPlayer.currentPosition.toFloat()
//                                                                                    if (!mediaPlaying) {
//                                                                                        this.onFinish()
//                                                                                    }
                                                                                }
                                                                            }

                                                                            override fun onFinish() {
                                                                            }
                                                                        }.start()
//                                                                    while (it.isPlaying){
//                                                                        Log.d("LOL", "ArtistScreen: ${it.currentPosition}")
//                                                                    }
                                                                    }
                                                                } catch (e: Exception) {
                                                                    e.printStackTrace()
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            ) {
                                                Row(
                                                    modifier = Modifier
                                                        .padding(vertical = 5.dp, horizontal = 8.dp)
                                                        .fillMaxWidth(),
                                                    verticalAlignment = Alignment.CenterVertically,
                                                    horizontalArrangement = Arrangement.Start
                                                ) {
                                                    RowThumbnail(imageUrl = data.album.cover)
                                                    Spacer(modifier = Modifier.weight(1f))
                                                    Text(
                                                        text =
                                                        if (data.title_short.length <= 26) data.title_short
                                                        else "${
                                                            data.title_short.substring(
                                                                0,
                                                                23
                                                            )
                                                        }...",
                                                        softWrap = true,
                                                        textAlign = TextAlign.Center,
                                                        overflow = TextOverflow.Ellipsis,
                                                        color = MaterialTheme.colorScheme.onTertiary
                                                    )
                                                    Spacer(modifier = Modifier.weight(1f))
                                                    Text(
                                                        text = String.format(
                                                            "%02d:%02d",
                                                            data.duration / 60,
                                                            data.duration % 60
                                                        ),
                                                        textAlign = TextAlign.End,
                                                        color = MaterialTheme.colorScheme.onTertiary
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
                                    .padding(start = 16.dp, end = 16.dp, top = 16.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Start
                            ) {
                                Text(
                                    text = "Top Albums",
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.weight(1f))
                            }
                            AnimatedVisibility(visible = artistAlbums == null) {
                                CircularProgressIndicator()
                            }
                            AnimatedVisibility(visible = artistAlbums != null) {
                                if (artistAlbums != null && artistAlbums.data.isNotEmpty()) {
                                    RowWithoutText(albums = artistAlbums){
                                        mediaPlaying = false
                                        mediaPlayer.stop()
                                        mediaPlayer.reset()
                                        mediaPlayer.release()
                                        navController.navigate(Screens.ArtistAlbum.name+"/$it")
                                    }
                                }
                            }

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 16.dp, end = 16.dp, top = 16.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Start
                            ) {
                                Text(
                                    text = "Related Artists",
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.weight(1f))
                            }
                            AnimatedVisibility(visible = relatedArtists == null) {
                                CircularProgressIndicator()
                            }
                            AnimatedVisibility(visible = relatedArtists != null) {
                                if (relatedArtists != null && relatedArtists.data.isNotEmpty()) {
                                    RowWithText(relatedArtists = relatedArtists) {
                                        mediaPlaying = false
                                        mediaPlayer.stop()
                                        mediaPlayer.reset()
                                        mediaPlayer.release()
                                        navController.navigate(Screens.ArtistScreen.name + "/$it")
                                    }
                                }
                            }
                        }
                    }
//                    if(showDetails) {
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.BottomCenter)
                                .verticalScroll(rememberScrollState()),
                            color = MaterialTheme.colorScheme.surface,
                            shape = AbsoluteRoundedCornerShape(
                                topRight = 25.dp, topLeft = 25.dp
                            ),
                            tonalElevation = 15.dp,
                            shadowElevation = 15.dp
                        ){
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                verticalArrangement = Arrangement.Top,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                AnimatedVisibility(visible = showDetails){
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth(),
                                        verticalArrangement = Arrangement.Top,
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ){
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(horizontal = 16.dp, vertical = 8.dp),
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.Start
                                        ) {
                                            Spacer(modifier = Modifier.weight(1f))
                                            IconButton(onClick = {
                                                showDetails = false
                                            }) {
                                                Icon(
                                                    painter = painterResource(id = R.drawable.ic_baseline_expand_more_24),
                                                    contentDescription = null
                                                )
                                            }
                                        }
                                        Card(
                                            shape = RoundedCornerShape(5.dp),
                                            elevation = CardDefaults.cardElevation(5.dp),
                                            modifier = Modifier.padding(vertical = 16.dp)
                                        ) {
                                            RowThumbnail(
                                                imageUrl = audioImg,
                                                showLoading = true,
                                            )
                                        }
                                        Text(
                                            text = audioName,
                                            style = MaterialTheme.typography.bodyMedium,
                                            modifier = Modifier.padding(bottom = 8.dp),
                                            textAlign = TextAlign.Center
                                        )

                                        IconButton(
                                            onClick = {
                                                if (mediaPlaying) {
                                                    mediaPlaying = false
                                                    mediaPlayer.pause()
                                                } else {
                                                    mediaPlaying = true
                                                    mediaPlayer.start()
                                                    object : CountDownTimer(
                                                        mediaPlayer.duration.toLong(),
                                                        100
                                                    ) {
                                                        override fun onTick(
                                                            millisUntilFinished: Long
                                                        ) {
                                                            if (mediaPlaying) {
                                                                mediaPosition =
                                                                    mediaPlayer.currentPosition.toFloat()
//                                                    if (!mediaPlaying) {
//                                                        this.onFinish()
//                                                    }
                                                            }
                                                        }

                                                        override fun onFinish() {
                                                        }
                                                    }.start()
                                                }
                                            },
                                            modifier = Modifier.size(56.dp)
                                        ) {
                                            Icon(
                                                painter =
                                                if (mediaPlaying)
                                                    painterResource(id = R.drawable.ic_baseline_pause_24)
                                                else painterResource(
                                                    id = R.drawable.ic_baseline_play_arrow_24
                                                ), contentDescription = null
                                            )
                                        }

                                        Slider(
                                            value = mediaPosition,
                                            onValueChange = {
                                                mediaPosition = it
                                                mediaPlayer.seekTo(it.toInt())
                                            },
                                            valueRange = 0f..mediaPlayer.duration.toFloat(),
                                            modifier = Modifier.padding(
                                                start = 32.dp,
                                                end = 32.dp,
                                                bottom = 16.dp
                                            )
                                        )
                                    }
                                }
                            }
                        }
                        // Debug The Seeker And Play Button
//                    }
                }
            }
        }
    }
}