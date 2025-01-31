package nz.co.example.app.ui.components.charactercard.model

import nz.co.example.rickandmortymodule.features.characters.business.models.Character

internal data class UIOCharacterCard(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val status: String,
    val isFavourite: Boolean
) {

    companion object {
        fun forPreviewFavourite(): UIOCharacterCard {
            return UIOCharacterCard(
                id = 1,
                name = "Rick Sanchez",
                imageUrl = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
                status = "Alive",
                isFavourite = true
            )
        }

        fun forPreview(): UIOCharacterCard {
            return UIOCharacterCard(
                id = 1,
                name = "Rick Sanchez",
                imageUrl = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
                status = "Alive",
                isFavourite = true
            )
        }

        fun forPreviewList(): List<UIOCharacterCard> {
            return listOf(
                UIOCharacterCard(
                    id = 1,
                    name = "Rick Sanchez",
                    imageUrl = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
                    status = "Alive",
                    isFavourite = true
                ),
                UIOCharacterCard(
                    id = 2,
                    name = "Rick Sanchez 2",
                    imageUrl = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
                    status = "Alive",
                    isFavourite = false
                ),
                UIOCharacterCard(
                    id = 3,
                    name = "Rick Sanchez 3",
                    imageUrl = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
                    status = "Alive",
                    isFavourite = true
                ),
                UIOCharacterCard(
                    id = 4,
                    name = "Rick Sanchez 4",
                    imageUrl = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
                    status = "Alive",
                    isFavourite = false
                ),
                UIOCharacterCard(
                    id = 5,
                    name = "Rick Sanchez 5",
                    imageUrl = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
                    status = "Alive",
                    isFavourite = false
                ),
                UIOCharacterCard(
                    id = 6,
                    name = "Rick Sanchez 6",
                    imageUrl = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
                    status = "Alive",
                    isFavourite = true
                )
            )
        }
    }
}

internal fun mapFrom(data: Character): UIOCharacterCard {
    return UIOCharacterCard(
        id = data.id,
        name = data.name,
        imageUrl = data.imageUrl,
        status = data.status,
        isFavourite = data.isFavourite
    )
}