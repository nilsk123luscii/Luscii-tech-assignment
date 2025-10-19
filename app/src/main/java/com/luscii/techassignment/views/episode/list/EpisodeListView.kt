package com.luscii.techassignment.views.episode.list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.luscii.techassignment.R
import com.luscii.techassignment.domain.models.Episode
import com.luscii.techassignment.views.common.ExceptionType
import com.luscii.techassignment.views.common.ExceptionView
import com.luscii.techassignment.views.common.ListItem
import com.luscii.techassignment.views.common.LoadingSpinner
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.StateFlow
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun EpisodeListView(
    state: StateFlow<EpisodeListViewState>,
    onEpisodeSelected: (Episode) -> Unit,
) {
    val listState = rememberLazyListState()

    state.collectAsState().value.let { viewState ->
        when (viewState) {
            is EpisodeListViewState.EpisodeList -> {
                val episodes = viewState.episodesFlow.collectAsLazyPagingItems()
                val state = episodes.loadState

                LaunchedEffect(Unit) {
                    while (true) {
                        delay(60_000)
                        episodes.refresh()
                    }
                }

                LazyColumn(
                    modifier = Modifier,
                    state = listState,
                    content = {
                        items(count = episodes.itemCount) { index ->
                            episodes.get(index = index)?.let { episode ->
                                ListItem(
                                    content = {
                                        EpisodeItemDetails(episode)
                                    },
                                    onClickItem = {
                                        onEpisodeSelected.invoke(episode)
                                    }
                                )
                            }
                        }
                        if (state == LoadState.Loading) {
                            item {
                                LoadingSpinner()
                            }
                        }

                        if (episodes.loadState.append.endOfPaginationReached) {
                            item {
                                ListItem(
                                    content = {
                                        Text(
                                            text = stringResource(R.string.episode_list_end_reached)
                                        )
                                    }
                                )
                            }
                        }

                        when (episodes.loadState.append) {
                            is LoadState.Loading -> item { LoadingSpinner() }
                            is LoadState.Error -> item {
                                ExceptionView(
                                    text = stringResource(R.string.exception_error_network),
                                    exceptionType = ExceptionType.ERROR
                                )
                            }

                            else -> Unit
                        }

                        when (episodes.loadState.refresh) {
                            is LoadState.Loading -> {
                                item { LoadingSpinner() }
                            }

                            is LoadState.Error -> item {
                                ExceptionView(
                                    text = stringResource(R.string.exception_error_network),
                                    exceptionType = ExceptionType.ERROR
                                )
                            }

                            is LoadState.NotLoading -> Unit
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun EpisodeItemDetails(episode: Episode) {
    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Text(text = episode.name) // far left
        Text(text = formatAirDate(episode.air_date)) // middle
        Text(text = episode.episode) // far right
    }
}

fun formatAirDate(dateString: String): String {
    val inputFormatter = DateTimeFormatter.ofPattern("MMMM d, yyyy", Locale.ENGLISH)
    val outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

    val date = LocalDate.parse(dateString, inputFormatter)
    return date.format(outputFormatter)
}
