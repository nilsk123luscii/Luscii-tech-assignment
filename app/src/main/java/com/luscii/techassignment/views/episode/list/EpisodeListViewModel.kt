package com.luscii.techassignment.views.episode.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.luscii.techassignment.domain.models.Episode
import com.luscii.techassignment.domain.usecases.GetEpisodesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

sealed class EpisodeListViewState {
    data class EpisodeList(val episodesFlow: Flow<PagingData<Episode>>) : EpisodeListViewState()
}

@HiltViewModel
class EpisodeListViewModel @Inject constructor(
    getEpisodesUseCase: GetEpisodesUseCase
) : ViewModel() {
    internal val episodesFlow: Flow<PagingData<Episode>> =
        getEpisodesUseCase()
            .flow
            .cachedIn(viewModelScope)

    private val _state =
        MutableStateFlow<EpisodeListViewState>(EpisodeListViewState.EpisodeList(episodesFlow))
    internal val state: StateFlow<EpisodeListViewState> = _state.asStateFlow()
}
