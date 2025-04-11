package com.anime.myanimeapp.screens.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.anime.myanimeapp.R
import com.anime.myanimeapp.screens.ApiServices
import com.anime.myanimeapp.utils.ResourceProvider
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MyAnimeRepo @Inject constructor(
    private val apiServices: ApiServices,
    private val resourceProvider: ResourceProvider
) {
    fun getAnimeData(): Flow<PagingData<AnimeData>> {
        return Pager(
            config = PagingConfig(
                pageSize = 25,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { MyAnimePagingSource(apiServices) }
        ).flow
    }

    suspend fun getAnimeDetailsById(animeId: String): Result<AnimeDetailData>? {
        return try {
            apiServices.getAnimeDetailById(animeId)?.let {
                if (it.data != null) {
                    Result.success(it.data)
                } else {
                    Result.failure(Exception(resourceProvider.getString(R.string.failed_to_fetch_anime_details)))
                }
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            Result.failure(Exception(resourceProvider.getString(R.string.failed_to_fetch_anime_details)))
        }
    }
}