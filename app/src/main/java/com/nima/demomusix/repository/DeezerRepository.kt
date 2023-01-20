package com.nima.demomusix.repository

import com.nima.demomusix.model.artist.Artist
import com.nima.demomusix.model.artist.album.ArtistAlbum
import com.nima.demomusix.model.artist.albums.ArtistAlbums
import com.nima.demomusix.model.artist.related.RelatedArtists
import com.nima.demomusix.model.artist.toptracks.ArtistTopTracks
import com.nima.demomusix.model.chart.albums.TopAlbums
import com.nima.demomusix.model.chart.artists.TopArtists
import com.nima.demomusix.model.chart.tracks.TopTracks
import com.nima.demomusix.model.genre.Genres
import com.nima.demomusix.model.genre.artists.GenreArtists
import com.nima.demomusix.network.DeezerApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DeezerRepository @Inject constructor(private val api: DeezerApi) {

    suspend fun getAllGenres(): Genres?{
        var genres: Genres? = null
        try {
            genres = api.getAllGenres()
        }catch (e: Exception){
            e.printStackTrace()
        }
        return genres
    }

    suspend fun getTopArtists(): TopArtists?{
        var topArtists: TopArtists? = null
        try {
            topArtists = api.getTopArtists()
        }catch (e: Exception){
            e.printStackTrace()
        }
        return topArtists
    }

    suspend fun getTopAlbums(): TopAlbums?{
        var topAlbums: TopAlbums? = null
        try {
            topAlbums = api.getTopAlbums()
        }catch (e: Exception){
            e.printStackTrace()
        }
        return topAlbums
    }

    suspend fun getTopTracks(): TopTracks?{
        var topTracks: TopTracks? = null
        try {
            topTracks = api.getTopTracks()
        }catch (e: Exception){
            e.printStackTrace()
        }
        return topTracks
    }

    suspend fun getGenreArtists(id: Int): GenreArtists?{
        var genreArtists: GenreArtists? = null

        try{
            genreArtists = api.getGenreArtists(id)
        }catch (e: Exception){
            e.printStackTrace()
        }
        return genreArtists
    }

    suspend fun getArtist(id: Int): Artist?{
        var artist: Artist? = null
        try{
            artist = api.getArtist(id)
        }catch (e: Exception){
            e.printStackTrace()
        }
        return artist
    }

    suspend fun getArtistTopTracks(id: Int): ArtistTopTracks?{
        var topTracks: ArtistTopTracks? = null
        try{
            topTracks = api.getArtistTopTracks(id)
        }catch (e: Exception){
            e.printStackTrace()
        }
        return topTracks
    }

    suspend fun getArtistAlbums(id: Int): ArtistAlbums?{
        var topAlbums: ArtistAlbums? = null
        try{
            topAlbums = api.getArtistAlbum(id)
        }catch (e: Exception){
            e.printStackTrace()
        }
        return topAlbums
    }

    suspend fun getRelatedArtists(id: Int): RelatedArtists?{
        var relatedArtist: RelatedArtists? = null
        try{
            relatedArtist = api.getRelatedArtists(id)
        }catch (e: Exception){
            e.printStackTrace()
        }
        return relatedArtist
    }

    suspend fun getArtistAlbumTracks(id: Int): ArtistAlbum? {
        var artistAlbum: ArtistAlbum? = null
        try {
            artistAlbum = api.getAlbumTracks(id)
        }catch (e: Exception){
            e.printStackTrace()
        }
        return artistAlbum
    }
}