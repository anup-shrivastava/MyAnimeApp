package com.anime.myanimeapp.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.anime.myanimeapp.utils.Dimens.paddingMedium
import com.anime.myanimeapp.utils.Dimens.paddingSmall

@Composable
fun <T : Any> SimpleDynamicList(
    modifier: Modifier = Modifier,
    items: LazyPagingItems<T>,
    itemContent: @Composable (T) -> Unit,
    emptyMessage: String = "No items found"
) {
    Box(modifier = modifier.fillMaxSize()) {
        when {
            items.itemCount == 0 && items.loadState.refresh is LoadState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    ProgressDialog()
                }
            }

            items.itemCount == 0 && items.loadState.refresh is LoadState.NotLoading -> {
                CustomText(
                    text = emptyMessage,
                    modifier = Modifier.padding(paddingMedium)
                )
            }

            else -> {
                LazyColumn(
                    contentPadding = PaddingValues(paddingMedium),
                    verticalArrangement = Arrangement.spacedBy(paddingSmall)
                ) {
                    items(items.itemCount) { index ->
                        items[index]?.let { itemContent(it) }
                    }

                    if (items.loadState.append is LoadState.Loading) {
                        item {
                            ProgressDialog()
                        }
                    }
                }
            }
        }
    }
}

