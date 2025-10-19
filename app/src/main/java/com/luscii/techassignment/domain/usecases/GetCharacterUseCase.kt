package com.luscii.techassignment.domain.usecases

import com.luscii.techassignment.data.repositories.CharacterRepository
import com.luscii.techassignment.domain.models.Character
import javax.inject.Inject

class GetCharacterUseCase @Inject constructor(private val repository: CharacterRepository) {
    suspend operator fun invoke(id: Int): Result<Character> {
        return repository.getCharacterById(id)
    }
}
