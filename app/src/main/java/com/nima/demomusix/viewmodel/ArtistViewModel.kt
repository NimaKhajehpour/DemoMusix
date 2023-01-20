package com.nima.demomusix.viewmodel

import androidx.lifecycle.ViewModel
import com.nima.demomusix.repository.DeezerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ArtistViewModel @Inject constructor(private val repository: DeezerRepository)
    : ViewModel() {

        suspend fun getArtist(id: Int) = repository.getArtist(id)

        suspend fun getArtistTopTracks(id: Int) = repository.getArtistTopTracks(id)

        suspend fun getArtistAlbums(id: Int) = repository.getArtistAlbums(id)

        suspend fun getRelatedArtists(id: Int) = repository.getRelatedArtists(id)
}