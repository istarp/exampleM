package nz.co.example.nbamodule.features.nbaplayers.data.remote.models

import kotlinx.serialization.Serializable

@Serializable
internal data class DONBAPlayersResponse(val meta: DOMeta, val data: List<DONBAPlayer>)