package com.luscii.techassignment

import com.luscii.techassignment.domain.usecases.GetCharacterUseCase
import com.luscii.techassignment.views.character.detail.CharacterDetailViewModel
import com.luscii.techassignment.views.character.detail.CharacterDetailViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.assertj.core.api.WithAssertions
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.assertIs

@OptIn(ExperimentalCoroutinesApi::class)
class CharacterDetailViewModelTest : WithAssertions {
    private val dispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
    }

    @Test
    fun `viewmodel starts in loading state`() = runTest {
        val getCharacterUsecase: GetCharacterUseCase = mock()

        whenever(getCharacterUsecase.invoke(1)).then {
            Result.success(MockDataHelpers().generateMockCharacter())
        }
        val testedViewModel =
            CharacterDetailViewModel(getCharacterUsecase, mock())

        assertThat(testedViewModel.state.value is CharacterDetailViewState.Loading)
    }

    @Test
    fun `load failure goes to error state`() = runTest {
        val testId = 1
        val getCharacterUsecase: GetCharacterUseCase = mock()

        whenever(getCharacterUsecase.invoke(testId)).thenReturn(
            Result.failure(Exception())
        )

        val testedViewModel =
            CharacterDetailViewModel(getCharacterUsecase, mock())

        testedViewModel.onLoadCharacterDetails(testId)

        val viewState = testedViewModel.state.value
        assertIs<CharacterDetailViewState.Error>(viewState)
    }

    @Test
    fun `successful load goes to success state`() = runTest {
        val testId = 1
        val getCharacterUsecase: GetCharacterUseCase = mock()

        whenever(getCharacterUsecase.invoke(testId)).thenReturn(
            Result.success(MockDataHelpers().generateMockCharacter())
        )

        val testedViewModel =
            CharacterDetailViewModel(getCharacterUsecase, mock())

        testedViewModel.onLoadCharacterDetails(testId)

        val viewState = testedViewModel.state.value
        assertIs<CharacterDetailViewState.Success>(viewState)
    }
}
