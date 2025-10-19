package com.luscii.techassignment.views.character.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luscii.techassignment.domain.models.Character
import com.luscii.techassignment.domain.usecases.GetCharacterUseCase
import com.luscii.techassignment.domain.usecases.SaveCharacterToFileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class CharacterDetailViewState {
    data object Loading : CharacterDetailViewState()
    data class Success(val character: Character) : CharacterDetailViewState()
    data object Error : CharacterDetailViewState()
}

@HiltViewModel
class CharacterDetailViewModel @Inject constructor(
    val getCharacterUseCase: GetCharacterUseCase,
    val saveCharacterToFileUseCase: SaveCharacterToFileUseCase
) : ViewModel() {

    private val _state =
        MutableStateFlow<CharacterDetailViewState>(CharacterDetailViewState.Loading)
    internal val state: StateFlow<CharacterDetailViewState> = _state.asStateFlow()

    fun onLoadCharacterDetails(id: Int) {
        viewModelScope.launch {
            _state.value = CharacterDetailViewState.Loading
            val result = getCharacterUseCase(id)
            _state.value = if (result.isSuccess) {
                CharacterDetailViewState.Success(result.getOrNull()!!)
            } else {
                CharacterDetailViewState.Error
            }
        }
    }

    fun onSaveCharacterToFile() {
        _state.value.let {
            if (it is CharacterDetailViewState.Success) {
                viewModelScope.launch {
                    saveCharacterToFileUseCase(it.character)
                }
            }
        }
    }
}
