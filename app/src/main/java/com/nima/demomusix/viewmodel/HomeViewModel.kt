package com.nima.demomusix.viewmodel

import androidx.lifecycle.ViewModel
import com.nima.demomusix.repository.DeezerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: DeezerRepository) : ViewModel() {

    suspend fun getAllGenres() = repository.getAllGenres()

    suspend fun getTopArtists() = repository.getTopArtists()

    suspend fun getTopAlbums() = repository.getTopAlbums()

    suspend fun getTopTracks() = repository.getTopTracks()
}