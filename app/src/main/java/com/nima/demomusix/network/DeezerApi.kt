package com.nima.demomusix.network

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
import retrofit2.http.GET
import retrofit2.http.Path
import javax.inject.Singleton

@Singleton
interface DeezerApi {

    @GET("genre")
    suspend fun getAllGenres(): Genres

    @GET("chart/0/artists")
    suspend fun getTopArtists(): TopArtists

    @GET("chart/0/albums")
    suspend fun getTopAlbums(): TopAlbums

    @GET("chart/0/tracks")
    suspend fun getTopTracks(): TopTracks

    @GET("genre/{id}/artists")
    suspend fun getGenreArtists(@Path("id") id: Int): GenreArtists

    @GET("artist/{id}")
    suspend fun getArtist(@Path("id") id: Int): Artist

    @GET("artist/{id}/top?limit=10")
    suspend fun getArtistTopTracks(@Path("id") id: Int): ArtistTopTracks

    @GET("artist/{id}/albums")
    suspend fun getArtistAlbum(@Path("id") id: Int): ArtistAlbums

    @GET("artist/{id}/related")
    suspend fun getRelatedArtists(@Path("id") id: Int): RelatedArtists

    @GET("album/{id}")
    suspend fun getAlbumTracks(@Path("id") id: Int): ArtistAlbum
}