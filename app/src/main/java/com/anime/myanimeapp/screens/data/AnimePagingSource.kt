package com.anime.myanimeapp.screens.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.anime.myanimeapp.screens.ApiServices

class MyAnimePagingSource(private val apiServices: ApiServices):PagingSource<Int, AnimeData>() {
    override fun getRefreshKey(state: PagingState<Int, AnimeData>): Int? {
        return state.anchorPosition.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition ?: return null)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, AnimeData> {
        val page = params.key ?: 1
        return try {
            val response = apiServices.getAnimeData(page)
            LoadResult.Page(
                data = response?.data?:emptyList(),
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (response?.pagination?.hasNextPage == true) page + 1 else null
            )
        } catch (ex: Exception) {
            LoadResult.Error(ex)
        }
    }
}