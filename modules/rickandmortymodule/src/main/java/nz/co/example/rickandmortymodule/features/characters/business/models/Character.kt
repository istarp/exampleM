package nz.co.example.rickandmortymodule.features.characters.business.models

data class Character(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val origin: String,
    val location: String,
    val imageUrl: String,
    val isFavourite: Boolean
)