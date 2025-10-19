package com.luscii.techassignment.domain.usecases

import androidx.paging.Pager
import com.luscii.techassignment.data.repositories.EpisodeRepository
import com.luscii.techassignment.domain.models.Episode
import javax.inject.Inject

class GetEpisodesUseCase @Inject constructor(private val repository: EpisodeRepository) {
    operator fun invoke(): Pager<Int, Episode> {
        return repository.getEpisodes()
    }
}
