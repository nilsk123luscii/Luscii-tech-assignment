package com.luscii.techassignment.main.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.luscii.techassignment.domain.models.characterIds
import com.luscii.techassignment.main.navigation.destinations.CharacterDestination
import com.luscii.techassignment.main.navigation.destinations.EpisodeDestination
import com.luscii.techassignment.views.character.detail.CharacterDetailView
import com.luscii.techassignment.views.character.detail.CharacterDetailViewModel
import com.luscii.techassignment.views.episode.detail.EpisodeDetailView
import com.luscii.techassignment.views.episode.list.EpisodeListView
import com.luscii.techassignment.views.episode.list.EpisodeListViewModel

@Composable
fun MainNavigation(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = EpisodeDestination.EpisodeList,
        modifier = modifier.fillMaxSize()
    ) {
        composable<EpisodeDestination.EpisodeList> {
            val episodeListViewModel: EpisodeListViewModel = hiltViewModel<EpisodeListViewModel>()

            EpisodeListView(episodeListViewModel.state, onEpisodeSelected = { episode ->
                navController.navigate(EpisodeDestination.EpisodeDetail(episode.characterIds))
            })
        }

        composable<EpisodeDestination.EpisodeDetail> { backStackEntry ->
            val destination = backStackEntry.toRoute<EpisodeDestination.EpisodeDetail>()

            EpisodeDetailView(destination.characterIds, onCharacterSelected = { characterId ->
                navController.navigate(CharacterDestination.CharacterDetail(characterId))
            })
        }

        composable<CharacterDestination.CharacterDetail> { backStackEntry ->
            val destination = backStackEntry.toRoute<CharacterDestination.CharacterDetail>()
            val characterDetailViewModel: CharacterDetailViewModel =
                hiltViewModel<CharacterDetailViewModel>()

            LaunchedEffect(Unit) {
                characterDetailViewModel.onLoadCharacterDetails(destination.characterId)
            }

            CharacterDetailView(
                characterDetailViewModel.state,
                onSaveCharacterToFile = characterDetailViewModel::onSaveCharacterToFile
            )
        }
    }
}
