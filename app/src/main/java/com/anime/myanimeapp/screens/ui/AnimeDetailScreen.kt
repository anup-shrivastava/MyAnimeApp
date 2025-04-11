package com.anime.myanimeapp.screens.ui

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.anime.myanimeapp.R
import com.anime.myanimeapp.components.CustomText
import com.anime.myanimeapp.components.ProgressDialog
import com.anime.myanimeapp.components.YouTubePlayerComposable
import com.anime.myanimeapp.screens.MyAnimeViewModel
import com.anime.myanimeapp.ui.theme.AppThemeColors
import com.anime.myanimeapp.ui.theme.AppThemeColors.OnSurface
import com.anime.myanimeapp.ui.theme.AppTypography
import com.anime.myanimeapp.utils.Dimens.paddingMedium
import com.anime.myanimeapp.utils.Dimens.paddingSmall
import com.anime.myanimeapp.utils.Dimens.paddingTooSmall
import com.anime.myanimeapp.utils.Dimens.size_12
import com.anime.myanimeapp.utils.Dimens.size_200
import com.anime.myanimeapp.utils.Dimens.size_40

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AnimeDetailScreen(
    animeId: String,
    navController: NavController,
    viewModel: MyAnimeViewModel
) {
    val anime = viewModel.uiState.collectAsState()
    val isLoading = viewModel.isLoading.collectAsState()
    val message = viewModel.message.collectAsState()
    val isNetworkAvailable = viewModel.isNetworkAvailable.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        if (isNetworkAvailable.value) {
            viewModel.getAnimeDetail(animeId)
        }
    }
    LaunchedEffect(message.value) {
        if (message.value.isNotEmpty()) {
            Toast.makeText(context, message.value, Toast.LENGTH_SHORT).show()
        }
    }

    if (anime.value == null || isLoading.value) {
        ProgressDialog()
    }

    if (isNetworkAvailable.value.not()) {
        ProgressDialog(message = stringResource(R.string.no_internet_connection_please_check_your_connection))
    } else {
        Column(modifier = Modifier
            .fillMaxSize()
            .background(OnSurface)) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(paddingMedium),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = AppThemeColors.Surface
                    )
                }

                CustomText(
                    text = anime.value?.title ?: anime.value?.titleEnglish.orEmpty(),
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 2,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                    color = AppThemeColors.Tertiary,
                    fontSize = AppTypography.titleMedium.fontSize
                )

                Spacer(modifier = Modifier.width(paddingSmall))

                AsyncImage(
                    model = anime.value?.images?.jpg?.imageUrl,
                    contentDescription = anime.value?.title,
                    modifier = Modifier
                        .size(size_40)
                        .clip(RoundedCornerShape(paddingSmall)),
                    contentScale = ContentScale.Crop
                )
            }
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(size_200)
                        .padding(horizontal = paddingMedium)
                ) {
                    val youtubeId = anime.value?.trailer?.youtubeId
                    if (!youtubeId.isNullOrEmpty()) {
                        YouTubePlayerComposable(youtubeId)
                    } else {
                        AsyncImage(
                            model = anime.value?.images?.jpg?.imageUrl,
                            contentDescription = anime.value?.title,
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(RoundedCornerShape(size_12)),
                            contentScale = ContentScale.Crop
                        )
                    }

                }
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingMedium)
                ) {
                    CustomText(
                        text = "Synopsis",
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = paddingTooSmall)
                    )

                    CustomText(
                        text = anime.value?.synopsis ?: "No synopsis available",
                        maxLines = 10
                    )

                    Spacer(modifier = Modifier.height(size_12))

                    Row(horizontalArrangement = Arrangement.spacedBy(paddingMedium)) {
                        CustomText(text = "⭐ Score: ${anime.value?.score ?: 0.0}")
                        CustomText(text = "🎬 Episodes: ${anime.value?.episodes ?: 0}")
                    }

                    Spacer(modifier = Modifier.height(size_12))

                    val genreList = anime.value?.genres.orEmpty()
                    if (genreList.isNotEmpty()) {
                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(paddingSmall),
                            verticalArrangement = Arrangement.spacedBy(paddingTooSmall)
                        ) {
                            genreList.forEach { genre ->
                                CustomText(
                                    text = genre.name,
                                    modifier = Modifier
                                        .background(
                                            AppThemeColors.Tertiary,
                                            shape = RoundedCornerShape(paddingSmall)
                                        )
                                        .padding(
                                            horizontal = paddingSmall,
                                            vertical = paddingTooSmall
                                        ),
                                    fontSize = AppTypography.labelSmall.fontSize
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
