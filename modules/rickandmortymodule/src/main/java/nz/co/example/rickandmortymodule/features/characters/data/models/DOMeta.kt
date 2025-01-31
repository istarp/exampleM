package nz.co.example.rickandmortymodule.features.characters.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class DOMeta(
    @SerialName("prev_cursor") val prevCursor: Int? = null,
    @SerialName("next_cursor") val nextCursor: Int? = null
)