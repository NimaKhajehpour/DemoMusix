package com.nima.demomusix.model.artist.toptracks

data class ArtistTopTracks(
    val `data`: List<Data>,
    val next: String,
    val total: Int
)