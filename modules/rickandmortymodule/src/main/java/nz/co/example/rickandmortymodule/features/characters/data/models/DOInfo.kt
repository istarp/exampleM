package nz.co.example.rickandmortymodule.features.characters.data.models

import kotlinx.serialization.Serializable

@Serializable
internal data class DOInfo(val next: String?, val prev: String?)