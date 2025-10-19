package com.luscii.techassignment.views.character.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.luscii.techassignment.R
import com.luscii.techassignment.ui.theme.LocalSpacing
import com.luscii.techassignment.views.common.ExceptionType
import com.luscii.techassignment.views.common.ExceptionView
import com.luscii.techassignment.views.common.ListItem
import com.luscii.techassignment.views.common.LoadingSpinner
import kotlinx.coroutines.flow.StateFlow

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CharacterDetailView(
    state: StateFlow<CharacterDetailViewState>,
    onSaveCharacterToFile: () -> Unit
) {
    state.collectAsState().value.let { viewState ->

        val scrollState = rememberScrollState()

        when (viewState) {
            CharacterDetailViewState.Error -> {
                ExceptionView(
                    text = stringResource(R.string.character_load_failed_error),
                    exceptionType = ExceptionType.ERROR
                )
            }

            CharacterDetailViewState.Loading -> {
                LoadingSpinner()
            }

            is CharacterDetailViewState.Success -> {
                Column(
                    modifier = Modifier
                        .verticalScroll(scrollState)
                        .fillMaxSize()
                ) {
                    ListItem(
                        content = {
                            Button(onClick = {
                                onSaveCharacterToFile.invoke()
                            }) {
                                Text(text = stringResource(R.string.character_save_serialized_file))
                            }
                        }
                    )

                    ListItem(
                        content = {
                            Text("Name: ${viewState.character.name}")
                        }
                    )

                    ListItem(
                        content = {
                            Text("Status: ${viewState.character.status}")
                        }
                    )

                    ListItem(
                        content = {
                            Text("Species: ${viewState.character.species}")
                        }
                    )

                    ListItem(
                        content = {
                            Text("Origin name: ${viewState.character.origin.name}")
                        }
                    )

                    ListItem(
                        content = {
                            Text("Episode count: ${viewState.character.episode.size}")
                        }
                    )

                    ListItem(
                        content = {
                            Box(
                                modifier = Modifier.width(
                                    LocalSpacing.current.xxl * 5
                                )
                            ) {
                                GlideImage(
                                    model = viewState.character.image,
                                    contentDescription = "profile image"
                                )
                            }
                        }
                    )
                }
            }
        }
    }
}
