package com.nima.demomusix.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.nima.demomusix.screens.*

@Composable
fun MusixNavigation (){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screens.HomeScreen.name){
        composable(Screens.HomeScreen.name){
            HomeScreen(navController = navController, viewModel = hiltViewModel())
        }
        composable(Screens.GenresScreen.name){
            GenresScreen(navController = navController, viewModel = hiltViewModel())
        }
        composable(Screens.GenreScreen.name+"/{id}/{name}",
            arguments = listOf(
                navArgument(name = "id"){type = NavType.IntType},
                navArgument(name = "name"){type = NavType.StringType}
            )
        ){
            GenreScreen(navController = navController,
                viewModel = hiltViewModel(),
                genreId = it.arguments?.getInt("id"),
                genreName = it.arguments?.getString("name")
            )
        }
        composable(Screens.ArtistScreen.name+"/{id}",
            arguments = listOf(
                navArgument(name = "id"){type = NavType.IntType}
            )
        ){
            ArtistScreen(navController = navController,
                viewModel = hiltViewModel(),
                id = it.arguments?.getInt("id")
            )
        }

        composable(Screens.ArtistAlbum.name+"/{id}",
            arguments = listOf(
                navArgument(name = "id"){type = NavType.IntType}
            )
            ){
            ArtistAlbum(navController = navController,
                viewModel = hiltViewModel(),
                id = it.arguments?.getInt("id"))
        }
    }
}