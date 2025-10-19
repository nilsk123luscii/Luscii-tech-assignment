package com.luscii.techassignment.data.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.luscii.techassignment.data.datasources.RickAndMortyDataSource
import com.luscii.techassignment.domain.models.Episode
import javax.inject.Inject

class EpisodeRepository @Inject constructor(
    private val dataSource: RickAndMortyDataSource
) {

    fun getEpisodes(): Pager<Int, Episode> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { EpisodePagingSource(dataSource) }
        )
    }
}
