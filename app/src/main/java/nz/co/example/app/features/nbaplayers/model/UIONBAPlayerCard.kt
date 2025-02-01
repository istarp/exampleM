package nz.co.example.app.features.nbaplayers.model

import nz.co.example.nbamodule.features.nbaplayers.business.models.NBAPlayer

internal data class UIONBAPlayerCard(
    val id: Int,
    val name: String,
    val position: String,
    val team: String
) {

    companion object {
        fun forPreviewList(): List<UIONBAPlayerCard> {
            return listOf(
                UIONBAPlayerCard(
                    id = 1,
                    name = "Rick Sanchez",
                    position = "G",
                    team = "Hawks"
                ),
                UIONBAPlayerCard(
                    id = 2,
                    name = "Rick Sanchez 2",
                    position = "B",
                    team = "Hawks 2"
                ),
                UIONBAPlayerCard(
                    id = 3,
                    name = "Rick Sanchez 3",
                    position = "C",
                    team = "Hawks 3"
                ),
                UIONBAPlayerCard(
                    id = 4,
                    name = "Rick Sanchez 4",
                    position = "A",
                    team = ""
                ),
                UIONBAPlayerCard(
                    id = 5,
                    name = "Rick Sanchez 5",
                    position = "",
                    team = "Hawks 5"
                )
            )
        }
    }
}

internal fun mapFrom(data: NBAPlayer): UIONBAPlayerCard {
    return UIONBAPlayerCard(
        id = data.id,
        name = data.firstName + " " + data.lastName,
        position = data.position,
        team = data.teamName
    )
}