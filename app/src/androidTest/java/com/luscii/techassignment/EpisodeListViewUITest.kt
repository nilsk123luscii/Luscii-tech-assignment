package com.luscii.techassignment

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import com.luscii.techassignment.domain.models.Episode
import com.luscii.techassignment.views.episode.list.EpisodeListView
import com.luscii.techassignment.views.episode.list.EpisodeListViewState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import org.junit.Test

class EpisodeListViewUITest : BaseUITest() {
    @Test
    fun pagination_loading_state_displays_loading_indicator() {
        val fakePagingDataFlow = flowOf(PagingData.empty<Episode>())

        val viewStateFlow =
            MutableStateFlow<EpisodeListViewState>(
                EpisodeListViewState.EpisodeList(
                    episodesFlow = fakePagingDataFlow
                )
            )

        setupContent {
            EpisodeListView(
                viewStateFlow,
                onEpisodeSelected = {
                })
        }

        composeTestRule.onNodeWithTag("loading_spinner")
            .assertIsDisplayed()
    }

    @Test
    fun pagination_error_state_displays_error_message() {
        val fakePagingDataFlow = flowOf(
            PagingData.from(
                MockDataHelpers().generateMockEpisodes(),
                sourceLoadStates = LoadStates(
                    refresh = LoadState.Error(
                        Throwable("Failed to load")
                    ),
                    prepend = LoadState.NotLoading(false),
                    append = LoadState.NotLoading(false)
                )
            )
        )

        val viewStateFlow =
            MutableStateFlow<EpisodeListViewState>(
                EpisodeListViewState.EpisodeList(
                    episodesFlow = fakePagingDataFlow
                )
            )

        setupContent {
            EpisodeListView(
                viewStateFlow,
                onEpisodeSelected = {
                })
        }

        composeTestRule.onNodeWithTag("exception_view")
            .assertIsDisplayed()
    }

    @Test
    fun pagination_end_reached_displays_informative_message() {
        val fakePagingDataFlow = flowOf(
            PagingData.from(
                MockDataHelpers().generateMockEpisodes(),
                sourceLoadStates = LoadStates(
                    refresh = LoadState.NotLoading(false),
                    prepend = LoadState.NotLoading(false),
                    append = LoadState.NotLoading(true)
                )
            )
        )

        val viewStateFlow =
            MutableStateFlow<EpisodeListViewState>(
                EpisodeListViewState.EpisodeList(
                    episodesFlow = fakePagingDataFlow
                )
            )

        setupContent {
            EpisodeListView(
                viewStateFlow,
                onEpisodeSelected = {
                })
        }

        composeTestRule.onNodeWithText("No more episodes")
            .assertIsDisplayed()
    }

    @Test
    fun pagination_loaded_state_displays_loaded_entries() {
        val fakePagingDataFlow = flowOf(
            PagingData.from(
                MockDataHelpers().generateMockEpisodes()
            )
        )

        val viewStateFlow =
            MutableStateFlow<EpisodeListViewState>(
                EpisodeListViewState.EpisodeList(
                    episodesFlow = fakePagingDataFlow
                )
            )

        setupContent {
            EpisodeListView(
                viewStateFlow,
                onEpisodeSelected = {
                })
        }

        composeTestRule.onNodeWithText("Pilot")
            .assertIsDisplayed()
    }

    @Test
    fun entry_click_invokes_callback() {
        val fakePagingDataFlow = flowOf(
            PagingData.from(
                MockDataHelpers().generateMockEpisodes()
            )
        )

        val viewStateFlow =
            MutableStateFlow<EpisodeListViewState>(
                EpisodeListViewState.EpisodeList(
                    episodesFlow = fakePagingDataFlow
                )
            )

        var clickCounter = 0
        setupContent {
            EpisodeListView(
                viewStateFlow,
                onEpisodeSelected = {
                    clickCounter++
                })
        }

        composeTestRule.onNodeWithText("Pilot")
            .performClick()

        assert(clickCounter == 1)
    }
}
