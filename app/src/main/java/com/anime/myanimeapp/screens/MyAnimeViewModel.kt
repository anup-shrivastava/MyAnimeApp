package com.anime.myanimeapp.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.anime.myanimeapp.screens.data.AnimeDetailData
import com.anime.myanimeapp.screens.data.MyAnimeRepo
import com.anime.myanimeapp.utils.NetworkObserver
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyAnimeViewModel @Inject constructor(
    private val myAnimeRepo: MyAnimeRepo,
    private val networkObserver: NetworkObserver
): ViewModel() {
    private val _uiState = MutableStateFlow<AnimeDetailData?>(AnimeDetailData())
    val uiState: StateFlow<AnimeDetailData?> = _uiState

    val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    val _message = MutableStateFlow("")
    val message: StateFlow<String> = _message

    private val _isNetworkAvailable = MutableStateFlow(false)
    val isNetworkAvailable: StateFlow<Boolean> = _isNetworkAvailable

    init {
        viewModelScope.launch {
            networkObserver.networkStatus.collect { isConnected ->
                _isNetworkAvailable.value = isConnected
            }
        }
    }

    val animePagingFlow = myAnimeRepo.getAnimeData().cachedIn(viewModelScope)

    fun getAnimeDetail(animeId: String) {
        _isLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            myAnimeRepo.getAnimeDetailsById(animeId)?.onSuccess {
                _uiState.value = it
            }?.onFailure {
                _message.value = it.message.toString()
            }
            _isLoading.value = false
        }
    }
}