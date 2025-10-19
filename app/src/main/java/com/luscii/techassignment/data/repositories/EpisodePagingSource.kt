package com.luscii.techassignment.data.repositories

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.luscii.techassignment.data.datasources.RickAndMortyDataSource
import com.luscii.techassignment.data.misc.NetworkResponse
import com.luscii.techassignment.domain.models.Episode
import kotlinx.coroutines.delay

class EpisodePagingSource(
    private val api: RickAndMortyDataSource
) : PagingSource<Int, Episode>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Episode> {

        val page = params.key ?: 1
        Log.e("EpisodePagingSource", "Loading page: $page")
        delay(2000L)
        val response = api.getEpisodes(page)
        return when (response) {
            is NetworkResponse.Success -> {
                val result = LoadResult.Page(
                    data = response.body.results,
                    prevKey = if (page == 1) null else page - 1,
                    nextKey = if (response.body.info.next == null) null else page + 1
                )

                return result
            }

            else -> {
                LoadResult.Error(Exception("Failed to fetch episodes"))
            }
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Episode>): Int? =
        state.anchorPosition?.let { anchor ->
            state.closestPageToPosition(anchor)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchor)?.nextKey?.minus(1)
        }
}
