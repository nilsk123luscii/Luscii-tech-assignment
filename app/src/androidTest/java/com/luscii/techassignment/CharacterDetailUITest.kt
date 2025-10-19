package com.luscii.techassignment

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.luscii.techassignment.views.character.detail.CharacterDetailView
import com.luscii.techassignment.views.character.detail.CharacterDetailViewState
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Test

class CharacterDetailUITest : BaseUITest() {
    @Test
    fun loading_state_displays_loading_indicator() {
        val viewStateFlow =
            MutableStateFlow<CharacterDetailViewState>(
                CharacterDetailViewState.Loading
            )

        setupContent {
            CharacterDetailView(
                viewStateFlow,
                onSaveCharacterToFile = {
                }
            )
        }

        composeTestRule.onNodeWithTag("loading_spinner")
            .assertIsDisplayed()
    }

    @Test
    fun error_state_displays_exception_view() {
        val viewStateFlow =
            MutableStateFlow<CharacterDetailViewState>(
                CharacterDetailViewState.Error
            )

        setupContent {
            CharacterDetailView(
                viewStateFlow,
                onSaveCharacterToFile = {
                }
            )
        }

        composeTestRule.onNodeWithTag("exception_view")
            .assertIsDisplayed()
    }

    @Test
    fun loaded_state_displays_item() {
        val viewStateFlow =
            MutableStateFlow<CharacterDetailViewState>(
                CharacterDetailViewState.Success(
                    character = MockDataHelpers().generateMockCharacter()
                )
            )

        setupContent {
            CharacterDetailView(
                viewStateFlow,
                onSaveCharacterToFile = {
                }
            )
        }

        composeTestRule.onNodeWithText("Rick Sanchez", substring = true)
            .assertIsDisplayed()
    }

    @Test
    fun button_click_invokes_callback() {
        val viewStateFlow =
            MutableStateFlow<CharacterDetailViewState>(
                CharacterDetailViewState.Success(
                    character = MockDataHelpers().generateMockCharacter()
                )
            )

        var clickCounter = 0

        setupContent {
            CharacterDetailView(
                viewStateFlow,
                onSaveCharacterToFile = {
                    clickCounter = clickCounter + 1
                }
            )
        }

        composeTestRule.onNodeWithText("Save character to file")
            .performClick()

        assert(clickCounter == 1)
    }
}
