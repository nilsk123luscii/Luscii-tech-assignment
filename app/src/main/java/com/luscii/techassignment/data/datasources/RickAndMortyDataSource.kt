package com.luscii.techassignment.data.datasources

import com.luscii.techassignment.data.misc.NetworkResponse
import com.luscii.techassignment.domain.models.Character
import com.luscii.techassignment.domain.models.EpisodeResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RickAndMortyDataSource {

    @GET("episode")
    suspend fun getEpisodes(@Query("page") page: Int): NetworkResponse<EpisodeResponse, Unit>

    @GET("character/{id}")
    suspend fun getCharacter(@Path("id") id: Int): NetworkResponse<Character, Unit>
}
