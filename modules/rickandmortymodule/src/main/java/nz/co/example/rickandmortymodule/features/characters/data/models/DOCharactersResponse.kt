package nz.co.example.rickandmortymodule.features.characters.data.models

import kotlinx.serialization.Serializable

@Serializable
internal data class DOCharactersResponse(val meta: DOMeta, val data: List<DOCharacter>)