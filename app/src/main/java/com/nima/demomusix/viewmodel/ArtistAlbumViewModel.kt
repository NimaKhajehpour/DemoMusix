package com.nima.demomusix.viewmodel

import androidx.lifecycle.ViewModel
import com.nima.demomusix.repository.DeezerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class ArtistAlbumViewModel @Inject constructor(private val repository: DeezerRepository)
    : ViewModel() {

        suspend fun getArtistAlbumTracks(id: Int) = repository.getArtistAlbumTracks(id)
}