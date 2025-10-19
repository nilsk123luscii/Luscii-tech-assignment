package com.luscii.techassignment.views.episode.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.luscii.techassignment.views.common.ListItem

@Composable
fun EpisodeDetailView(characterIds: List<Int>, onCharacterSelected: (Int) -> Unit) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier.verticalScroll(scrollState)
    ) {
        characterIds.forEach { characterId ->
            ListItem(content = {
                Text("Character ID: $characterId")
            }, onClickItem = {
                onCharacterSelected(characterId)
            })
        }
    }
}
