package com.luscii.techassignment.main.navigation.destinations

import kotlinx.serialization.Serializable

@Serializable
sealed class EpisodeDestination {
    @Serializable
    data object EpisodeList : EpisodeDestination()

    @Serializable
    data class EpisodeDetail(val characterIds: List<Int>) : EpisodeDestination()
}
