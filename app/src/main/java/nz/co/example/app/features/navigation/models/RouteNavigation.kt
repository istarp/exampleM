package nz.co.example.app.features.navigation.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
internal data class RouteNavigation(
    val value: String, val transition: TransitionType = TransitionType.FADE
) : GenericNavigation, Parcelable