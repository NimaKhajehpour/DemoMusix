package com.nima.demomusix.screens

import android.media.AudioManager
import android.media.MediaPlayer
import android.os.CountDownTimer
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.nima.demomusix.R
import com.nima.demomusix.components.RowThumbnail
import com.nima.demomusix.model.artist.album.ArtistAlbum
import com.nima.demomusix.navigation.Screens
import com.nima.demomusix.viewmodel.ArtistAlbumViewModel
import kotlinx.coroutines.runBlocking

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArtistAlbum (
    navController: NavController,
    viewModel: ArtistAlbumViewModel,
    id: Int?
){
    val albumTracks = produceState<ArtistAlbum?>(initialValue = null){
        value = viewModel.getArtistAlbumTracks(id ?: 0)
    }.value

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
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AnimatedVisibility(visible = albumTracks == null) {
            CircularProgressIndicator()
        }
        AnimatedVisibility(visible = albumTracks != null) {
            if (albumTracks != null){
                Box(
                    modifier = Modifier.fillMaxSize()
                ){
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
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                verticalArrangement = Arrangement.Top,
                                horizontalAlignment = Alignment.CenterHorizontally

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
                                    Spacer(modifier = Modifier.weight(1f))

                                    Text(
                                        text = String.format(
                                            "%d Tracks - %02d:%02d:%02d",
                                            albumTracks.nb_tracks,
                                            albumTracks.duration / 3600,
                                            albumTracks.duration % 3600 / 60,
                                            albumTracks.duration % 60
                                        ),
                                        style = MaterialTheme.typography.bodySmall,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(end = 16.dp),
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
                            Modifier
                                .fillMaxWidth()
                                .verticalScroll(rememberScrollState()),
                            verticalArrangement = Arrangement.Top,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Row(
                                Modifier
                                    .fillMaxWidth()
                                    .padding(start = 16.dp, end = 16.dp, bottom = 8.dp, top = 16.dp),
                                verticalAlignment = Alignment.Top,
                                horizontalArrangement = Arrangement.Start
                            ) {
                                RowThumbnail(
                                    imageUrl = albumTracks.cover_medium,
                                    modifier = Modifier.padding(end = 8.dp)
                                )
                                Column(
                                    modifier = Modifier.padding(5.dp),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.Start
                                ) {
                                    Text(
                                        text = albumTracks.title,
                                        style = MaterialTheme.typography.bodySmall,
                                        fontWeight = FontWeight.Bold,
                                        softWrap = true,
                                        modifier = Modifier.padding(start = 8.dp, bottom = 5.dp)
                                    )

                                    Text(
                                        text = albumTracks.release_date,
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(bottom = 5.dp, start = 8.dp)
                                    )

                                    OutlinedButton(
                                        onClick = {
                                            mediaPlaying = false
                                            mediaPlayer.stop()
                                            mediaPlayer.reset()
                                            mediaPlayer.release()
                                            navController.navigate(Screens.ArtistScreen.name + "/${albumTracks.artist.id}")
                                        },
                                        modifier = Modifier.padding(bottom = 5.dp)
                                    ) {
                                        Text(
                                            text = albumTracks.artist.name,
                                            style = MaterialTheme.typography.bodySmall,
                                            fontWeight = FontWeight.Bold,
                                            modifier = Modifier.padding(end = 5.dp),
                                            textAlign = TextAlign.Center
                                        )
                                    }
                                }
                            }
                            Text(
                                text = "Tracks",
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )

                            albumTracks.tracks.data.forEach { data ->
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 5.dp, horizontal = 8.dp),
                                    shape = RoundedCornerShape(
                                        5.dp
                                    ),
                                    colors = CardDefaults.cardColors(
                                        containerColor = MaterialTheme.colorScheme.tertiary,
                                        contentColor = MaterialTheme.colorScheme.onTertiary
                                    ),
                                    elevation = CardDefaults.cardElevation(5.dp),
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
                                            overflow = TextOverflow.Ellipsis
                                        )
                                        Spacer(modifier = Modifier.weight(1f))
                                        Text(
                                            text = String.format(
                                                "%02d:%02d",
                                                data.duration / 60,
                                                data.duration % 60
                                            ),
                                            textAlign = TextAlign.End
                                        )
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