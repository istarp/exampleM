package nz.co.example.app.features.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavBackStackEntry
import nz.co.example.app.features.navigation.models.topLevelRoutes

internal fun navEnterTransition(): AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition =
    {
        when {
            topLevelRoutes.contains(targetState.destination.route) -> fadeIn()
            else -> slideInFromLeft()
        }
    }

internal fun navExitTransition(): AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition = {
    when {
        topLevelRoutes.contains(targetState.destination.route) -> fadeOut()
        else -> slideOutToLeft()
    }
}

internal fun navPopExitTransition(): AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition =
    {
        when {
            topLevelRoutes.contains(initialState.destination.route) -> fadeOut()
            else -> slideOutToRight()
        }
    }

internal fun navPopEnterTransition(): AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition =
    {
        when {
            topLevelRoutes.contains(initialState.destination.route) -> fadeIn()
            else -> slideInFromRight()
        }
    }


internal fun fadeIn() = fadeIn(animationSpec = tween(DEFAULT_TRANSITION_DURATION_IN_MILLIS))

internal fun fadeOut() = fadeOut(animationSpec = tween(DEFAULT_TRANSITION_DURATION_IN_MILLIS))

internal fun AnimatedContentTransitionScope<NavBackStackEntry>.slideInFromLeft() =
    slideIntoContainer(
        AnimatedContentTransitionScope.SlideDirection.Left,
        tween(DEFAULT_TRANSITION_DURATION_IN_MILLIS)
    )

internal fun AnimatedContentTransitionScope<NavBackStackEntry>.slideOutToLeft() =
    slideOutOfContainer(
        AnimatedContentTransitionScope.SlideDirection.Left,
        tween(DEFAULT_TRANSITION_DURATION_IN_MILLIS)
    )

internal fun AnimatedContentTransitionScope<NavBackStackEntry>.slideOutToRight() =
    slideOutOfContainer(
        AnimatedContentTransitionScope.SlideDirection.Right,
        tween(DEFAULT_TRANSITION_DURATION_IN_MILLIS)
    )

internal fun AnimatedContentTransitionScope<NavBackStackEntry>.slideInFromRight() =
    slideIntoContainer(
        AnimatedContentTransitionScope.SlideDirection.Right,
        tween(DEFAULT_TRANSITION_DURATION_IN_MILLIS)
    )

internal const val DEFAULT_TRANSITION_DURATION_IN_MILLIS = 600