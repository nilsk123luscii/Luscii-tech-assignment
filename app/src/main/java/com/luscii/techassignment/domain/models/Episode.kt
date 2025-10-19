package com.luscii.techassignment.domain.models

data class Info(
    val count: Int,
    val pages: Int,
    val next: String?,
    val prev: String?
)

data class Episode(
    val id: Int,
    val name: String,
    val air_date: String,
    val episode: String,
    val characters: List<String>,
    val url: String,
    val created: String
)

data class EpisodeResponse(
    val info: Info,
    val results: List<Episode>
)

val Episode.characterIds: List<Int>
    get() = characters.mapNotNull { it.substringAfterLast("/").toIntOrNull() }
