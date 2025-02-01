package nz.co.example.nbamodule.features.nbaplayers.data.remote.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class DOMeta(
    @SerialName("prev_cursor") val prevCursor: Int? = null,
    @SerialName("next_cursor") val nextCursor: Int? = null
)