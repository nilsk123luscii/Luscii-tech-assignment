package com.luscii.techassignment.main.navigation.destinations

import kotlinx.serialization.Serializable

@Serializable
sealed class CharacterDestination {
    @Serializable
    data class CharacterDetail(val characterId: Int) : CharacterDestination()
}
