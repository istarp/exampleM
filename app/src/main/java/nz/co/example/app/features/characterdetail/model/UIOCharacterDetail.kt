package nz.co.example.app.features.characterdetail.model

import nz.co.example.nbamodule.features.nbaplayers.business.models.NBAPlayer

internal data class UIOCharacterDetail(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val origin: String,
    val location: String,
    val isFavourite: Boolean
) {

    companion object {
        fun forPreview(): UIOCharacterDetail {
            return UIOCharacterDetail(
                id = 1,
                name = "Rick Sanchez",
                imageUrl = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
                status = "Alive",
                species = "Human",
                type = "",
                gender = "Male",
                origin = "Earth (C-137)",
                location = "Citadel of Ricks",
                isFavourite = true
            )
        }
    }

}

internal fun mapFrom(data: NBAPlayer): UIOCharacterDetail {
    return UIOCharacterDetail(
        id = data.id,
        name = "${data.firstName} ${data.lastName}",
        imageUrl = "https://rickandmortyapi.com/api/character/avatar/${data.id}.jpeg",
        status = "",
        species = data.position,
        type = data.teamName,
        gender = "",
        origin = "",
        location = "",
        isFavourite = false
    )
}