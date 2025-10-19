package com.luscii.techassignment.data.repositories

import com.luscii.techassignment.data.datasources.RickAndMortyDataSource
import com.luscii.techassignment.data.misc.NetworkResponse
import com.luscii.techassignment.domain.models.Character
import javax.inject.Inject

class CharacterRepository @Inject constructor(
    private val dataSource: RickAndMortyDataSource
) {
    suspend fun getCharacterById(id: Int): Result<Character> {
        return when (val result = dataSource.getCharacter(id)) {
            is NetworkResponse.Success -> {
                Result.success(result.body)
            }

            else -> {
                Result.failure(Exception("Failed to fetch character with id $id"))
            }
        }
    }
}
