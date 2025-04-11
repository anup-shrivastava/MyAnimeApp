package com.anime.myanimeapp.screens

import com.anime.myanimeapp.screens.data.AnimeDetailResponse
import com.anime.myanimeapp.screens.data.TopAnimeResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiServices {
    @GET("top/anime")
    suspend fun getAnimeData(
        @Query("page") page: Int
    ): TopAnimeResponse?

    @GET("anime/{anime_id}")
    suspend fun getAnimeDetailById(
        @Path("anime_id") page: String
    ): AnimeDetailResponse?

}