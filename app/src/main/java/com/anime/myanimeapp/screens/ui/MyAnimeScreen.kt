package com.anime.myanimeapp.screens.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.anime.myanimeapp.components.CustomText
import com.anime.myanimeapp.components.ProgressDialog
import com.anime.myanimeapp.components.SimpleDynamicList
import com.anime.myanimeapp.screens.data.AnimeData
import com.anime.myanimeapp.screens.MyAnimeViewModel
import com.anime.myanimeapp.ui.theme.AppThemeColors
import com.anime.myanimeapp.utils.Dimens.paddingTooSmall
import com.anime.myanimeapp.utils.Dimens.size_10
import com.anime.myanimeapp.utils.Dimens.size_100
import com.anime.myanimeapp.utils.Dimens.size_12
import com.anime.myanimeapp.utils.Dimens.size_4
import com.anime.myanimeapp.utils.Dimens.size_6

@Composable
fun MyAnimeScreen(navController: NavController, viewModel: MyAnimeViewModel = hiltViewModel()) {
    val myAnimeList = viewModel.animePagingFlow.collectAsLazyPagingItems()
    val isNetworkAvailable = viewModel.isNetworkAvailable.collectAsState()

    if (isNetworkAvailable.value.not()) {
        ProgressDialog(message = "No internet connection. Please check your connection.")
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(AppThemeColors.Gray)
        ) {
            SimpleDynamicList(
                items = myAnimeList,
                itemContent = { anime ->
                    AnimeItem(
                        anime,
                        navigateToDetail = { navController.navigate("anime_detail/${anime.malId}") })
                }
            )
        }
    }
}

@Composable
fun AnimeItem(
    anime: AnimeData,
    modifier: Modifier = Modifier,
    navigateToDetail: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = paddingTooSmall)
            .shadow(
                elevation = size_6,
                shape = RoundedCornerShape(size_12),
                clip = true
            )
            .background(AppThemeColors.OnSurface)
            .clickable { navigateToDetail() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(size_12)
        ) {
            AsyncImage(
                model = anime.images?.jpg?.imageUrl,
                contentDescription = anime.title,
                modifier = Modifier
                    .size(size_100)
                    .clip(RoundedCornerShape(size_10)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(size_12))

            Column(
                verticalArrangement = Arrangement.spacedBy(size_4),
                modifier = Modifier.weight(1f)
            ) {
                CustomText(
                    text = anime.title,
                    maxLines = 2
                )

                anime.synopsis?.let {
                    CustomText(
                        text = it,
                        maxLines = 3
                    )
                }

                Row(horizontalArrangement = Arrangement.spacedBy(size_12)) {
                    CustomText(text = "🎯 ${anime.score}")
                    CustomText(text = "🎬 ${anime.episodes} eps")
                }
            }
        }
    }
}

