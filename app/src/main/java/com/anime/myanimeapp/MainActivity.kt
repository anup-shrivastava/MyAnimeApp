package com.anime.myanimeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.anime.myanimeapp.screens.ui.AnimeDetailScreen
import com.anime.myanimeapp.screens.ui.MyAnimeScreen
import com.anime.myanimeapp.ui.theme.MyAnimeAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyAnimeAppTheme {
                val navController = rememberNavController()

                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->

                    NavHost(
                        navController = navController,
                        startDestination = "anime_list",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("anime_list") {
                            MyAnimeScreen(navController = navController)
                        }

                        composable(
                            route = "anime_detail/{animeId}",
                            arguments = listOf(navArgument("animeId") {
                                type = NavType.StringType
                            })
                        ) { backStackEntry ->
                            val animeId =
                                backStackEntry.arguments?.getString("animeId") ?: return@composable
                            AnimeDetailScreen(animeId = animeId, navController = navController, viewModel = hiltViewModel())
                        }
                    }
                }
            }
        }
    }
}