package com.luscii.techassignment

import com.luscii.techassignment.domain.models.Character
import com.luscii.techassignment.domain.models.Episode
import com.luscii.techassignment.domain.models.Location
import com.luscii.techassignment.domain.models.Origin

class MockDataHelpers {
    fun generateMockEpisodes(): List<Episode> {
        return listOf(
            Episode(
                id = 1,
                name = "Pilot",
                air_date = "December 2, 2013",
                episode = "S01E01",
                characters = listOf(),
                url = "http://www.google.nl",
                created = "TODO()"
            )
        )
    }

    fun generateMockCharacter(): Character {
        return Character(
            id = 1,
            name = "Rick Sanchez",
            status = "Alive",
            species = "Human",
            type = "Human",
            gender = "Male",
            origin = Origin(
                name = "Earth (C-137)",
                url = "TODO()"
            ),
            location = Location(
                name = "Earth (Replacement Dimension)",
                url = "www.google.nl"
            ),
            image = "www.google.nl",
            episode = listOf(),
            url = "www.google.nl",
            created = "",
        )
    }
}
